package br.com.dio.service.impl;

import br.com.dio.domain.model.Veterinario;
import br.com.dio.domain.repository.VeterinarioRepository;
import br.com.dio.service.VeterinarioService;
import br.com.dio.service.exception.BusinessException;
import br.com.dio.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class VeterinarioServiceImpl implements VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;

    public VeterinarioServiceImpl(VeterinarioRepository veterinarioRepository) {
        this.veterinarioRepository = veterinarioRepository;
    }

    @Transactional(readOnly = true)
    public List<Veterinario> findAll() {
        return this.veterinarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Veterinario findById(Long id) {
        return this.veterinarioRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Veterinario create(Veterinario veterinarioToCreate) {
        ofNullable(veterinarioToCreate).orElseThrow(() -> new BusinessException("Veterinario to create must not be null."));
        ofNullable(veterinarioToCreate.getCrmv()).orElseThrow(() -> new BusinessException("Veterinario's CRMV must not be null."));

        if (veterinarioRepository.existsByCrmv(veterinarioToCreate.getCrmv())) {
            throw new BusinessException("This CRMV already exists.");
        }
        return this.veterinarioRepository.save(veterinarioToCreate);
    }

    @Override
    public Veterinario update(Long id, Veterinario veterinarioToUpdate) {
        Veterinario dbVeterinario = this.findById(id);
        if(!dbVeterinario.getId().equals(veterinarioToUpdate.getId())) {
            throw new BusinessException("Update IDs must be the same.");
        }

        dbVeterinario.setNome(veterinarioToUpdate.getNome());
        dbVeterinario.setCrmv(veterinarioToUpdate.getCrmv());
        dbVeterinario.setUnidade(veterinarioToUpdate.getUnidade());
        dbVeterinario.setStatus(veterinarioToUpdate.getStatus());
        dbVeterinario.setEspecialidade(veterinarioToUpdate.getEspecialidade());

        return this.veterinarioRepository.save(dbVeterinario);
    }

    @Override
    public void delete(Long id) {
        Veterinario dbVeterinario = this.findById(id);
        this.veterinarioRepository.delete(dbVeterinario);
    }
}