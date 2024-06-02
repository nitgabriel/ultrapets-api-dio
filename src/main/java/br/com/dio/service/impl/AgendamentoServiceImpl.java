package br.com.dio.service.impl;

import br.com.dio.domain.model.Agendamento;
import br.com.dio.domain.repository.AgendamentoRepository;
import br.com.dio.service.AgendamentoService;
import br.com.dio.service.exception.BusinessException;
import br.com.dio.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class AgendamentoServiceImpl implements AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    public AgendamentoServiceImpl(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    @Transactional(readOnly = true)
    public List<Agendamento> findAll() {
        return this.agendamentoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Agendamento findById(Long id) {
        return this.agendamentoRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Agendamento create(Agendamento agendamentoToCreate) {
        ofNullable(agendamentoToCreate).orElseThrow(() -> new BusinessException("Agendamento to create must not be null."));
        ofNullable(agendamentoToCreate.getData()).orElseThrow(() -> new BusinessException("Agendamento's date must not be null."));
        ofNullable(agendamentoToCreate.getHorario()).orElseThrow(() -> new BusinessException("Agendamento's Hora must not be null."));
        ofNullable(agendamentoToCreate.getPet()).orElseThrow(() -> new BusinessException("Agendamento's Pet must not be null."));
        ofNullable(agendamentoToCreate.getDono()).orElseThrow(() -> new BusinessException("Agendamento's Dono must not be null."));
        ofNullable(agendamentoToCreate.getUnidade()).orElseThrow(() -> new BusinessException("Agendamento's Unidade must not be null."));
        ofNullable(agendamentoToCreate.getVeterinario()).orElseThrow(() -> new BusinessException("Agendamento's Veterinario must not be null."));

        return this.agendamentoRepository.save(agendamentoToCreate);
    }

    @Override
    public Agendamento update(Long id, Agendamento agendamentoToUpdate) {
        Agendamento dbAgendamento = this.findById(id);
        if(!dbAgendamento.getId().equals(agendamentoToUpdate.getId())) {
            throw new BusinessException("Update IDs must be the same.");
        }

        dbAgendamento.setData(agendamentoToUpdate.getData());
        dbAgendamento.setHorario(agendamentoToUpdate.getHorario());
        dbAgendamento.setDono(agendamentoToUpdate.getDono());
        dbAgendamento.setPet(agendamentoToUpdate.getPet());
        dbAgendamento.setVeterinario(agendamentoToUpdate.getVeterinario());
        dbAgendamento.setUnidade(agendamentoToUpdate.getUnidade());
        dbAgendamento.setEspecialidade(agendamentoToUpdate.getEspecialidade());

        return this.agendamentoRepository.save(dbAgendamento);
    }

    @Override
    public void delete(Long id) {
        Agendamento dbAgendamento = this.findById(id);
        this.agendamentoRepository.delete(dbAgendamento);
    }
}