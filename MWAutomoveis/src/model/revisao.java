package model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
@Entity
public class revisao {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int idRevisao;
    private Date data;
    private double km;
    private String servicosRealizados;
    @JoinColumn (name = "idAutomovel")
    private automovel automovel;

    public revisao(Date data, double km, String servicosRealizados, automovel automovel) {
        this.data = data;
        this.km = km;
        this.servicosRealizados = servicosRealizados;
        this.automovel = automovel;
    }

    public revisao() {
    }

    

    public int getIdRevisao() {
        return idRevisao;
    }

    public void setIdRevisao(int idRevisao) {
        this.idRevisao = idRevisao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getKm() {
        return km;
    }

    public void setKm(double km) {
        this.km = km;
    }

    public String getServicosRealizados() {
        return servicosRealizados;
    }

    public automovel getAutomovel() {
        return automovel;
    }

    public void setAutomovel(automovel automovel) {
        this.automovel = automovel;
    }
    

    public void setServicosRealizados(String servicosRealizados) {
        this.servicosRealizados = servicosRealizados;
    }
    
    
}
