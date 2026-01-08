package br.com.fiap.locatech.locatech.controller;

import br.com.fiap.locatech.locatech.dtos.AluguelRequestDTO;
import br.com.fiap.locatech.locatech.entities.Aluguel;
import br.com.fiap.locatech.locatech.services.AluguelService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alugueis")
public class AluguelController {

    private static final Logger logger = LoggerFactory.getLogger(AluguelController.class);

    private final AluguelService service;

    public AluguelController(AluguelService service) {
        this.service = service;
    }

    // http://localhost:8080/alugueis?page=1&size=10
    @GetMapping
    public ResponseEntity<List<Aluguel>> findAllAlugueis(
        @RequestParam("page") int page,
        @RequestParam("size") int size
    ) {
        logger.info("Foi acessado o endpoint de alugueis /alugueis");
        var alugueis =  this.service.findAllAlugueis(page, size);
        return ResponseEntity.ok(alugueis);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Aluguel>> findAluguelById(
            @PathVariable long id
    ){
        logger.info("/alugueis" + id);
        var aluguel = this.service.findAluguelById(id);
        return ResponseEntity.ok(aluguel);
    }

    @PostMapping
    public ResponseEntity<Void> saveAluguel(
            @Valid @RequestBody AluguelRequestDTO aluguel
    ) {
        logger.info(" POST -> /alugueis");
        this.service.saveAluguel(aluguel);

        var status = HttpStatus.CREATED;
        return ResponseEntity.status(status.value()).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAluguel(
            @RequestBody Aluguel aluguel,
            @PathVariable("id") Long id
    ) {
        logger.info(" PUT -> /alugueis" + id);
        this.service.updateAluguel(aluguel, id);
        var status = HttpStatus.OK;
        return ResponseEntity.status(status.value()).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAluguel(
            @PathVariable Long id
    ) {
        logger.info(" DELETE -> /alugueis" + id);
        this.service.deleteAluguel(id);
        var status = HttpStatus.NO_CONTENT;
        return ResponseEntity.status(status.value()).build();
    }
}
