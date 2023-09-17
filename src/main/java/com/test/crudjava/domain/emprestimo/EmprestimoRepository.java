package com.test.crudjava.domain.emprestimo;

import com.test.crudjava.domain.pessoa.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    Pessoa findById(long id);
}
