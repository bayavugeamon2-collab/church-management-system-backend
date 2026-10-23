package com.auca.church.domain;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "visitation")
public class Visitation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "visit_date")
    private LocalDate visitDate;

    @Enumerated(EnumType.STRING)
    private VisitationStatus status;

    private String purpose;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "pastor_id")
    private Pastor pastor;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public LocalDate getVisitDate() { return visitDate; }
    public void setVisitDate(LocalDate visitDate) { this.visitDate = visitDate; }

    public VisitationStatus getStatus() { return status; }
    public void setStatus(VisitationStatus status) { this.status = status; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }

    public Pastor getPastor() { return pastor; }
    public void setPastor(Pastor pastor) { this.pastor = pastor; }
}
