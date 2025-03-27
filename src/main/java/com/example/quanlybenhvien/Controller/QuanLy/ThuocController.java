package com.example.quanlybenhvien.Controller.QuanLy;

import com.example.quanlybenhvien.Entity.KhoThuoc;
import com.example.quanlybenhvien.Entity.Thuoc;
import com.example.quanlybenhvien.Service.KhoThuocService;
import com.example.quanlybenhvien.Service.NhapThuocService;
import com.example.quanlybenhvien.Service.ThuocService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/quanly/trangchu/trangthuoc")
public class ThuocController {
    private final ThuocService thuocService;
    private final NhapThuocService nhapThuocService;
    private final KhoThuocService khoThuocService;

    public ThuocController(ThuocService thuocService, NhapThuocService nhapThuocService, KhoThuocService khoThuocService) {
        this.thuocService = thuocService;
        this.nhapThuocService = nhapThuocService;
        this.khoThuocService = khoThuocService;
    }

    @GetMapping
    public String danhsach1(Model model) {
        model.addAttribute("thuocs", thuocService.timkiemthuoc(""));
        return "admin/trangthuoc";
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

    @PostMapping("/khothuoc/update/{maThuoc}")
    public String updateSoLuong(@PathVariable("maThuoc") String maThuoc,
                                @RequestParam("soLuongMoi") int soLuongMoi,
                                RedirectAttributes redirectAttributes) {
        khoThuocService.updateSoLuongThuoc(maThuoc, soLuongMoi);
        redirectAttributes.addFlashAttribute("message", "Cập nhật số lượng thành công!");
        return "redirect:/quanly/trangchu/thuoc/khothuoc";
    }

    @GetMapping("/nhapthuoc")
    public String danhSachNhapThuoc(Model model) {
        model.addAttribute("nhapThuocList", nhapThuocService.getAllNhapThuoc());
        return "admin/nhapthuoc";
    }

    @GetMapping("/thuoc")
    public String danhsach(Model model) {
        model.addAttribute("thuocs", thuocService.timkiemthuoc(""));
        return "admin/themthuoc";
    }

    @GetMapping("/thuoc/them")
    public String giaodienthem(Model model) {
        model.addAttribute("thuoc", new Thuoc());
        return "admin/themthuoc";
    }

    @PostMapping("/thuoc/them")
    public String themthuocmoi(@ModelAttribute Thuoc thuoc, RedirectAttributes redirectAttributes) {
        thuocService.themthuocmoi(thuoc);
        redirectAttributes.addFlashAttribute("message", "Thêm thuốc thành công!");
        return "redirect:/quanly/trangchu/trangthuoc/thuoc";
    }

    @GetMapping("/thuoc/timkiem")
    public String timkiemthuoc(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        List<Thuoc> ketQua = thuocService.timkiemthuoc(keyword);
        model.addAttribute("thuocs", ketQua);
        model.addAttribute("keyword", keyword);
        return "admin/trangthuoc";
    }

    @GetMapping("/thuoc/capnhat/{maThuoc}")
    public String giaodiencapnhat(@PathVariable String maThuoc, Model model) {
        Optional<Thuoc> thuoc = thuocService.timtheomathuoc(maThuoc);
        thuoc.ifPresent(value -> model.addAttribute("thuoc", value));
        return thuoc.isPresent() ? "thuoc/capnhat" : "redirect:/quanly/trangchu/trangthuoc/thuoc";
    }

    @PostMapping("/thuoc/capnhat/{maThuoc}")
    public String capnhatthuoc(@PathVariable("maThuoc") String maThuoc,
                               @ModelAttribute Thuoc thuocCapNhat,
                               RedirectAttributes redirectAttributes) {
        try {
            Thuoc thuoc = thuocService.capnhatthuoc(maThuoc, thuocCapNhat);
            redirectAttributes.addFlashAttribute("message", "Cập nhật thành công: " + thuoc.getTenThuoc());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/quanly/trangchu/trangthuoc/thuoc";
    }

    @PostMapping("/thuoc/xoa/{maThuoc}")
    public String xoathuoc(@PathVariable String maThuoc, RedirectAttributes redirectAttributes) {
        try {
            thuocService.xoathuoc(maThuoc);
            redirectAttributes.addFlashAttribute("message", "Xóa thành công thuốc có mã: " + maThuoc);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/quanly/trangchu/trangthuoc/thuoc";
    }
}
