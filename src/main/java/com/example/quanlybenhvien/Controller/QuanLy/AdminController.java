package com.example.quanlybenhvien.Controller.QuanLy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;

import com.example.quanlybenhvien.Service.QuanLyService;

@Controller
public class AdminController {
    @Autowired
    QuanLyService nguoiDungService;

    @GetMapping("/quanly/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {
        if (error != null) {
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu!");
        }
        if (logout != null) {
            model.addAttribute("message", "Đã đăng xuất thành công!");
        }
        return "admin/admin-login";
    }

    @GetMapping("/quanly/trangchu")
    public String showAdminHome() {
        return "admin/admin";
    }
}
