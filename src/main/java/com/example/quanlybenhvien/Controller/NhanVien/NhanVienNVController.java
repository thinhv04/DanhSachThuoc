package com.example.quanlybenhvien.Controller.NhanVien;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlybenhvien.Entity.NhanVien;
import com.example.quanlybenhvien.Service.NhanVienService;

@Controller
@RequestMapping("/nhanvien")
public class NhanVienNVController {
    @Autowired
    NhanVienService nhanVienService;

    // Hiển thị trang đăng nhập
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        return "nhanvien/nhanvien-login";
    }

    // Hiển thị trang chủ nhân viên
    @GetMapping("/trangchu")
    public String showAdminHome() {
        return "nhanvien/nhanvien";
    }

    // **Hiển thị trang thông tin cá nhân**
    @GetMapping("/profile")
    public String showProfile(Model model) {
        NhanVien nhanVien = nhanVienService.getNhanVienDangNhap();
        if (nhanVien != null) {
            model.addAttribute("nhanVien", nhanVien);
        } else {
            model.addAttribute("error", "Không thể lấy thông tin nhân viên.");
        }
        return "nhanvien/profile";
    }
}
