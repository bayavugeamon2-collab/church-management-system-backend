package com.auca.church.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.auca.church.domain.Pastor;

@Repository
public interface PastorRepository extends JpaRepository<Pastor, UUID> {

    // C1
    @Query("SELECT p FROM Pastor p JOIN p.ministries m WHERE LOWER(m.name) = LOWER(:name)")
    List<Pastor> findByMinistryName(@Param("name") String name);
}