package br.com.dio.service.impl;

import br.com.dio.domain.model.Dono;
import br.com.dio.domain.repository.DonoRepository;
import br.com.dio.service.DonoService;
import br.com.dio.service.exception.BusinessException;
import br.com.dio.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class DonoServiceImpl implements DonoService {

    private final DonoRepository donoRepository;

    public DonoServiceImpl(DonoRepository donoRepository) {
        this.donoRepository = donoRepository;
    }

    @Transactional(readOnly = true)
    public List<Dono> findAll() {
        return this.donoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Dono findById(Long id) {
        return this.donoRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Dono create(Dono donoToCreate) {
        ofNullable(donoToCreate).orElseThrow(() -> new BusinessException("Dono to create must not be null."));
        ofNullable(donoToCreate.getCpf()).orElseThrow(() -> new BusinessException("Dono's CPF must not be null."));

        if (donoRepository.existsByCpf(donoToCreate.getCpf())) {
            throw new BusinessException("This cpf already exists.");
        }
        return this.donoRepository.save(donoToCreate);
    }

    @Override
    public Dono update(Long id, Dono donoToUpdate) {
        Dono dbDono = this.findById(id);
        if(!dbDono.getId().equals(donoToUpdate.getId())) {
            throw new BusinessException("Update IDs must be the same.");
        }

        dbDono.setContato(donoToUpdate.getContato());
        dbDono.setCpf(donoToUpdate.getCpf());
        dbDono.setNome(donoToUpdate.getNome());
        dbDono.setEmail(donoToUpdate.getEmail());
        dbDono.setFormaPagamento(donoToUpdate.getFormaPagamento());

        return this.donoRepository.save(dbDono);
    }

    @Override
    public void delete(Long id) {
        Dono dbDono = this.findById(id);
        this.donoRepository.delete(dbDono);
    }
}
