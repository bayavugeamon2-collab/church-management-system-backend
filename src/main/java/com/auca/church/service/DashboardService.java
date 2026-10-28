package com.auca.church.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.auca.church.domain.VisitationStatus;
import com.auca.church.repository.MemberRepository;
import com.auca.church.repository.PastorRepository;
import com.auca.church.repository.ParishRepository;
import com.auca.church.repository.VisitationRepository;
import com.auca.church.repository.VisitReportRepository;

@Service
public class DashboardService {

    private final MemberRepository memberRepository;
    private final PastorRepository pastorRepository;
    private final ParishRepository parishRepository;
    private final VisitationRepository visitationRepository;
    private final VisitReportRepository visitReportRepository;

    public DashboardService(MemberRepository memberRepository,
            PastorRepository pastorRepository,
            ParishRepository parishRepository,
            VisitationRepository visitationRepository,
            VisitReportRepository visitReportRepository) {
        this.memberRepository = memberRepository;
        this.pastorRepository = pastorRepository;
        this.parishRepository = parishRepository;
        this.visitationRepository = visitationRepository;
        this.visitReportRepository = visitReportRepository;
    }

    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();

        summary.put("members", memberRepository.count());
        summary.put("pastors", pastorRepository.count());
        summary.put("freeParishes", (long) parishRepository.findFreeParishes().size());
        summary.put("visitationsToday",
                (long) visitationRepository.findByVisitDateBetweenOrderByVisitDateAsc(
                        LocalDate.now(), LocalDate.now()).size());
        summary.put("requested",
                (long) visitationRepository.findByStatusOrderByVisitDateAsc(
                        VisitationStatus.REQUESTED).size());
        summary.put("visitReports", visitReportRepository.count());

        return summary;
    }
}