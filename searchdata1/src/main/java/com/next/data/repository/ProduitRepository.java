package com.next.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.next.data.model.Produit;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long>{
	
	public Page<Produit> findByDesignationContains(String mc, Pageable pageable); 
}
