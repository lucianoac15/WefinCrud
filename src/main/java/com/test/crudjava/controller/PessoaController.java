package com.test.crudjava.controller;

import com.test.crudjava.controller.exceptions.ObjectNotFoundException;
import com.test.crudjava.controller.request.PessoaRequest;
import com.test.crudjava.controller.response.PessoaResponse;
import com.test.crudjava.domain.pessoa.Pessoa;
import com.test.crudjava.domain.pessoa.PessoaRepository;
import com.test.crudjava.domain.pessoa.converter.PessoaConverter;
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
@RequestMapping("/pessoas")
@Tag(name = "Pessoas")
public class PessoaController {

    @Autowired
    private PessoaRepository repository;

    @Autowired
    private PessoaConverter converter;


    @Operation(summary = "Cadastrar")
    @PostMapping
    @Transactional
    public ResponseEntity<Void> cadastrar(@Valid  @RequestBody PessoaRequest request) {
        Pessoa pessoa = converter.ParseEntity(request);
        pessoa = repository.save(pessoa);
        URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(pessoa.getId())
                    .toUri();
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Editar")
    @PutMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<Void> update(@Valid @RequestBody PessoaRequest request,
                                       @Valid @PathVariable Long id) {
        Pessoa pessoaSalva = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Pessoa não encontrada! Id: " + id));

        Pessoa pessoa = converter.ParseEntity(request);
        pessoa.setId(pessoaSalva.getId());
        pessoa = repository.save(pessoa);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deletar")
    @DeleteMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id) {
        Pessoa pessoa = repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Pessoa não encontrada! Id: " + id));

        repository.delete(pessoa);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Buscar pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<PessoaResponse> find(@Valid @PathVariable Long id) {
        PessoaResponse obj = converter.Parse(repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Pessoa não encontrada! Id: " + id)));
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    @Operation(summary = "Listar todos")
    public ResponseEntity<List<PessoaResponse>> findAll(@PageableDefault(size = 10, sort = { "nome" }) Pageable paginacao) {
        List<PessoaResponse> list = converter.Parse(repository.findAll());
        return ResponseEntity.ok().body(list);
    }
}
