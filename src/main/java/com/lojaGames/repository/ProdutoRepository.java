package com.lojaGames.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lojaGames.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto,Long>{

	public List<Produto> findAllByPrecoLessThanEqual(BigDecimal preco);
}
