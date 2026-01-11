package br.com.fiap.locatech.locatech.controller;

import br.com.fiap.locatech.locatech.entities.Veiculo;
import br.com.fiap.locatech.locatech.services.VeiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/veiculos")
@Tag(name = "Veículo", description = "Controller CRUD Veículos")
public class VeiculoController {

    private static final Logger logger = LoggerFactory.getLogger(VeiculoController.class);

    private final VeiculoService service;

    public VeiculoController(VeiculoService service) {
        this.service = service;
    }

    // http://localhost:8080/veiculos?page=1&size=10
    @Operation(
            description = "Busca todos os veículos paginados",
            summary = "Busca de veículos",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200")
            }
    )
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
        var status = HttpStatus.CREATED;
        return ResponseEntity.status(status.value()).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateVeiculo(
        @PathVariable("id") Long id,
        @RequestBody Veiculo veiculo
    ){
        logger.info("PUT -> /veiculos" + id);
        this.service.updateVeiculo(veiculo, id);

        var status = HttpStatus.OK;
        return ResponseEntity.status(status.value()).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVeiculo(
            @PathVariable("id") Long id
    ){
        logger.info("DELETE -> /veiculos" + id);
        this.service.deleteVeiculo(id);
        var status = HttpStatus.NO_CONTENT;
        return ResponseEntity.status(status.value()).build();
    }
}
