package com.newidea.pedido.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="pedido")
public class Pedido {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dataEntrada;
    private String dataAtualizacao;
    private String status;

    public Pedido() {

    }

    public Pedido(Long id, String dataEntrada, String dataAtualizacao, String status) {
        this.id = id;
        this.dataEntrada = dataEntrada;
        this.dataAtualizacao = dataAtualizacao;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(String dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDataAtualizacao() { return dataAtualizacao; }

    public void setDataAtualizacao(String dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
}
