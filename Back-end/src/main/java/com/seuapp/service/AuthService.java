package com.seuapp.service;

import com.seuapp.dto.RegisterDTO;
import com.seuapp.model.User;
import com.seuapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    private static final SecureRandom RANDOM = new SecureRandom();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registrar(RegisterDTO dto) {
        User user = new User();
        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());
        // Nunca gravamos a senha em texto puro -- gravamos o hash.
        user.setSenha(passwordEncoder.encode(dto.getSenha()));
        return userRepository.save(user);
    }

    public Optional<User> login(String email, String senha) {
        return userRepository.findByEmail(email)
                .filter(u -> passwordEncoder.matches(senha, u.getSenha()));
    }

    // --- RECUPERAÇÃO DE SENHA ---

    // Etapa 1: gera o código, salva no usuário e dispara o email
    public boolean gerarCodigoRecuperacao(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            // Não revelamos se o email existe ou não, por segurança
            return false;
        }

        User user = userOpt.get();
        // SecureRandom em vez de Random: o código de recuperação de senha
        // é um segredo de curta duração e precisa ser imprevisível.
        String codigo = String.format("%06d", RANDOM.nextInt(1_000_000));

        user.setCodigoRecuperacao(codigo);
        user.setCodigoExpiracao(LocalDateTime.now().plusMinutes(5));
        userRepository.save(user);

        emailService.enviarCodigoRecuperacao(email, codigo);
        return true;
    }

    // Etapa 2: apenas confere se o código bate e não expirou
    public boolean verificarCodigo(String email, String codigo) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) return false;

        User user = userOpt.get();
        if (user.getCodigoRecuperacao() == null || user.getCodigoExpiracao() == null) return false;
        if (LocalDateTime.now().isAfter(user.getCodigoExpiracao())) return false;

        return user.getCodigoRecuperacao().equals(codigo);
    }

    // Etapa 3: confere o código de novo (nunca confie só no que o front-end diz) e troca a senha
    public boolean redefinirSenha(String email, String codigo, String novaSenha) {
        if (!verificarCodigo(email, codigo)) return false;

        User user = userRepository.findByEmail(email).get();
        user.setSenha(passwordEncoder.encode(novaSenha));

        // invalida o código pra não poder ser reusado
        user.setCodigoRecuperacao(null);
        user.setCodigoExpiracao(null);

        userRepository.save(user);
        return true;
    }
}
