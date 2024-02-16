package com.rasmoo.api.rasfood.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cliente {

    @EmbeddedId
    private ClienteId clienteId = new ClienteId();

    private String nome;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Endereco> endereco = new ArrayList<>();

    @Embedded
    private Contato contato;

    public Cliente() {
    }

    public Cliente(ClienteId clienteId, String nome, List<Endereco> endereco, Contato contato) {
        this.clienteId = clienteId;
        this.nome = nome;
        this.endereco = endereco;
        this.contato = contato;
    }

    public void addEndereco(Endereco endereco) {
        endereco.setCliente(this);
        this.endereco.add(endereco);
    }

    public ClienteId getClienteId() {
        return clienteId;
    }

    public void setClienteId(ClienteId clienteId) {
        this.clienteId = clienteId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Endereco> getEndereco() {
        return endereco;
    }

    public void setEndereco(List<Endereco> endereco) {
        this.endereco = endereco;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "clienteId=" + clienteId +
                ", nome='" + nome + '\'' +
                ", endereco=" + endereco +
                ", contato=" + contato +
                '}';
    }
}
