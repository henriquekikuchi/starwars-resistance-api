package br.com.kikuchi.henrique.starwarsapi.repository;

import br.com.kikuchi.henrique.starwarsapi.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}
