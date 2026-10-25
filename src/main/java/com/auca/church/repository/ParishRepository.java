package com.auca.church.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.auca.church.domain.Parish;

@Repository
public interface ParishRepository extends JpaRepository<Parish, UUID> {

    // C2
    @Query("SELECT p FROM Parish p WHERE NOT EXISTS (SELECT pas FROM Pastor pas WHERE pas.parish = p) ORDER BY p.parishNumber")
    List<Parish> findFreeParishes();

    // D4
    Optional<Parish> findByParishNumber(int parishNumber);
}