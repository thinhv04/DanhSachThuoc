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
    model.addAttribute("nhanVien", nhanVienDangNhap);

    // Lấy danh sách nhập thuốc
    List<NhapThuoc> danhSachNhapThuoc = nhapThuocService.getAllNhapThuoc();
    System.out.println("📌 Dữ liệu hiển thị trên giao diện: " + danhSachNhapThuoc);
    model.addAttribute("nhapThuocList", danhSachNhapThuoc);

    model.addAttribute("nhapThuoc", new NhapThuoc());
    model.addAttribute("khoThuocList", khoThuocService.getAllKhoThuoc());
    model.addAttribute("thuocList", thuocRepository.findAll());

    return "nhanvien/nhapthuoc";
    }

    @PostMapping("/them")
    public String themNhapThuoc(@ModelAttribute("nhapThuoc") NhapThuoc nhapThuoc, RedirectAttributes redirectAttributes) {
        try {
            nhapThuocService.themNhapThuoc(nhapThuoc);
            redirectAttributes.addFlashAttribute("successMessage", "Nhập thuốc thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        return "redirect:/nhanvien/trangchu/nhapthuoc/form"; // 🔥 Quay lại form để hiển thị danh sách
    }
}
