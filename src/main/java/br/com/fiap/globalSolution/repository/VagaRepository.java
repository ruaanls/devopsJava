package br.com.fiap.globalSolution.repository;

import br.com.fiap.globalSolution.entity.StatusVaga;
import br.com.fiap.globalSolution.entity.Vagas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VagaRepository extends JpaRepository<Vagas, Long>
{
    @Query("SELECT v FROM Vagas v WHERE v.linha = :linha AND v.id NOT IN (SELECT m.vaga.id FROM Motos m WHERE m.vaga IS NOT NULL)")
    List<Vagas> findVagasLivresByLinha(@Param("linha") String linha);

    List<Vagas> findByStatusVaga(StatusVaga statusVaga);
}
