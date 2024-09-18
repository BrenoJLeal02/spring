package br.com.fujideia.iesp.tecback.repository;

import br.com.fujideia.iesp.tecback.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {
    @Query("select f from Filme f where f.id=: id")
    List<Filme> findbyId(Long id);
    @Query("select f from Filme f where f.anoLancamento=:ano")
    List<Filme> findbyAnoLancamento(@Param("ano") Integer ano);
    @Query("SELECT f FROM Filme f WHERE f.titulo LIKE %:titulo%")
    List<Filme> findbyTitulo(String titulo);
    List<Filme> findbyAnoLancamentoAndTitulo(Integer ano, String titulo);
    @Query("SELECT f FROM Filme f WHERE f.titulo LIKE %:titulo%")
    List<Filme> buscarPorTitulo(@Param("titulo") String titulo);

}
