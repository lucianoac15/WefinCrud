package com.test.crudjava.domain.pessoa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Pessoa findByIdentificador(String identificador);
}
