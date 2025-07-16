package Capstone.backend.repository;


import Capstone.backend.model.ProgettoPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgettoPortfolioRepository extends JpaRepository<ProgettoPortfolio, Integer> {
    Optional<ProgettoPortfolio> findById(int id);
    List<ProgettoPortfolio> findAllByOrderByDataDesc();
}
