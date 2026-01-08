package br.com.fiap.locatech.locatech.services;

import br.com.fiap.locatech.locatech.dtos.AluguelRequestDTO;
import br.com.fiap.locatech.locatech.entities.Aluguel;
import br.com.fiap.locatech.locatech.repositories.AluguelRepository;
import br.com.fiap.locatech.locatech.repositories.VeiculoRepository;
import br.com.fiap.locatech.locatech.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AluguelService {

    private final AluguelRepository repository;
    private final VeiculoRepository veiculoRepository;

    public AluguelService(
            AluguelRepository repository,
            VeiculoRepository veiculoRepository
    ) {
        this.repository = repository;
        this.veiculoRepository = veiculoRepository;
    }

    public List<Aluguel> findAllAlugueis(int page, int size) {
        int offset = (page -1) * size;
        return this.repository.findAll(size, offset);
    }

    public Optional<Aluguel> findAluguelById(long id) {
        return Optional.ofNullable(this.repository.findbyId(id)
                .orElseThrow(()-> new ResourceNotFoundException("Aluguel não encontrado")));
    }

    public void saveAluguel(AluguelRequestDTO aluguel){
        var aluguelEntity = calculaAluguel(aluguel);
        var save = this.repository.save(aluguelEntity);
        Assert.state(save == 1 , "Erro ao salvar aluguel" + aluguel.pessoaId());
    }

    public void updateAluguel(Aluguel aluguel, Long id){
        var update = this.repository.update(aluguel, id);
        if (update == 0){
            throw new ResourceNotFoundException("Aluguel não encontrado");
        }
    }

    public void deleteAluguel(Long id){
        var delete = this.repository.delete(id);
        if( delete == 0 ){
            throw new ResourceNotFoundException("Aluguel não encontrada");
        }
    }

    private Aluguel calculaAluguel(AluguelRequestDTO aluguel){
        var veiculo = this.veiculoRepository.findById(aluguel.veiculoId())
                .orElseThrow(() -> new ResourceNotFoundException("Veiculo não encotrado"));

        var quantidadeDias = BigDecimal.valueOf(aluguel.dataFim().getDayOfYear() - aluguel.dataInicio().getDayOfYear());

        var valor = veiculo.getValorDiaria().multiply(quantidadeDias);
        return new Aluguel(aluguel,valor );
    }
}
