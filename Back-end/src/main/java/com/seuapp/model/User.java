package com.seuapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;

    private String senha;

    // --- CAMPOS DO CADASTRO (o formulário já enviava isso, mas o backend descartava) ---
    private String descricao;

    // "discente" ou "docente" -- define quais dos campos abaixo se aplicam
    private String tipoVinculo;

    // Preenchidos quando tipoVinculo = "discente"
    private String curso;
    private Integer periodo;

    // Preenchidos quando tipoVinculo = "docente"
    private String departamento;
    private String siape;

    // --- CAMPOS PARA RECUPERAÇÃO DE SENHA ---
    private String codigoRecuperacao;

    private java.time.LocalDateTime codigoExpiracao;

    // Construtor vazio (obrigatório para o JPA/Hibernate)
    public User() {}

    // Construtor com os campos
    public User(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    // --- GETTERS E SETTERS MANUAIS ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; } // O método que o Java estava sentindo falta!

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getTipoVinculo() { return tipoVinculo; }
    public void setTipoVinculo(String tipoVinculo) { this.tipoVinculo = tipoVinculo; }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }

    public Integer getPeriodo() { return periodo; }
    public void setPeriodo(Integer periodo) { this.periodo = periodo; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getSiape() { return siape; }
    public void setSiape(String siape) { this.siape = siape; }

    public String getCodigoRecuperacao() { return codigoRecuperacao; }
    public void setCodigoRecuperacao(String codigoRecuperacao) { this.codigoRecuperacao = codigoRecuperacao; }

    public java.time.LocalDateTime getCodigoExpiracao() { return codigoExpiracao; }
    public void setCodigoExpiracao(java.time.LocalDateTime codigoExpiracao) { this.codigoExpiracao = codigoExpiracao; }
}