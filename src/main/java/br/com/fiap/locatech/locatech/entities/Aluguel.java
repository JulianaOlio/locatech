package br.com.fiap.locatech.locatech.entities;

import br.com.fiap.locatech.locatech.dtos.AluguelRequestDTO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Aluguel{
    private Long id;
    private Long pessoaId;
    private Long veiculoId;
    private String veiculoModelo;
    private String pessoaCpf;
    private String pessoaNome;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private BigDecimal valorTotal;

    public Aluguel(AluguelRequestDTO aluguelRequestDTO, BigDecimal valor){
        this.pessoaId = aluguelRequestDTO.pessoaId();
        this.veiculoId = aluguelRequestDTO.veiculoId();
        this.dataInicio = aluguelRequestDTO.dataInicio();
        this.dataFim = aluguelRequestDTO.dataFim();
        this.valorTotal = valor;
    }
}