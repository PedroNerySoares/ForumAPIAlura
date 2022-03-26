package br.com.alura.forum.controller;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalheTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repositories.CursoRepository;
import br.com.alura.forum.repositories.TopicoRepository;

@RequestMapping("/topicos")
@RestController
public class TopicosController {
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@GetMapping
	public List<TopicoDto> lista(String nomeCurso){
//		Topico topico  = new Topico("Duvida", "Duvida Spring", new Curso("Spring","Programação"));
//		return TopicoDto.converter( Arrays.asList(topico,topico,topico));
		if(nomeCurso==null) {
			
			List<Topico> topicos =  topicoRepository.findAll();
			return TopicoDto.converter(topicos);
		
		} else {
			
			List<Topico> topicos =  topicoRepository.findByCursoNome(nomeCurso);
			return TopicoDto.converter(topicos);
		
		}
	}

	@PostMapping
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody  @Valid TopicoForm form,UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.saveAndFlush(topico);
		URI uri = uriBuilder.path("/topico/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	

	@GetMapping("/{id}")
	public ResponseEntity<DetalheTopicoDto> detalhar(@PathVariable Long id) {
		
		//Topico topico = topicoRepository.getById(id); 
		Optional<Topico>  topico = topicoRepository.findById(id);
		
		if(topico.isPresent()) {
			return ResponseEntity.ok(new DetalheTopicoDto(topico.get()));
		}
		
		return ResponseEntity.notFound().build();
				
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id,@RequestBody  @Valid AtualizacaoTopicoForm form) {
		
		Topico topico = form.atualizar(id,topicoRepository);
		return ResponseEntity.ok(new TopicoDto(topico));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity remover(@PathVariable Long id){
		
		topicoRepository.deleteById(id);
		return ResponseEntity.ok(null);
	}

}
