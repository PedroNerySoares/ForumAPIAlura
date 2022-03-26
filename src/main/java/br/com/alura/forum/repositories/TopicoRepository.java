package br.com.alura.forum.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.forum.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

	//Cruso - relacionamento de Topico
	//	nome do curso 
	
	List<Topico> findByCursoNome(String nomeCurso);

//	 este caso devera ser criado JPQL quando vc não quiser utilizar forma do spring 
//	@Query("SELECT T FROM Topico t WHERE T.CURSO.NOME= :nomeCurso")
//	List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso);
	
}
