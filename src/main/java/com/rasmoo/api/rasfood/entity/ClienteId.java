package com.rasmoo.api.rasfood.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class ClienteId {
    private String cpf;

    private String email;

    public ClienteId() {
    }

    public ClienteId(String email, String cpf) {
        this.email = email;
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ClienteId{" +
                "cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
