package br.com.dio.service.impl;

import br.com.dio.domain.model.Pet;
import br.com.dio.domain.repository.PetRepository;
import br.com.dio.service.PetService;
import br.com.dio.service.exception.BusinessException;
import br.com.dio.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Transactional(readOnly = true)
    public List<Pet> findAll() {
        return this.petRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Pet findById(Long id) {
        return this.petRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Pet create(Pet petToCreate) {
        ofNullable(petToCreate).orElseThrow(() -> new BusinessException("Pet to create must not be null."));
        ofNullable(petToCreate.getNome()).orElseThrow(() -> new BusinessException("Pet's name must not be null."));

        return this.petRepository.save(petToCreate);
    }

    @Override
    public Pet update(Long id, Pet petToUpdate) {
        Pet dbPet = this.findById(id);
        if(!dbPet.getId().equals(petToUpdate.getId())) {
            throw new BusinessException("Update IDs must be the same.");
        }

        dbPet.setNome(petToUpdate.getNome());
        dbPet.setDono(petToUpdate.getDono());
        dbPet.setEspecie(petToUpdate.getEspecie());
        dbPet.setRaca(petToUpdate.getRaca());
        dbPet.setDataNascimento(petToUpdate.getDataNascimento());

        return this.petRepository.save(dbPet);
    }

    @Override
    public void delete(Long id) {
        Pet dbPet = this.findById(id);
        this.petRepository.delete(dbPet);
    }
}