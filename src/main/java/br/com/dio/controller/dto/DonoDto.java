package br.com.dio.controller.dto;

import br.com.dio.domain.model.Dono;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record DonoDto(
        Long id,
        String nome,
        String email,
        String cpf,
        String formaPagamento,
        String contato,
        List<PetDto> pets
) {
    public DonoDto(Dono model) {
        this(
                model.getId(),
                model.getNome(),
                model.getEmail(),
                model.getCpf(),
                model.getFormaPagamento(),
                model.getContato(),
                ofNullable(model.getPets()).orElse(emptyList()).stream().map(PetDto::new).collect(toList())
        );
    }

    public Dono toModel() {
        Dono model = new Dono();
        model.setId(this.id);
        model.setNome(this.nome);
        model.setEmail(this.email);
        model.setCpf(this.cpf);
        model.setFormaPagamento(this.formaPagamento);
        model.setContato(this.contato);
        model.setPets(ofNullable(this.pets).orElse(emptyList()).stream().map(PetDto::toModel).collect(toList()));
        return model;
    }
}