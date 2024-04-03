package com.lojaGames.controller;

import java.math.BigDecimal;
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
import com.lojaGames.model.Produto;
import com.lojaGames.repository.CategoriaRepository;
import com.lojaGames.repository.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public ResponseEntity<List<Produto>> getAll() {
		return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id) {
		return produtoRepository.findById(id).map(produto -> ResponseEntity.status(HttpStatus.OK).body(produto))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	
	@GetMapping("/preco/{preco}")
	public ResponseEntity<List<Produto>> getByPreco(@PathVariable BigDecimal preco){
		return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.findAllByPrecoLessThanEqual(preco));
	
	}
	

	@PostMapping
	public ResponseEntity<Produto> post(@Valid @RequestBody Produto produto) {
		if(categoriaRepository.existsById(produto.getCategoria().getId())) {
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PutMapping
	public ResponseEntity<Produto> put(@Valid @RequestBody Produto produto) {
	    // Verificar se a categoria do produto existe
	    Optional<Categoria> categoriaOptional = categoriaRepository.findById(produto.getCategoria().getId());
	    if (categoriaOptional.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	    }

	    Optional<Produto> produtoBanco = produtoRepository.findById(produto.getId());
	    if (produtoBanco.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }

	    Produto produtoAtualizado = produtoRepository.save(produto);
	    return ResponseEntity.status(HttpStatus.OK).body(produtoAtualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		Optional<Produto> produtoBanco = produtoRepository.findById(id);
		if (produtoBanco.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		produtoRepository.delete(produtoBanco.get());
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
