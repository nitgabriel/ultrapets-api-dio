package br.com.dio.domain.repository;

import br.com.dio.domain.model.Dono;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonoRepository extends JpaRepository<Dono, Long> {

    boolean existsByCpf(String cpf);

}
