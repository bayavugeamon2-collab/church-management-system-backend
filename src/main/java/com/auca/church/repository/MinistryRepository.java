package com.auca.church.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.auca.church.domain.Ministry;

@Repository
public interface MinistryRepository extends JpaRepository<Ministry, UUID> {
}
