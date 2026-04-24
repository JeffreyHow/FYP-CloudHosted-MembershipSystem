package com.fyp.membership.membershipsystem.controller;

import com.fyp.membership.membershipsystem.entity.Member;
import com.fyp.membership.membershipsystem.service.AdminRegisterService;
import com.fyp.membership.membershipsystem.service.AuthService;
import com.fyp.membership.membershipsystem.service.MemberService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final AuthService authService;
    private final AdminRegisterService adminRegisterService;
    private final MemberService memberService;

    public AuthController(
            AuthService authService,
            AdminRegisterService adminRegisterService,MemberService memberService
    ) {
        this.authService = authService;
        this.adminRegisterService = adminRegisterService;
        this.memberService = memberService;
    }

    // Admin Register & Login Nav
    @GetMapping("/admin/login")
    public String adminLoginPage() {
        return "admin-login";
    }
    @GetMapping("/admin/register")
    public String adminRegisterPage() {
        return "admin-register";
    }
    @PostMapping("/admin/register")
    public String registerAdmin(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword
    ) {
        adminRegisterService.register(name, email, password,confirmPassword);
        return "redirect:/admin/login";
    }

    // Member Register & Login Nav
    @GetMapping("/member/login")
    public String memberLoginPage() {
        return "member-login";
    }
    @GetMapping("/member/register")
    public String memberRegisterPage() {
        return "member-register";
    }
    @PostMapping("/member/register")
    public String registerMember(
            @RequestParam String name,
            @RequestParam String phone,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            RedirectAttributes redirectAttributes
    ) {

        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Password not match");
            return "redirect:/member/register";
        }

        try {
            memberService.registerMember(name, phone, email, password);
        } catch (IllegalArgumentException e) {

            if (e.getMessage().contains("Email")) {
                redirectAttributes.addFlashAttribute("error", "Email already exists");
                return "redirect:/member/register";
            }

            if (e.getMessage().contains("Phone")) {
                redirectAttributes.addFlashAttribute("error", "Phone number already exists");
                return "redirect:/member/register";
            }
        }

        return "redirect:/member/login";
    }
}
