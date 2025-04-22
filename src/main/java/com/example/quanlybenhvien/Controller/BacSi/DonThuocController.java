package com.example.quanlybenhvien.Controller.BacSi;

import com.example.quanlybenhvien.Entity.BacSi;
import com.example.quanlybenhvien.Entity.BenhAn;
import com.example.quanlybenhvien.Entity.DonThuoc;
import com.example.quanlybenhvien.Service.BenhAnService;
import com.example.quanlybenhvien.Service.DonThuocService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/bacsi/trangchu/benhan/donthuoc")
public class DonThuocController {

    @Autowired
    private DonThuocService donThuocService;

    @Autowired
    private BenhAnService benhAnService;

    // ✅ Hiển thị form tạo đơn thuốc
    @GetMapping("/tao")
    public String hienThiFormTaoDonThuoc(@RequestParam("maBenhAn") Integer maBenhAn, Model model) {
        BenhAn benhAn = benhAnService.getBenhAnById(maBenhAn).orElse(null);

        if (benhAn == null || benhAn.getBacSi() == null) {
            return "redirect:/bacsi/trangchu/benhan/danh-sach-benh-an";
        }

        DonThuoc donThuoc = new DonThuoc();
        donThuoc.setBenhAn(benhAn);
        donThuoc.setBacSi(benhAn.getBacSi()); // ✅ Lấy bác sĩ từ bệnh án
        donThuoc.setNgayLap(LocalDateTime.now());

        model.addAttribute("donThuoc", donThuoc);
        return "bacsi/taodonthuoc";
    }


    // ✅ Lưu đơn thuốc vào DB
    @PostMapping("/luu")
    public String luuDonThuoc(@ModelAttribute("donThuoc") DonThuoc donThuoc) {
        Integer maBenhAn = donThuoc.getBenhAn().getMaBenhAn();
        BenhAn benhAn = benhAnService.getBenhAnById(maBenhAn).orElse(null);

        if (benhAn == null || benhAn.getBacSi() == null) {
            return "redirect:/bacsi/trangchu/benhan/danh-sach-benh-an";
        }

        donThuoc.setBenhAn(benhAn);
        donThuoc.setBacSi(benhAn.getBacSi()); // ✅ Gán bác sĩ từ bệnh án

        DonThuoc donThuocDaLuu = donThuocService.saveAndReturn(donThuoc);
        return "redirect:/bacsi/trangchu/benhan/chitietdonthuoc/tao?maDonThuoc=" + donThuocDaLuu.getMaDonThuoc();

    }


    // ✅ Hiển thị danh sách đơn thuốc theo mã bệnh án
    @GetMapping("/danhsach")
    public String hienThiDanhSachDonThuoc(@RequestParam("maBenhAn") Integer maBenhAn, Model model) {
        List<DonThuoc> danhSachDonThuoc = donThuocService.findByMaBenhAn(maBenhAn);
        model.addAttribute("danhSachDonThuoc", danhSachDonThuoc);
        model.addAttribute("maBenhAn", maBenhAn);
        return "bacsi/danhsachdonthuoc";
    }
}
