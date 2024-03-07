package com.newidea.pedido.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="orders")
public class OrderEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String item;
    private String valor;
    private String dataEntrada;
    private String dataAtualizacao;
    private String status;

    public OrderEntity() {
    }

    public OrderEntity(Long id, String item, String valor, String dataEntrada, String dataAtualizacao, String status) {
        this.id = id;
        this.item = item;
        this.valor = valor;
        this.dataEntrada = dataEntrada;
        this.dataAtualizacao = dataAtualizacao;
        this.status = status;
    }

    public OrderEntity(String item, String valor, String dataEntrada, String dataAtualizacao, String status) {
        this.item = item;
        this.valor = valor;
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

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
