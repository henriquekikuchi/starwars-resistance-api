package br.com.kikuchi.henrique.starwarsapi.repository;

import br.com.kikuchi.henrique.starwarsapi.model.RebelResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RebelResourceRepository extends JpaRepository<RebelResource, Integer> {
}
