package com.example.quanlybenhvien.Controller.NhanVien;

import com.example.quanlybenhvien.Entity.NhanVien;
import com.example.quanlybenhvien.Entity.NhapThuoc;
import com.example.quanlybenhvien.Service.NhapThuocService;
import com.example.quanlybenhvien.Service.KhoThuocService;
import com.example.quanlybenhvien.Service.NhanVienService;
import com.example.quanlybenhvien.Dao.ThuocDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/nhanvien/trangchu/nhapthuoc")
public class NhapThuocController {
    
    @Autowired
    private NhapThuocService nhapThuocService;
    
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
        if (nhanVienDangNhap == null) {
            return "redirect:/nhanvien/login";
        }
        model.addAttribute("nhanVien", nhanVienDangNhap);
        model.addAttribute("nhapThuoc", new NhapThuoc());
        model.addAttribute("thuocList", thuocRepository.findAll());
        model.addAttribute("nhapThuocList", nhapThuocService.getAllNhapThuoc());
        return "nhanvien/nhapthuoc";
    }

    @PostMapping("/them")
public String themNhapThuoc(@ModelAttribute("nhapThuoc") NhapThuoc nhapThuoc,
                            RedirectAttributes redirectAttributes) {

    NhanVien nhanVienDangNhap = nhanVienService.getNhanVienDangNhap();

    if (nhanVienDangNhap == null) {
        // Nếu không tìm thấy nhân viên → trả về lỗi
        redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy thông tin nhân viên đăng nhập!");
        return "redirect:/nhanvien/trangchu/nhapthuoc/form";
    }

    // Gán nhân viên cho bản ghi nhập thuốc
    nhapThuoc.setNhanVien(nhanVienDangNhap);

    try {
        // Gọi service lưu nhập thuốc
        nhapThuocService.themNhapThuoc(nhapThuoc);

        // Nếu thành công → gán successMessage
        redirectAttributes.addFlashAttribute("successMessage", " ✅ Nhập thuốc thành công!");
    } catch (Exception e) {
        // Nếu có lỗi khi lưu → gán errorMessage
        redirectAttributes.addFlashAttribute("errorMessage", " ❌ Lỗi khi nhập thuốc: " + e.getMessage());
    }

    // Quay lại form nhập thuốc, hiển thị thông báo
    return "redirect:/nhanvien/trangchu/nhapthuoc/form";
}

}
