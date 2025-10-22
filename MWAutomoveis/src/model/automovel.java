package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class automovel {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idAutomoveis")
    private int idAutomovel;
    @JoinColumn (name = "idCliente")
    private cliente cliente;
    private String placa;
    private String marca;
    private String modelo;
    private int ano;

    public automovel(cliente cliente, String placa, String marca, String modelo, int ano) {
        this.cliente = cliente;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
    }

    public automovel() {
    }

    public int getIdAutomovel() {
        return idAutomovel;
    }

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    @Override
    public String toString() {
        return marca;
    }
    
    
}
