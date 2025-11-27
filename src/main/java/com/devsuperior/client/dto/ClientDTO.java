package com.devsuperior.client.dto;

import com.devsuperior.client.entities.Client;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public class ClientDTO {

    private Long id;

    @NotBlank(message = "Nome é obrigatório") // não nulo e não vazio ("   " não vale)
    @Size(min = 10, message = "Nome deve ter pelo menos 10 caracteres")
    private String name;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(
            regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11}",
            message = "CPF deve ter 11 dígitos"
    )
    private String cpf;

    @NotNull(message = "Income(Renda) é obrigatória.")
    @PositiveOrZero(message = "Income(Renda) não pode ser negativa.")
    private Double income;

    @NotNull(message = "Data de nascimento é obrigatória.")
    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate birthDate;

    @NotNull(message = "Número de filhos é obrigatório.")
    @Min(value = 0, message = "Número de filhos não pode ser negativo.")
    private Integer children;

    public ClientDTO() {
    }

    public ClientDTO(Long id, String name, String cpf, Double income, LocalDate birthDate, Integer children) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.income = income;
        this.birthDate = birthDate;
        this.children = children;
    }

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.cpf = client.getCpf();
        this.income = client.getIncome();
        this.birthDate = client.getBirthDate();
        this.children = client.getChildren();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public Double getIncome() {
        return income;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Integer getChildren() {
        return children;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setChildren(Integer children) {
        this.children = children;
    }
}