package com.test.crudjava.domain.pessoa;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "pessoas")
@Entity(name = "Pessoa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String identificador;
    @Enumerated(EnumType.STRING)
    private TipoIdentificador tipoIdentificador;

    private double valorMinimoMensal;
    private double valorMaximoEmprestimo;
    private Date dataNascimento;

    public Pessoa(String nome, String identificador, Date dataNascimento) {
        this.nome = nome;
        this.identificador = identificador;
        this.tipoIdentificador = obterTipoIdentificador();
        this.valorMinimoMensal = obterValorMinimoMensal();
        this.valorMaximoEmprestimo = obterValorMaximoEmprestimo();
        this.dataNascimento = dataNascimento;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getIdentificador() {
        return identificador;
    }

    public TipoIdentificador getTipoIdentificador() {
        return tipoIdentificador;
    }

    public double getValorMinimoMensal() {
        return valorMinimoMensal;
    }

    public double getValorMaximoEmprestimo() {
        return valorMaximoEmprestimo;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    private TipoIdentificador obterTipoIdentificador(){

        switch (identificador.length()){
            case 8: return TipoIdentificador.EU;
            case 10: return TipoIdentificador.AP;
            case 11: return TipoIdentificador.PF;
            case 14: return TipoIdentificador.PJ;
        }
        return null;
    }

    private double obterValorMinimoMensal(){
        switch (tipoIdentificador){
            case EU: return 100.0;
            case AP: return 400.0;
            case PF: return 300.0;
            case PJ: return 1000.0;
        }
        return 0;
    }

    private double obterValorMaximoEmprestimo(){
        switch (tipoIdentificador){
            case EU, PF: return 10000.0;
            case AP: return 25000.0;
            case PJ: return 100000.0;
        }
        return 0;
    }


}
