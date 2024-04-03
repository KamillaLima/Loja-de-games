package com.lojaGames.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lojaGames.model.Categoria;
import com.lojaGames.repository.CategoriaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public ResponseEntity<List<Categoria>> getAll(){
		return ResponseEntity.status(HttpStatus.OK).body(categoriaRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> getById(@PathVariable Long id){
		Optional<Categoria> categoriaBanco = categoriaRepository.findById(id);
		if(categoriaBanco.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(categoriaBanco.get());
	}
	
	
	@GetMapping("tipo/{tipo}")
	public ResponseEntity<List<Categoria>> getByName(@PathVariable String tipo){
		return ResponseEntity.status(HttpStatus.OK).body(categoriaRepository.findAllByTipoContainingIgnoreCase(tipo));
	}
	
	
	@PostMapping
	public ResponseEntity<Categoria> post(@Valid @RequestBody Categoria categoria){
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRepository.save(categoria));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id){
		Optional<Categoria> categoriaBanco = categoriaRepository.findById(id);
		if(categoriaBanco.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		categoriaRepository.delete(categoriaBanco.get());
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PutMapping
	public ResponseEntity<Categoria> put(@Valid @RequestBody Categoria categoria){
		Optional<Categoria> categoriaBanco = categoriaRepository.findById(categoria.getId());
		if(categoriaBanco.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(categoriaRepository.save(categoria));
	}
	
} 
