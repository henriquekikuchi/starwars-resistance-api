package br.com.kikuchi.henrique.starwarsapi.repository;

import br.com.kikuchi.henrique.starwarsapi.model.Rebel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RebelRepository extends JpaRepository<Rebel, Integer> {

    Page<Rebel> findAll(Pageable pageable);

    List<Rebel> findAllByBetrayerIsTrue();
    List<Rebel> findAllByBetrayerIsFalse();
}
