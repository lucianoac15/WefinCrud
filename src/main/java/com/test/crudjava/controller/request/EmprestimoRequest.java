package com.test.crudjava.controller.request;

import com.test.crudjava.domain.emprestimo.validation.IEmprestimoValidation;
import jakarta.validation.constraints.NotEmpty;


@IEmprestimoValidation
public record EmprestimoRequest(
        double valorEmprestimo,
        double valorParcela,
        int numeroParcelas,
        @NotEmpty(message = "O identificador é obrigatório")
        String identificador
) {
}

