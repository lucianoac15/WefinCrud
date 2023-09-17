package com.test.crudjava.domain.pessoa.converter;

import com.test.crudjava.controller.request.PessoaRequest;
import com.test.crudjava.controller.response.PessoaResponse;
import com.test.crudjava.domain.pessoa.Pessoa;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaConverter {

    public PessoaResponse Parse(Pessoa entity) {
        if (entity == null)
            return null;

        return new PessoaResponse(entity.getId(), entity.getNome(), entity.getIdentificador(),
                entity.getTipoIdentificador(), entity.getValorMinimoMensal(), entity.getValorMaximoEmprestimo(), entity.getDataNascimento());
    }

    public List<PessoaResponse> Parse(List<Pessoa> origin) {
        if (origin == null)
            return null;

        return origin.stream().map(obj -> Parse(obj)).collect(Collectors.toList());
    }

    public Pessoa ParseEntity(PessoaRequest request) {
        if (request == null)
            return null;

        return new Pessoa(request.nome(), request.identificador(), request.dataNascimento());
    }
}
