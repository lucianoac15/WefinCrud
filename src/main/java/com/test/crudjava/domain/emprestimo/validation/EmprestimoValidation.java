package com.test.crudjava.domain.emprestimo.validation;

import com.test.crudjava.controller.exceptions.FieldMessage;
import com.test.crudjava.controller.request.EmprestimoRequest;
import com.test.crudjava.domain.pessoa.Pessoa;
import com.test.crudjava.domain.pessoa.PessoaRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class EmprestimoValidation implements ConstraintValidator<IEmprestimoValidation, EmprestimoRequest> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private PessoaRepository pessoaRepository;

    public void initialize(IEmprestimoValidation annotation) {
    }

    @Override
    public boolean isValid(EmprestimoRequest emprestimoRequest, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();
        Pessoa pessoa = pessoaRepository.findByIdentificador(emprestimoRequest.identificador());

        if (pessoa == null) {
            list.add(new FieldMessage("identificador", "Identificador não cadastrado"));
        } else {
            if (emprestimoRequest.valorParcela() <= 0) {
                list.add(new FieldMessage("valorParcela", "O valor da parcela deve ser maior que zero"));
            }
            if (emprestimoRequest.valorEmprestimo() <= 0) {
                list.add(new FieldMessage("valorEmprestimo", "O valor do emprestimo deve ser maior que zero"));
            }
            if (emprestimoRequest.numeroParcelas() <= 0) {
                list.add(new FieldMessage("numeroParcelas", "A quantidade de parcelas deve ver maior que zero"));
            }
            if (pessoa.getValorMinimoMensal() > emprestimoRequest.valorParcela()) {
                list.add(new FieldMessage("valorParcela", "O valor da parcela é menor que o minimo permitido"));
            }
            if (pessoa.getValorMaximoEmprestimo() < emprestimoRequest.valorEmprestimo()) {
                list.add(new FieldMessage("valorEmprestimo", "O valor do emprestimo é maior que o permitido"));
            }
            if (emprestimoRequest.numeroParcelas() > 24) {
                list.add(new FieldMessage("numeroParcelas", "A quantidade de parcelas é maior que o permitido"));
            }
            if ((emprestimoRequest.numeroParcelas() * emprestimoRequest.valorParcela()) != emprestimoRequest.valorEmprestimo()) {
                list.add(new FieldMessage("valorParcela", "A quantidade de parcelas e o valor é imcompativel com o valor do emprestimo"));
            }
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
        }

        return list.isEmpty();
    }
}
