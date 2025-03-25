package com.example.quanlybenhvien.Controller.NhanVien;

import com.example.quanlybenhvien.Entity.KhoThuoc;
import com.example.quanlybenhvien.Service.KhoThuocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/nhanvien/trangchu/khothuoc")
public class KhoThuocController {
    @Autowired
    private KhoThuocService khoThuocService;

    // Hiển thị danh sách kho thuốc + tìm kiếm
    @GetMapping("/list")
    public String getAllKhoThuoc(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<KhoThuoc> khoThuocList = (keyword != null && !keyword.isEmpty()) 
                                      ? khoThuocService.searchKhoThuoc(keyword) 
                                      : khoThuocService.getAllKhoThuoc();
        model.addAttribute("khoThuocList", khoThuocList);
        model.addAttribute("keyword", keyword);
        return "nhanvien/kho_thuoc";
    }

    // Xử lý cập nhật số lượng thuốc trong kho
    @PostMapping("/update/{maThuoc}")
    public String updateSoLuong(@PathVariable("maThuoc") String maThuoc,
                                @RequestParam("soLuongMoi") int soLuongMoi) {
        khoThuocService.updateSoLuongThuoc(maThuoc, soLuongMoi);
        return "redirect:/nhanvien/trangchu/khoathuoc/list";
    }
}
