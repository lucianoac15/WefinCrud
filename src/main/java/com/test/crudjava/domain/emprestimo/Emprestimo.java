package com.test.crudjava.domain.emprestimo;

import com.test.crudjava.domain.pessoa.Pessoa;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "emprestimos")
@Entity(name = "Emprestimo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double valorEmprestimo;

    private double valorParcela;
    private int numeroParcelas;
    private TipoStatusPagamento statusPagamento;
    private LocalDate dataCriacao;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    public Emprestimo(Long id, double valorEmprestimo, double valorParcela, int numeroParcelas, Pessoa pessoa) {
        this.id = id;
        this.valorEmprestimo = valorEmprestimo;
        this.valorParcela = valorParcela;
        this.numeroParcelas = numeroParcelas;
        this.statusPagamento = TipoStatusPagamento.EM_ABERTO;
        this.dataCriacao = LocalDate.now();
        this.pessoa = pessoa;
    }

    public Long getId() {
        return id;
    }

    public double getValorEmprestimo() {
        return valorEmprestimo;
    }

    public double getValorParcela() {
        return valorParcela;
    }

    public int getNumeroParcelas() {
        return numeroParcelas;
    }

    public TipoStatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setStatusPagamento(TipoStatusPagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }
}
