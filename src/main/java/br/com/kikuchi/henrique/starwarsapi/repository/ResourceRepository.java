package br.com.kikuchi.henrique.starwarsapi.repository;

import br.com.kikuchi.henrique.starwarsapi.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {
}
