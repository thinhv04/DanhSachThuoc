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
import com.example.quanlybenhvien.Service.BacSiService;

@Controller
@RequestMapping("/nguoidung/bacsi")
public class BacSiNDController {
    @Autowired
    private BacSiService bacSiService;

    @GetMapping
    public String hienthiBacSi(Model model) {
        List<BacSi> listBS = bacSiService.getAllBacSi();
        model.addAttribute("bacsiND", listBS);
        return "bacsiND";
    }

    // Xử lý khi nhấn vào "XEM CHI TIẾT"
    @GetMapping("/chitiet/{id}")
    public String chiTietBacSi(@PathVariable("id") String id, Model model) {
        System.out.println("ID nhận được: " + id); // Debug ID nhận vào

        Optional<BacSi> optionalBacSi = bacSiService.findById(id);
        if (!optionalBacSi.isPresent()) {
            System.out.println("Không tìm thấy bác sĩ với ID: " + id);
            return "redirect:/nguoidung/bacsi";
        }

        BacSi bacSi = optionalBacSi.get();
        model.addAttribute("bacSi", bacSi);
        return "chitietBacSi";
    }
}
