package br.com.kikuchi.henrique.starwarsapi.repository;

import br.com.kikuchi.henrique.starwarsapi.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Integer> {

    Optional<Report> findByAccuserIdAndAccusedId(Integer accuserId, Integer accusedId);
    boolean existsByAccuserIdAndAccusedId(Integer accuserId, Integer accusedId);
}
