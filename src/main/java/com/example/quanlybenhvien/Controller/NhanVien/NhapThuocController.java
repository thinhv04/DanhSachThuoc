package com.example.quanlybenhvien.Controller.NhanVien;

import com.example.quanlybenhvien.Entity.NhanVien;
import com.example.quanlybenhvien.Entity.NhapThuoc;
import com.example.quanlybenhvien.Dao.ThuocDao;
import com.example.quanlybenhvien.Dao.NhapThuocDao;
import com.example.quanlybenhvien.Service.NhapThuocService;
import com.example.quanlybenhvien.Service.KhoThuocService;
import com.example.quanlybenhvien.Service.NhanVienService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/nhanvien/trangchu/nhapthuoc/")
public class NhapThuocController {
    
    @Autowired
    private NhapThuocService nhapThuocService;
    
    @Autowired
    private NhapThuocDao nhapThuocRepository;
    
    @Autowired
    private ThuocDao thuocRepository;
    
    @Autowired
    private KhoThuocService khoThuocService;

    @Autowired
    private NhanVienService nhanVienService;

    @GetMapping("/form")
    public String hienThiFormNhapThuoc(Model model) {
        // Lấy thông tin nhân viên đang đăng nhập
        NhanVien nhanVienDangNhap = nhanVienService.getNhanVienDangNhap();
        System.out.println("📌 Nhân viên đăng nhập: " + nhanVienDangNhap);

        if (nhanVienDangNhap == null) {
            model.addAttribute("errorMessage", "⚠ Không tìm thấy nhân viên đăng nhập!");
            return "redirect:/nhanvien/login"; // Chuyển hướng về trang đăng nhập nếu không có nhân viên
        }

        model.addAttribute("nhanVien", nhanVienDangNhap);
        model.addAttribute("nhapThuoc", new NhapThuoc());
        model.addAttribute("thuocList", thuocRepository.findAll());
        model.addAttribute("nhapThuocList", nhapThuocService.getAllNhapThuoc());

        return "nhanvien/nhapthuoc";
    }

    @PostMapping("/them")
public String themNhapThuoc(@ModelAttribute("nhapThuoc") NhapThuoc nhapThuoc, RedirectAttributes redirectAttributes) {
    // Lấy nhân viên đang đăng nhập từ session
    NhanVien nhanVienDangNhap = nhanVienService.getNhanVienDangNhap();

    if (nhanVienDangNhap == null) {
        redirectAttributes.addFlashAttribute("errorMessage", "⚠ Không tìm thấy thông tin nhân viên đăng nhập!");
        return "redirect:/nhanvien/trangchu/nhapthuoc/form";
    }

    // Gán nhân viên vào NhapThuoc
    nhapThuoc.setNhanVien(nhanVienDangNhap);

    System.out.println("📌 Dữ liệu nhận được sau khi gán nhân viên: " + nhapThuoc);

    try {
        nhapThuocService.themNhapThuoc(nhapThuoc);
        redirectAttributes.addFlashAttribute("successMessage", "✅ Nhập thuốc thành công!");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("errorMessage", "❌ Lỗi: " + e.getMessage());
    }

    return "redirect:/nhanvien/trangchu/nhapthuoc/form";
}


}
