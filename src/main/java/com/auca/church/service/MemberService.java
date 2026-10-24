package com.auca.church.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.auca.church.domain.Member;
import com.auca.church.repository.MemberRepository;
import com.auca.church.repository.PastorRepository;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PastorRepository pastorRepository;

    public MemberService(MemberRepository memberRepository, PastorRepository pastorRepository) {
        this.memberRepository = memberRepository;
        this.pastorRepository = pastorRepository;
    }

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Optional<Member> findById(UUID id) {
        return memberRepository.findById(id);
    }

    public String update(UUID id, Member body) {
        Optional<Member> opt = memberRepository.findById(id);
        if (opt.isEmpty()) {
            return "The member with that id does not exist";
        }
        Member existing = opt.get();
        existing.setFirstName(body.getFirstName());
        existing.setLastName(body.getLastName());
        existing.setMemberNumber(body.getMemberNumber());
        existing.setPhone(body.getPhone());
        existing.setCell(body.getCell());
        memberRepository.save(existing);
        return "Member updated successfully";
    }

    public String delete(UUID id) {
        if (!memberRepository.existsById(id)) {
            return "The member with that id does not exist";
        }
        memberRepository.deleteById(id);
        return "Member deleted successfully";
    }

    // B1
    public List<Member> findByCell(String cell) {
        return memberRepository.findByCellIgnoreCaseOrderByLastNameAsc(cell);
    }

    // C4
    public Object findMembersOfPastor(UUID pastorId) {
        if (!pastorRepository.existsById(pastorId)) {
            return "The pastor with that id does not exist";
        }
        return memberRepository.findMembersOfPastor(pastorId);
    }
}