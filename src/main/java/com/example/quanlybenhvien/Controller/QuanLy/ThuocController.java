package com.example.quanlybenhvien.Controller.QuanLy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.quanlybenhvien.Entity.KhoThuoc;
import com.example.quanlybenhvien.Entity.Thuoc;
import com.example.quanlybenhvien.Service.KhoThuocService;
import com.example.quanlybenhvien.Service.NhapThuocService;
import com.example.quanlybenhvien.Service.ThuocService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/quanly/trangchu/thuoc")
public class ThuocController {
    private final ThuocService thuocService;

    public ThuocController(ThuocService thuocService) {
        this.thuocService = thuocService;
    }

    @Autowired
    private KhoThuocService khoThuocService;

    @Autowired 
    private NhapThuocService nhapThuocService;

    @GetMapping
    public String danhsach(Model model) {
        model.addAttribute("thuocs", thuocService.timkiemthuoc(""));
        return "admin/trangthuoc";
    }

    @GetMapping("/them")
    public String giaodienthem(Model model) {
        model.addAttribute("thuoc", new Thuoc());
        return "admin/trangthuoc";
    }

    @PostMapping("/them")
    public String themthuocmoi(@ModelAttribute Thuoc thuoc, RedirectAttributes redirectAttributes) {
        thuocService.themthuocmoi(thuoc);
        redirectAttributes.addFlashAttribute("message", "Thêm thuốc thành công!");
        return "redirect:/quanly/trangchu/thuoc";
    }

    @GetMapping("/timkiem")
    public String timkiemthuoc(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        List<Thuoc> ketQua = thuocService.timkiemthuoc(keyword);
        model.addAttribute("thuocs", ketQua);
        model.addAttribute("keyword", keyword); // Giữ lại từ khóa trên giao diện
        return "admin/trangthuoc";
    }

    @GetMapping("/capnhat/{maThuoc}")
    public String giaodiencapnhat(@PathVariable String maThuoc, Model model) {
        Optional<Thuoc> thuoc = thuocService.timtheomathuoc(maThuoc);
        thuoc.ifPresent(value -> model.addAttribute("thuoc", value));
        return thuoc.isPresent() ? "thuoc/capnhat" : "redirect:/quanly/trangchu/thuoc";
    }

    // @PostMapping("/capnhat/{maThuoc}")
    // public String capNhatThuoc(@PathVariable String maThuoc, @ModelAttribute
    // Thuoc thuoc) {
    // thuocService.capNhatThuoc(maThuoc, thuoc);
    // return "redirect:/thuoc";
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
        return "redirect:/quanly/trangchu/thuoc";
    }

    @PostMapping("/xoa/{maThuoc}")
    public String xoathuoc(@PathVariable String maThuoc, RedirectAttributes redirectAttributes) {
        try {
            thuocService.xoathuoc(maThuoc);
            redirectAttributes.addFlashAttribute("message", "Xóa thành công thuốc có mã: " + maThuoc);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/quanly/trangchu/thuoc";
    }

    @GetMapping("/khothuoc")
    public String danhSachKhoThuoc(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<KhoThuoc> khoThuocList = (keyword != null && !keyword.isEmpty())
                ? khoThuocService.searchKhoThuoc(keyword)
                : khoThuocService.getAllKhoThuoc();
        model.addAttribute("khoThuocList", khoThuocList);
        model.addAttribute("keyword", keyword);
        return "admin/khothuoc";
    }

    @GetMapping("/nhapthuoc")
    public String danhSachNhapThuoc(Model model) {
        model.addAttribute("nhapThuocList", nhapThuocService.getAllNhapThuoc());
        return "admin/nhapthuoc";
    }
}
