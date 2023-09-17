package com.test.crudjava.domain.pessoa.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PessoaValidation.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface IPessoaValidation {
    String message() default "Erro de validação";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
