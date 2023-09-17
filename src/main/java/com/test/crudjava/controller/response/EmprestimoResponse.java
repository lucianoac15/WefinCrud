package com.test.crudjava.controller.response;

import com.test.crudjava.domain.emprestimo.TipoStatusPagamento;

import java.time.LocalDate;

public record EmprestimoResponse(Long id, double valorEmprestimo, double valorParcela, int numeroParcelas,
                                 TipoStatusPagamento statusPagamento, LocalDate dataCriacao, PessoaResponse pessoa) {
}
