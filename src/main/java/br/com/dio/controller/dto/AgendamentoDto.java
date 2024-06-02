package br.com.dio.controller.dto;

import br.com.dio.domain.model.Agendamento;
import br.com.dio.domain.model.Dono;
import br.com.dio.domain.model.Pet;
import br.com.dio.domain.model.Unidade;
import br.com.dio.domain.model.Veterinario;
import br.com.dio.service.DonoService;
import br.com.dio.service.PetService;
import br.com.dio.service.UnidadeService;
import br.com.dio.service.VeterinarioService;
import br.com.dio.service.impl.DonoServiceImpl;

import java.time.LocalDate;
import java.time.LocalTime;

import static java.util.Optional.ofNullable;

public record AgendamentoDto(
        Long id,
        LocalDate data,
        LocalTime horario,
        String especialidade,
        DonoDto dono,
        PetDto pet,
        VeterinarioDto veterinario,
        UnidadeDto unidade
) {
    public AgendamentoDto(Agendamento model) {
        this(
                model.getId(),
                model.getData(),
                model.getHorario(),
                model.getEspecialidade(),
                ofNullable(model.getDono()).map(DonoDto::new).orElse(null),
                ofNullable(model.getPet()).map(PetDto::new).orElse(null),
                ofNullable(model.getVeterinario()).map(VeterinarioDto::new).orElse(null),
                ofNullable(model.getUnidade()).map(UnidadeDto::new).orElse(null)
        );
    }

    public Agendamento toModel() {
        Agendamento model = new Agendamento();
        model.setId(this.id);
        model.setData(this.data);
        model.setHorario(this.horario);
        model.setEspecialidade(this.especialidade);
        model.setDono(ofNullable(this.dono).map(DonoDto::toModel).orElse(null));
        model.setPet(ofNullable(this.pet).map(PetDto::toModel).orElse(null));
        model.setVeterinario(ofNullable(this.veterinario).map(VeterinarioDto::toModel).orElse(null));
        model.setUnidade(ofNullable(this.unidade).map(UnidadeDto::toModel).orElse(null));
        return model;
    }
}