package com.rasmoo.api.rasfood.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ordens_cardapio")
public class OrdensCardapio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cardapio cardapio;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ordem ordem;

    @Column(name = "valor_de_registro")
    private BigDecimal valorDeRegistro;

    private Integer quantidade;

    public OrdensCardapio() {
    }

    public OrdensCardapio(Cardapio cardapio, Ordem ordem, BigDecimal valorDeRegistro, Integer quantidade) {
        this.cardapio = cardapio;
        this.ordem = ordem;
        this.valorDeRegistro = valorDeRegistro;
        this.quantidade = quantidade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cardapio getCardapio() {
        return cardapio;
    }

    public void setCardapio(Cardapio cardapio) {
        this.cardapio = cardapio;
    }

    public Ordem getOrdem() {
        return ordem;
    }

    public void setOrdem(Ordem ordem) {
        this.ordem = ordem;
    }

    public BigDecimal getValorDeRegistro() {
        return valorDeRegistro;
    }

    public void setValorDeRegistro(BigDecimal valorDeRegistro) {
        this.valorDeRegistro = valorDeRegistro;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "OrdensCardapio{" +
                "id=" + id +
                ", cardapio=" + cardapio +
                ", ordem=" + ordem +
                ", valorDeRegistro=" + valorDeRegistro +
                ", quantidade=" + quantidade +
                '}';
    }
}
