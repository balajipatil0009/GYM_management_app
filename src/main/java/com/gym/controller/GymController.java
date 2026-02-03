package com.gym.controller;

import com.gym.model.*;
import com.gym.service.GymService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GymController {

    @Autowired
    private GymService gymService;

    // --- Dashboard ---
    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("memberCount", gymService.getMemberCount());
        model.addAttribute("todayAttendance", gymService.getTodayAttendanceCount());
        return "index"; // Corresponds to index.html (Dashboard)
    }

    // --- Members ---
    @GetMapping("/members")
    public String listMembers(Model model) {
        model.addAttribute("members", gymService.getAllMembers());
        return "members";
    }

    @GetMapping("/add-member")
    public String showAddMemberForm(Model model) {
        model.addAttribute("member", new Member());
        return "member-form";
    }

    @PostMapping("/members")
    public String saveMember(@ModelAttribute Member member) {
        gymService.saveMember(member);
        return "redirect:/members";
    }

    // --- Trainers ---
    @GetMapping("/trainers")
    public String listTrainers(Model model) {
        model.addAttribute("trainers", gymService.getAllTrainers());
        return "trainers";
    }

    @GetMapping("/add-trainer")
    public String showAddTrainerForm(Model model) {
        model.addAttribute("trainer", new Trainer());
        return "trainer-form";
    }

    @PostMapping("/trainers")
    public String saveTrainer(@ModelAttribute Trainer trainer) {
        gymService.saveTrainer(trainer);
        return "redirect:/trainers";
    }

    // --- Plans ---
    @GetMapping("/add-plan")
    public String showAddPlanForm(Model model) {
        model.addAttribute("plan", new Plan());
        return "plan-form";
    }

    @PostMapping("/plans")
    public String savePlan(@ModelAttribute Plan plan) {
        gymService.savePlan(plan);
        return "redirect:/"; // Redirect to dashboard or maybe a plans list if we had one
    }

    // --- Enrollments ---
    @GetMapping("/enrollments")
    public String listEnrollments(Model model) {
        model.addAttribute("enrollments", gymService.getAllEnrollments());
        return "enrollments";
    }

    @GetMapping("/enroll-member")
    public String showEnrollmentForm(Model model) {
        model.addAttribute("members", gymService.getAllMembers());
        model.addAttribute("plans", gymService.getAllPlans());
        // We'll capture memberId and planId from the form
        return "enrollment-form";
    }

    @PostMapping("/enrollments")
    public String createEnrollment(@RequestParam Long memberId, @RequestParam Long planId) {
        gymService.createEnrollment(memberId, planId);
        return "redirect:/enrollments";
    }

    // --- Attendance ---
    @GetMapping("/log-attendance")
    public String showAttendanceForm(Model model) {
        model.addAttribute("members", gymService.getAllMembers());
        return "attendance-form";
    }

    @PostMapping("/attendance")
    public String logAttendance(@RequestParam Long memberId) {
        gymService.logAttendance(memberId);
        return "redirect:/"; // Redirect to dashboard to see updated count
    }
}
