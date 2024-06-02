package br.com.dio.service.impl;

import br.com.dio.domain.model.Unidade;
import br.com.dio.domain.repository.UnidadeRepository;
import br.com.dio.service.UnidadeService;
import br.com.dio.service.exception.BusinessException;
import br.com.dio.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class UnidadeServiceImpl implements UnidadeService {
    private final UnidadeRepository unidadeRepository;

    public UnidadeServiceImpl(UnidadeRepository unidadeRepository) {
        this.unidadeRepository = unidadeRepository;
    }

    @Transactional(readOnly = true)
    public List<Unidade> findAll() {
        return this.unidadeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Unidade findById(Long id) {
        return this.unidadeRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Unidade create(Unidade unidadeToCreate) {
        ofNullable(unidadeToCreate).orElseThrow(() -> new BusinessException("Unidade to create must not be null."));
        ofNullable(unidadeToCreate.getCep()).orElseThrow(() -> new BusinessException("Unidade's CEP must not be null."));

        return this.unidadeRepository.save(unidadeToCreate);
    }

    @Override
    public Unidade update(Long id, Unidade unidadeToUpdate) {
        Unidade dbUnidade = this.findById(id);
        if(!dbUnidade.getId().equals(unidadeToUpdate.getId())) {
            throw new BusinessException("Update IDs must be the same.");
        }

        dbUnidade.setNome(unidadeToUpdate.getNome());
        dbUnidade.setCep(unidadeToUpdate.getCep());
        dbUnidade.setRua(unidadeToUpdate.getRua());
        dbUnidade.setBairro(unidadeToUpdate.getBairro());
        dbUnidade.setCidade(unidadeToUpdate.getCidade());
        dbUnidade.setUf(unidadeToUpdate.getUf());
        dbUnidade.setNumero(unidadeToUpdate.getNumero());


        return this.unidadeRepository.save(dbUnidade);
    }

    @Override
    public void delete(Long id) {
        Unidade dbUnidade = this.findById(id);
        this.unidadeRepository.delete(dbUnidade);
    }
}
