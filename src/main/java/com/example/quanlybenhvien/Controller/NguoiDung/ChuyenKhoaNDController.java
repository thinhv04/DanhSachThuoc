package com.example.quanlybenhvien.Controller.NguoiDung;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlybenhvien.Entity.BacSi;
import com.example.quanlybenhvien.Entity.ChuyenKhoa;
import com.example.quanlybenhvien.Service.ChuyenKhoaService;

@Controller
@RequestMapping("/nguoidung/chuyenkhoa")
public class ChuyenKhoaNDController {
    @Autowired
    private ChuyenKhoaService chuyenKhoaService;

    @GetMapping
    public String hienthiChuyenKhoa(Model model) {
        List<ChuyenKhoa> listCK = chuyenKhoaService.getAllChuyenKhoa();
        model.addAttribute("chuyenkhoaND", listCK);
        return "/chuyenkhoaND";
    }

    // Xử lý khi nhấn vào "XEM CHI TIẾT"
    @GetMapping("/chitiet/{id}")
    public String chiTietChuyenKhoa(@PathVariable("id") String id, Model model) {
        System.out.println("ID nhận được: " + id); // Debug ID nhận vào

        Optional<ChuyenKhoa> optionalChuyenKhoa = chuyenKhoaService.findById(id);
        if (!optionalChuyenKhoa.isPresent()) {
            System.out.println("Không tìm thấy bác sĩ với ID: " + id);
            return "redirect:/nguoidung/chuyenkhoa";
        }

        ChuyenKhoa chuyenKhoa = optionalChuyenKhoa.get();
        model.addAttribute("chuyenkhoa", chuyenKhoa);
        return "chitietchuyenkhoa";
    }
}
