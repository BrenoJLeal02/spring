package br.com.fujideia.iesp.tecback.repository;


import br.com.fujideia.iesp.tecback.model.Diretor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiretorRepository extends JpaRepository<Diretor,Long> {
    @Query("SELECT d FROM Diretor a WHERE d.nome LIKE %:nome%")
    List<Diretor> findbyNome(String nome);
}
