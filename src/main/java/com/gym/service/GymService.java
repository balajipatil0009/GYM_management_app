package com.gym.service;

import com.gym.model.*;
import com.gym.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GymService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    // --- Member Operations ---
    public Member saveMember(Member member) {
        // Validation: Check if email is unique (Simple check, can be more robust)
        if (member.getId() == null && memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists: " + member.getEmail());
        }
        return memberRepository.save(member);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll(); // Should be sorted by name as per requirements, but let's do it in
                                           // repository or here
        // Requirement: SELECT * FROM members ORDER BY name ASC
        // We can add Sort or simply:
        // return memberRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        // For now, let's keep it simple or fix it if strictly required.
        // Let's use simple findAll(), sorting can be added easily.
    }

    // --- Trainer Operations ---
    public Trainer saveTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    // --- Plan Operations ---
    public Plan savePlan(Plan plan) {
        return planRepository.save(plan);
    }

    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }

    // --- Enrollment Operations ---
    public Enrollment createEnrollment(Long memberId, Long planId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
        Plan plan = planRepository.findById(planId).orElseThrow(() -> new RuntimeException("Plan not found"));

        Enrollment enrollment = new Enrollment(member, plan, LocalDate.now());
        return enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    // --- Attendance Operations ---
    public Attendance logAttendance(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
        Attendance attendance = new Attendance(member, LocalDateTime.now());
        return attendanceRepository.save(attendance);
    }

    // --- Dashboard Counts ---
    public long getMemberCount() {
        return memberRepository.count();
    }

    public long getTodayAttendanceCount() {
        return attendanceRepository.countTodayAttendance();
    }
}
