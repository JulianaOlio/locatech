package br.com.fiap.locatech.locatech.controller;

import br.com.fiap.locatech.locatech.entities.Pessoa;
import br.com.fiap.locatech.locatech.services.PessoaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
@Tag(name = "Pessoa", description = "Controller CRUD Pessoas")
public class PessoaController {

    private static final Logger logger = LoggerFactory.getLogger(PessoaController.class);

    private final PessoaService service;

    public PessoaController(PessoaService service) {
        this.service = service;
    }

    // http://localhost:8080/pessoass?page=1&size=10
    @GetMapping
    public ResponseEntity<List<Pessoa>> findAllPessoas(
        @RequestParam("page") int page,
        @RequestParam("size") int size
    ) {
        logger.info("Foi acessado o endpoint de pessoas /pessoas");
        var pessoas =  this.service.findAllPessoas(page, size);
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Pessoa>> findPessoaById(
            @PathVariable long id
    ){
        logger.info("/pessoas" + id);
        this.service.findPessoaById(id);
        var pessoa = this.service.findPessoaById(id);
        return ResponseEntity.ok(pessoa);
    }

    @PostMapping
    public ResponseEntity<Pessoa> savePessoa(
            @RequestBody Pessoa pessoa
    ) {
        logger.info(" POST -> /pessoas");
        this.service.savePessoa(pessoa);

        var status = HttpStatus.CREATED;
        return ResponseEntity.status(status.value()).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> updatePessoa(
            @RequestBody Pessoa pessoa,
            @PathVariable("id") Long id
    ) {
        logger.info(" PUT -> /pessoas" + id);
        this.service.updatePessoa(pessoa, id);
        var status = HttpStatus.OK;
        return ResponseEntity.status(status.value()).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePessoa(
            @PathVariable Long id
    ) {
        logger.info(" DELETE -> /pessoas" + id);
        this.service.deletePessoa(id);
        var status = HttpStatus.NO_CONTENT;
        return ResponseEntity.status(status.value()).build();
    }
}
