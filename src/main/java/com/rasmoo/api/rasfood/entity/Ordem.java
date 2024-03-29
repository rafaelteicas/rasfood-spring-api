package com.rasmoo.api.rasfood.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ordens")
public class Ordem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "valor_total")
    private BigDecimal valorTotal = BigDecimal.valueOf(0);

    @Column(name = "data_de_criacao")
    private LocalDateTime dataDeCriacao = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    @OneToMany(mappedBy = "ordem", cascade = CascadeType.ALL)
    private List<OrdensCardapio> ordensCardapioList = new ArrayList<>();

    public Ordem() {
    }

    public Ordem(BigDecimal valorTotal, LocalDateTime dataDeCriacao, Cliente cliente, List<OrdensCardapio> ordensCardapioList) {
        this.valorTotal = valorTotal;
        this.dataDeCriacao = dataDeCriacao;
        this.cliente = cliente;
        this.ordensCardapioList = ordensCardapioList;
    }

    public void addOrdensCardapio(OrdensCardapio ordensCardapio) {
        ordensCardapio.setOrdem(this);
        this.ordensCardapioList.add(ordensCardapio);
        this.valorTotal.add(ordensCardapio.getValorDeRegistro().multiply(BigDecimal.valueOf(ordensCardapio.getQuantidade())));
    }

    public List<OrdensCardapio> getOrdensCardapioList() {
        return ordensCardapioList;
    }

    public void setOrdensCardapioList(List<OrdensCardapio> ordensCardapioList) {
        this.ordensCardapioList = ordensCardapioList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDateTime getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(LocalDateTime dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
