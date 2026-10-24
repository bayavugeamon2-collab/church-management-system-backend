package com.auca.church.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.auca.church.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {

    // B1
    List<Member> findByCellIgnoreCaseOrderByLastNameAsc(String cell);

    // C4
    @Query("SELECT DISTINCT v.member FROM Visitation v WHERE v.pastor.id = :pastorId")
    List<Member> findMembersOfPastor(@Param("pastorId") UUID pastorId);
}