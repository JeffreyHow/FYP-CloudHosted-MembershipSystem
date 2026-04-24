package com.fyp.membership.membershipsystem.controller;

import com.fyp.membership.membershipsystem.entity.Coupon;
import com.fyp.membership.membershipsystem.entity.DiscountType;
import com.fyp.membership.membershipsystem.service.CouponService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/coupons")
public class CouponsController {

    private final CouponService couponService;

    public CouponsController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping
    public String couponsPage(Model model) {

        // === Dashboard cards data ===
        long totalCoupons = couponService.getTotalCoupons();
        long totalRedemptions = couponService.getTotalRedemptions();
        double usageRate = couponService.getUsageRate();

        model.addAttribute("totalCoupons", totalCoupons);
        model.addAttribute("totalRedemptions", totalRedemptions);
        model.addAttribute("usageRate", usageRate);

        // === layout control ===
        model.addAttribute("content", "admin-coupons");
        model.addAttribute("activePage", "coupons");

        // ===== Coupon Table =====
        List<Coupon> coupons = couponService.findAllCoupons();
        model.addAttribute("coupons", coupons);
        return "admin-layout";
    }

    @GetMapping("/add")
    public String addCouponForm(Model model) {

        model.addAttribute("coupon", new Coupon());
        model.addAttribute("discountTypes", DiscountType.values());
        model.addAttribute("content", "admin-coupons-add");

        return "admin-layout";
    }

    @PostMapping("/add")
    public String addCoupon(@ModelAttribute Coupon coupon) {

        couponService.create(coupon);

        return "redirect:/admin/coupons";
    }

    @GetMapping("/edit/{id}")
    public String editCouponForm(@PathVariable Long id, Model model) {

        Coupon coupon = couponService.findById(id);

        model.addAttribute("coupon", coupon);
        model.addAttribute("discountTypes", DiscountType.values());
        model.addAttribute("content", "admin-coupons-edit");

        return "admin-layout";
    }

    @PostMapping("/edit/{id}")
    public String updateCoupon(@PathVariable Long id,
                               @ModelAttribute Coupon coupon) {

        couponService.update(id, coupon);
        return "redirect:/admin/coupons";
    }

    @PostMapping("/delete/{id}")
    public String deleteCoupon(@PathVariable Long id) {

        couponService.delete(id);
        return "redirect:/admin/coupons";
    }
}
