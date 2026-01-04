package br.com.fiap.locatech.locatech.controller;

import br.com.fiap.locatech.locatech.entities.Veiculo;
import br.com.fiap.locatech.locatech.repositories.VeiculoRepository;
import br.com.fiap.locatech.locatech.services.VeiculoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private static final Logger logger = LoggerFactory.getLogger(VeiculoController.class);

    private final VeiculoService service;

    public VeiculoController(VeiculoService service) {
        this.service = service;
    }


    // http://localhost:8080/veiculos?page=1&size=10

    @GetMapping
    public ResponseEntity<List<Veiculo>> findAllVeiculos(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
      logger.info("Foi acessado o endpoint de veículos /veiculos");
      var veiculos = this.service.findAllVeiculos(page, size);
      return ResponseEntity.ok(veiculos);
    }

    // http://localhost:8080/veiculos/1
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Veiculo>> findVeiculoById(
            @PathVariable("id") long id
    ) {
        logger.info("/veiculos" + id);
        var veiculo = this.service.findVeiculoById(id);
        return ResponseEntity.ok(veiculo);
    }

    @PostMapping
    public ResponseEntity<Void> saveVeiculo(
            @RequestBody Veiculo veiculo
    ) {
        logger.info("POST -> /veiculos");
        this.service.saveVeiculo(veiculo);
        return ResponseEntity.status(201).build();
    }

}
