package com.auca.church.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.auca.church.domain.Ministry;

@Repository
public interface MinistryRepository extends JpaRepository<Ministry, UUID> {

    // C3
    @Query("SELECT m FROM Ministry m WHERE m.pastors IS EMPTY")
    List<Ministry> findUnusedMinistries();
}