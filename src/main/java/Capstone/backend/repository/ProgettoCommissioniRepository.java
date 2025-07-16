package Capstone.backend.repository;


import Capstone.backend.model.ProgettoCommissioni;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgettoCommissioniRepository extends JpaRepository<ProgettoCommissioni, Long> {
    Optional<ProgettoCommissioni> findById(int id);
    List<ProgettoCommissioni> findAllByOrderByDataDesc();
}
