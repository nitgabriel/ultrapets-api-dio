package br.com.dio.controller.dto;

import br.com.dio.domain.model.Dono;
import br.com.dio.domain.model.Pet;

import java.time.LocalDate;

import static java.util.Optional.ofNullable;

public record PetDto(
        Long id,
        String nome,
        String especie,
        String numeroConveniado,
        String raca,
        LocalDate dataNascimento,
        DonoDto donoDto
) {
    public PetDto(Pet model) {
        this(
                model.getId(),
                model.getNome(),
                model.getEspecie(),
                model.getNumeroConveniado(),
                model.getRaca(),
                model.getDataNascimento(),
                ofNullable(model.getDono()).map(DonoDto::new).orElse(null)
        );
    }

    public Pet toModel() {
        Pet model = new Pet();
        model.setId(this.id);
        model.setNome(this.nome);
        model.setEspecie(this.especie);
        model.setNumeroConveniado(this.numeroConveniado);
        model.setRaca(this.raca);
        model.setDataNascimento(this.dataNascimento);
        model.setDono(ofNullable(this.donoDto).map(DonoDto::toModel).orElse(null));
        return model;
    }
}