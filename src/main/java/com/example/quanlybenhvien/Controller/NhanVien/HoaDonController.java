package com.example.quanlybenhvien.Controller.NhanVien;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.quanlybenhvien.Entity.HoaDonLichKham;
import com.example.quanlybenhvien.Service.HoaDonService;

@Controller
@RequestMapping("/nhanvien/trangchu/hoadon")
public class HoaDonController {

    @Autowired
    HoaDonService hoaDonService;

    @GetMapping()
    public String hienThiDanhSachHoaDon(Model model) {

        List<HoaDonLichKham> hoaDonList = hoaDonService.findAll();

        model.addAttribute("hoadonList", hoaDonList);

        return "nhanvien/hoadon";
    }

}
