package br.com.fujideia.iesp.tecback.repository;

import br.com.fujideia.iesp.tecback.model.Ator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtorRepository extends JpaRepository<Ator, Long> {

    @Query("SELECT a FROM Ator a WHERE a.nome LIKE %:nome%")
    List<Ator> findbyNome(String nome);
}
