package com.example.quanlybenhvien.Controller.QuanLy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.quanlybenhvien.Entity.DichVu;
import com.example.quanlybenhvien.Service.DichVuService;

@Controller
@RequestMapping("/quanly/trangchu/dichvu")
public class DichVuController {

    private final DichVuService dichVuService;

    public DichVuController(DichVuService dichVuService) {
        this.dichVuService = dichVuService;
    }

    // Hiển thị trang quản lý dịch vụ
    @GetMapping
    public String hienThiDanhSach(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("dichVu", new DichVu());
        model.addAttribute("danhSachDichVu", keyword == null ? dichVuService.layTatCa() : dichVuService.timKiem(keyword));
        return "admin/dichvu";
    }

    // Lưu thông tin dịch vụ (Thêm hoặc Cập nhật)
    @PostMapping("/luu")
    public String luuDichVu(@RequestParam String maDichVu,
                            @RequestParam String tenDichVu,
                            @RequestParam(required = false) String moTa,
                            @RequestParam double gia) {
        DichVu dichVu = new DichVu(maDichVu, tenDichVu, moTa, gia);
        dichVuService.luu(dichVu);
        return "redirect:/quanly/trangchu/dichvu";
    }

    // Xóa dịch vụ
    @GetMapping("/xoa/{id}")
    public String xoaDichVu(@PathVariable String id) {
        dichVuService.xoa(id);
        return "redirect:/quanly/trangchu/dichvu";
    }

    // Chỉnh sửa dịch vụ
    @GetMapping("/sua/{id}")
    public String suaDichVu(@PathVariable String id, Model model) {
        DichVu dichVu = dichVuService.timTheoId(id);
        model.addAttribute("dichVu", dichVu);
        model.addAttribute("danhSachDichVu", dichVuService.layTatCa());
        return "admin/dichvu";
    }
}