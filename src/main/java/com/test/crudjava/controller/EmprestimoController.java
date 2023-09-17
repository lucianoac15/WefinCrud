package com.test.crudjava.controller;

import com.test.crudjava.controller.exceptions.ObjectNotFoundException;
import com.test.crudjava.controller.request.EmprestimoRequest;
import com.test.crudjava.controller.response.EmprestimoResponse;
import com.test.crudjava.domain.emprestimo.Emprestimo;
import com.test.crudjava.domain.emprestimo.EmprestimoRepository;
import com.test.crudjava.domain.emprestimo.TipoStatusPagamento;
import com.test.crudjava.domain.emprestimo.converter.EmprestimoConverter;
import com.test.crudjava.domain.pessoa.PessoaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/emprestimos")
@Tag(name = "Emprestimos")
public class EmprestimoController {
    @Autowired
    private EmprestimoRepository repository;

    @Autowired
    private PessoaRepository pessoARepository;

    @Autowired
    private EmprestimoConverter converter;

    @Operation(summary = "Cadastrar")
    @PostMapping
    @Transactional
    public ResponseEntity<Void> cadastrar(@Valid @RequestBody EmprestimoRequest request) {
        Emprestimo emprestimo = converter.ParseEntity(request, pessoARepository.findByIdentificador(request.identificador()));
        emprestimo = repository.save(emprestimo);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(emprestimo.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Editar")
    @PutMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<Void> update(@Valid @RequestBody EmprestimoRequest request,
                                       @Valid @PathVariable Long id) {
        Emprestimo emprestimoSalvo = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Emprestimo não encontrado! Id: " + id));

        Emprestimo emprestimo = converter.ParseEntity(request, emprestimoSalvo.getPessoa());
        emprestimo.setId(emprestimoSalvo.getId());
        emprestimo = repository.save(emprestimo);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Pagar")
    @PutMapping(value = "pagar/{id}")
    @Transactional
    public ResponseEntity<Void> pagar(@Valid @PathVariable Long id) {
        Emprestimo emprestimoSalvo = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Emprestimo não encontrado! Id: " + id));

        emprestimoSalvo.setStatusPagamento(TipoStatusPagamento.PAGO);
        repository.save(emprestimoSalvo);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deletar")
    @DeleteMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id) {
        Emprestimo emprestimo = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Emprestimo não encontrado! Id: " + id));

        repository.delete(emprestimo);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<EmprestimoResponse> find(@Valid @PathVariable Long id) {
        EmprestimoResponse obj = converter.Parse(repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Emprestimo não encontrado! Id: " + id)));
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    @Operation(summary = "Listar todos")
    public ResponseEntity<List<EmprestimoResponse>> findAll(@PageableDefault(size = 10, sort = {"id"}) Pageable paginacao) {
        List<EmprestimoResponse> list = converter.Parse(repository.findAll());
        return ResponseEntity.ok().body(list);
    }
}
