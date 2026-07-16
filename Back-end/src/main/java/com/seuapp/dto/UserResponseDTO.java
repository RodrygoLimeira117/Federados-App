package com.seuapp.dto;

import com.seuapp.model.User;

/**
 * DTO de saída para dados de usuário.
 * Existe justamente para NUNCA devolver o hash da senha na resposta da API
 * (o AuthController antigo devolvia a entidade User inteira no /registro).
 */
public class UserResponseDTO {
    private Long id;
    private String nome;
    private String email;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.nome = user.getNome();
        this.email = user.getEmail();
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
}
