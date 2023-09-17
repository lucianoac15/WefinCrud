package com.test.crudjava.domain.pessoa.validation;

import com.test.crudjava.controller.exceptions.FieldMessage;
import com.test.crudjava.controller.request.PessoaRequest;
import com.test.crudjava.controller.utils.Utils;
import com.test.crudjava.domain.pessoa.Pessoa;
import com.test.crudjava.domain.pessoa.PessoaRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PessoaValidation implements ConstraintValidator<IPessoaValidation, PessoaRequest> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private PessoaRepository repository;

    public void initialize(IPessoaValidation annotation) {
    }

    @Override
    public boolean isValid(PessoaRequest pessoaRequest, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();
        try {
            if (!Utils.validarIdentificacaoCpf(pessoaRequest.identificador()) && !Utils.validarIdentificacaoCnpj(pessoaRequest.identificador()) &&
                    !Utils.validarIdentificacaoEstudante(pessoaRequest.identificador()) && !Utils.validarIdentificacaoAposentado(pessoaRequest.identificador())) {
                list.add(new FieldMessage("identificador", "Identificador inválido"));
            } else {
                Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
                String requestId = map.get("id");
                Pessoa pessoa = repository.findByIdentificador(pessoaRequest.identificador());

                if (requestId == null) {
                    if (pessoa != null) {
                        list.add(new FieldMessage("identificador", "Identificador já cadastrado"));
                    }
                } else {
                    Long uriId = Long.parseLong(requestId);
                    if (pessoa != null && !pessoa.getId().equals(uriId)) {
                        list.add(new FieldMessage("identificador", "Identificador já cadastrado"));
                    }
                }
            }
        } catch (Exception ex) {
            list.add(new FieldMessage("Exception", ex.getMessage()));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
        }

        return list.isEmpty();
    }
}
