package Capstone.backend.repository;

import Capstone.backend.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findById(int id);

    Optional<Admin> findByName(String name);
}
