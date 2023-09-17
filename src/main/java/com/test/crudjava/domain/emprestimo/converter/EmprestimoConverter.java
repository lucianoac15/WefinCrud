package com.test.crudjava.domain.emprestimo.converter;

import com.test.crudjava.controller.request.EmprestimoRequest;
import com.test.crudjava.controller.response.EmprestimoResponse;
import com.test.crudjava.domain.emprestimo.Emprestimo;
import com.test.crudjava.domain.pessoa.Pessoa;
import com.test.crudjava.domain.pessoa.converter.PessoaConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmprestimoConverter {

    @Autowired
    private PessoaConverter converter;

    public EmprestimoResponse Parse(Emprestimo entity) {
        if (entity == null)
            return null;

        return new EmprestimoResponse(entity.getId(), entity.getValorEmprestimo(), entity.getValorParcela(), entity.getNumeroParcelas(),
                entity.getStatusPagamento(), entity.getDataCriacao(), converter.Parse(entity.getPessoa()));
    }

    public List<EmprestimoResponse> Parse(List<Emprestimo> origin) {
        if (origin == null)
            return null;

        return origin.stream().map(obj -> Parse(obj)).collect(Collectors.toList());
    }

    public Emprestimo ParseEntity(EmprestimoRequest request, Pessoa pessoa) {
        if (request == null)
            return null;

        return new Emprestimo(0L, request.valorEmprestimo(), request.valorParcela(), request.numeroParcelas(), pessoa);
    }
}
