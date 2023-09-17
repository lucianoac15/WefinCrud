package com.test.crudjava.controller.request;

import com.test.crudjava.domain.pessoa.validation.IPessoaValidation;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@IPessoaValidation
public record PessoaRequest(
        @NotEmpty(message = "O nome é obrigatório")
        @Size(min = 5, max = 50, message = "O nome deve possuir entre {min} e {max} caracteres")
        String nome,
        @NotEmpty(message = "O identificador é obrigatório")
        String identificador,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @Past
        @NotNull(message = "A data de nascimento é obrigatória")
        Date dataNascimento

) {
}
