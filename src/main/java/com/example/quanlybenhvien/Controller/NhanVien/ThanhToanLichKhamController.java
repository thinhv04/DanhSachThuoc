package com.example.quanlybenhvien.Controller.NhanVien;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.quanlybenhvien.Entity.HoaDonLichKham;
import com.example.quanlybenhvien.Entity.LichKham;
import com.example.quanlybenhvien.Service.HoaDonService;
import com.example.quanlybenhvien.Service.LichKhamService;

@Controller
@RequestMapping("/nhanvien/trangchu/thanhtoan")
public class ThanhToanLichKhamController {
    @Autowired
    LichKhamService lichKhamService;
    @Autowired
    HoaDonService hoaDonService;

    @GetMapping
    public String thanhToan(@RequestParam("id") int maLichKham, Model model) {
        Optional<LichKham> lichKhamOptional = lichKhamService.findById(maLichKham);

        if (lichKhamOptional.isPresent()) {
            LichKham lichKham = lichKhamOptional.get();

            // 👉 Tính tổng tiền
            double tongTien = 0.0;
            if (lichKham.getChiTietDichVus() != null) {
                tongTien = lichKham.getChiTietDichVus().stream()
                        .mapToDouble(ct -> ct.getDichVu().getGia() * ct.getSoLuong())
                        .sum();
            }

            model.addAttribute("lich", lichKham);
            model.addAttribute("maLichKham", lichKham.getMaLichKham());
            model.addAttribute("ngayKham", lichKham.getNgayKham());
            model.addAttribute("gioKham", lichKham.getGioKham());
            model.addAttribute("dichVukham", lichKham.getChiTietDichVus());
            model.addAttribute("tenBenhNhan", lichKham.getBenhNhan().getHoTen());
            model.addAttribute("nhanVien", lichKham.getNhanVien().getHoTen());
            model.addAttribute("bacSi", lichKham.getBacSi().getHoTen());
            model.addAttribute("tongTien", tongTien);

            return "nhanvien/thanhtoan";
        } else {
            model.addAttribute("errorMessage", "Lịch khám không tồn tại.");
            return "nhanvien/lich-kham-da-hoan-thanh";
        }
    }

    @GetMapping("/hoadon")

    public String hienThiDanhSachHoaDon(Model model) {

        List<HoaDonLichKham> hoaDonList = hoaDonService.findAll();

        model.addAttribute("hoadonList", hoaDonList);

        return "nhanvien/hoadon";
    }

    @PostMapping("/xacnhanthanhtoan")
    public String xacNhanThanhToan(HoaDonLichKham hoaDonLichKham,
            Model model) {
        if (hoaDonLichKham.getLichKham() == null) {
            // Handle the case where LichKham is not set, perhaps throw an error or create
            // it
            return "error"; // Example: Redirect to an error page if LichKham is missing
        }

        // Lưu vào CSDL (maHoaDon sẽ được tự động sinh)
        hoaDonService.hoaDonLichKham(hoaDonLichKham);
        System.out.println("Ngày thanh toán: " + hoaDonLichKham.getNgayThanhToan());

        // Sau khi lưu thành công, thêm thông tin hóa đơn vào model và chuyển hướng đến
        // trang danh sách hóa đơn
        model.addAttribute("hoaDon", hoaDonLichKham);

        // Chuyển hướng đến trang danh sách hóa đơn
        return "redirect:/nhanvien/trangchu/thanhtoan/hoadon";
    }

}
