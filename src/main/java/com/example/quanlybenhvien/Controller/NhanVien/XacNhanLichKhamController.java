package com.example.quanlybenhvien.Controller.NhanVien;

import com.example.quanlybenhvien.Entity.LichKham;
import com.example.quanlybenhvien.Entity.NhanVien;
import com.example.quanlybenhvien.Service.LichKhamService;
import com.example.quanlybenhvien.Service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/nhanvien/trangchu/lichkham")
public class XacNhanLichKhamController {

    @Autowired
    private LichKhamService lichKhamService;

    @Autowired
    private NhanVienService nhanVienService;

    @GetMapping("/cho-xac-nhan")
    public String hienThiLichKhamChoXacNhan(Model model) {
        NhanVien nhanVienDangNhap = nhanVienService.getNhanVienDangNhap();
        if (nhanVienDangNhap == null) {
            return "redirect:/nhanvien/login";
        }
        model.addAttribute("nhanVien", nhanVienDangNhap);
        model.addAttribute("activeTab", "cho-xac-nhan");
        model.addAttribute("lichKhams", lichKhamService.getLichKhamChoXacNhan());
        return "nhanvien/lichkham-xacnhan";
    }

    @PostMapping("/xac-nhan")
    public String xacNhanLichKham(@RequestParam int maLichKham,
            @RequestParam String trangThai,
            @RequestParam String ghiChu,
            RedirectAttributes redirectAttributes) {
        NhanVien nhanVienDangNhap = nhanVienService.getNhanVienDangNhap();
        if (nhanVienDangNhap == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy thông tin nhân viên đăng nhập!");
            return "redirect:/nhanvien/login";
        }
        try {
            // Truyền thêm nhanVienDangNhap vào phương thức xác nhận
            lichKhamService.xacNhanLichKham(maLichKham, trangThai, ghiChu, nhanVienDangNhap);
            redirectAttributes.addFlashAttribute("successMessage", "Lịch khám đã được xác nhận và chuyển qua bác sĩ!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        return "redirect:/nhanvien/trangchu/lichkham/cho-xac-nhan";
    }

    @GetMapping("/da-huy")
    public String hienThiLichKhamDaHuy(Model model) {
        NhanVien nhanVienDangNhap = nhanVienService.getNhanVienDangNhap();
        if (nhanVienDangNhap == null) {
            return "redirect:/nhanvien/login";
        }
         model.addAttribute("nhanVien", nhanVienDangNhap);
        model.addAttribute("lichKhams", lichKhamService.getLichKhamDaHuy());
        model.addAttribute("activeTab", "da-huy");
        return "nhanvien/lichkham-dahuy"; // Trang JSP/HTML bạn sẽ tạo
    }
    @GetMapping("/da-hoan-thanh")
    public String hienThiLichKhamDaHoanThanh(Model model) {
        NhanVien nhanVienDangNhap = nhanVienService.getNhanVienDangNhap();
        if (nhanVienDangNhap == null) {
            return "redirect:/nhanvien/login";
        }
         model.addAttribute("nhanVien", nhanVienDangNhap);
        model.addAttribute("lichKhams", lichKhamService.getLichKhamHoanThanh());
        model.addAttribute("activeTab", "da-hoan-thanh");
        return "nhanvien/lich-kham-da-hoan-thanh"; // Trang JSP/HTML bạn sẽ tạo
    }
    @GetMapping("/cho-bac-si")
    public String hienThiLichKhamChoBacSi(Model model) {
        NhanVien nhanVienDangNhap = nhanVienService.getNhanVienDangNhap();
        if (nhanVienDangNhap == null) {
            return "redirect:/nhanvien/login";
        }
         model.addAttribute("nhanVien", nhanVienDangNhap);
        model.addAttribute("activeTab", "cho-bac-si");
        model.addAttribute("lichKhams", lichKhamService.getChoBacSi());
        return "nhanvien/lichkham-chobacsi"; // Trang JSP/HTML bạn sẽ tạo
    }
}
