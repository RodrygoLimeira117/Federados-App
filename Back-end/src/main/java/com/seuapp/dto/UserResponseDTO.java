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
    private String descricao;
    private String tipoVinculo;
    private String curso;
    private Integer periodo;
    private String departamento;
    private String siape;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.nome = user.getNome();
        this.email = user.getEmail();
        this.descricao = user.getDescricao();
        this.tipoVinculo = user.getTipoVinculo();
        this.curso = user.getCurso();
        this.periodo = user.getPeriodo();
        this.departamento = user.getDepartamento();
        this.siape = user.getSiape();
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getDescricao() { return descricao; }
    public String getTipoVinculo() { return tipoVinculo; }
    public String getCurso() { return curso; }
    public Integer getPeriodo() { return periodo; }
    public String getDepartamento() { return departamento; }
    public String getSiape() { return siape; }
}
