package com.test.crudjava.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.test.crudjava.domain.pessoa.TipoIdentificador;

import java.util.Date;

public record PessoaResponse(Long id, String nome, String identificador, TipoIdentificador tipoIdentificador,
                             double valorMinimoMensal, double valorMaximoEmprestimo,
                             @JsonFormat(pattern = "yyyy-MM-dd")
                             Date dataNascimento) {
}