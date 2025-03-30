package com.example.quanlybenhvien.Controller.NhanVien;

import com.example.quanlybenhvien.Service.ThuocService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.quanlybenhvien.Entity.Thuoc;
import com.example.quanlybenhvien.Service.ThuocService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/nhanvien/trangchu/thuoc")
public class NhanVienThuocController {
    private final ThuocService thuocService;

    public NhanVienThuocController(ThuocService thuocService) { 
        this.thuocService = thuocService;
    }

    @GetMapping
    public String danhsach(Model model) {
        model.addAttribute("thuocs", thuocService.timkiemthuoc(""));
        return "nhanvien/trangthuoc";
    }

    @GetMapping("/them")
    public String giaodienthem(Model model) {
        model.addAttribute("thuoc", new Thuoc());
        return "nhanvien/trangthuoc";
    }

    @PostMapping("/them")
    public String themthuocmoi(@ModelAttribute Thuoc thuoc, RedirectAttributes redirectAttributes) {
        thuocService.themthuocmoi(thuoc);
        redirectAttributes.addFlashAttribute("message", "Thêm thuốc thành công!");
        return "redirect:/nhanvien/trangchu/thuoc";
    }

    @GetMapping("/timkiem")
    public String timkiemthuoc(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        List<Thuoc> ketQua = thuocService.timkiemthuoc(keyword);
        model.addAttribute("thuocs", ketQua);
        model.addAttribute("keyword", keyword); // Giữ lại từ khóa trên giao diện
        return "nhanvien/trangthuoc";
    }


    @GetMapping("/capnhat/{maThuoc}")
    public String giaodiencapnhat(@PathVariable String maThuoc, Model model) {
        Optional<Thuoc> thuoc = thuocService.timtheomathuoc(maThuoc);
        thuoc.ifPresent(value -> model.addAttribute("thuoc", value));
        return thuoc.isPresent() ? "thuoc/capnhat" : "redirect:/nhanvien/trangchu/thuoc";
    }

    // @PostMapping("/capnhat/{maThuoc}")
    // public String capNhatThuoc(@PathVariable String maThuoc, @ModelAttribute Thuoc thuoc) {
    //     thuocService.capNhatThuoc(maThuoc, thuoc);
    //     return "redirect:/thuoc";
    // }
    @PostMapping("/capnhat/{maThuoc}")
    public String capnhatthuoc(@PathVariable("maThuoc") String maThuoc,
                            @ModelAttribute Thuoc thuocCapNhat,
                            RedirectAttributes redirectAttributes) {
        try {
            Thuoc thuoc = thuocService.capnhatthuoc(maThuoc, thuocCapNhat);
            redirectAttributes.addFlashAttribute("message", "Cập nhật thành công: " + thuoc.getTenThuoc());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/nhanvien/trangchu/thuoc";
    }


    @PostMapping("/xoa/{maThuoc}")
    public String xoathuoc(@PathVariable String maThuoc, RedirectAttributes redirectAttributes) {
        try {
            thuocService.xoathuoc(maThuoc);
            redirectAttributes.addFlashAttribute("message", "Xóa thành công thuốc có mã: " + maThuoc);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/nhanvien/trangchu/thuoc";
    }
}
