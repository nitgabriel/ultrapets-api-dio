package br.com.dio.controller.dto;

import br.com.dio.domain.model.Veterinario;
import jakarta.validation.constraints.Null;

import static java.util.Optional.ofNullable;

public record VeterinarioDto(
        Long id,
        String nome,
        String crmv,
        UnidadeDto unidade,
        String status,
        String especialidade
) {
    public VeterinarioDto(Veterinario model) {
        this(
                model.getId(),
                model.getNome(),
                model.getCrmv(),
                ofNullable(model.getUnidade()).map(UnidadeDto::new).orElse(null),
                model.getStatus(),
                model.getEspecialidade()
        );
    }

    public Veterinario toModel() {
        Veterinario model = new Veterinario();
        model.setId(this.id);
        model.setNome(this.nome);
        model.setCrmv(this.crmv);
        model.setStatus(this.status);
        model.setEspecialidade(this.especialidade);
        model.setUnidade(ofNullable(this.unidade).map(UnidadeDto::toModel).orElse(null));
        return model;
    }
}