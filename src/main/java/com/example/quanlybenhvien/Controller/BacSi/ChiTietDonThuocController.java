package com.example.quanlybenhvien.Controller.BacSi;

import com.example.quanlybenhvien.Entity.BenhAn;
import com.example.quanlybenhvien.Entity.ChiTietDonThuoc;
import com.example.quanlybenhvien.Entity.DonThuoc;
import com.example.quanlybenhvien.Entity.Thuoc;
import com.example.quanlybenhvien.Service.BenhAnService;
import com.example.quanlybenhvien.Service.ChiTietDonThuocService;
import com.example.quanlybenhvien.Service.DonThuocService;
import com.example.quanlybenhvien.Service.KhoThuocService;
import com.example.quanlybenhvien.Service.ThuocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/bacsi/trangchu/benhan/chitietdonthuoc")
public class ChiTietDonThuocController {

    @Autowired
    private ChiTietDonThuocService chiTietDonThuocService;

    @Autowired
    private ThuocService thuocService;

    @Autowired
    private KhoThuocService khoThuocService;

    @Autowired
    private DonThuocService donThuocService;

    @Autowired
    private BenhAnService benhAnService;




    @GetMapping("/tao")
    public String hienThiFormKeThuoc(@RequestParam("maDonThuoc") Integer maDonThuoc, Model model) {
        ChiTietDonThuoc chiTiet = new ChiTietDonThuoc();
        chiTiet.setMaDonThuoc(maDonThuoc);

        List<Thuoc> danhSachThuoc = thuocService.timkiemthuoc(null);
        List<ChiTietDonThuoc> chiTietDaKe = chiTietDonThuocService.findByMaDonThuoc(maDonThuoc);

        Map<String, String> thuocMap = danhSachThuoc.stream()
            .collect(Collectors.toMap(Thuoc::getMaThuoc, Thuoc::getTenThuoc));

        model.addAttribute("chiTietDonThuoc", chiTiet);
        model.addAttribute("danhSachThuoc", danhSachThuoc);
        model.addAttribute("chiTietDaKe", chiTietDaKe);
        model.addAttribute("thuocMap", thuocMap);
        model.addAttribute("maDonThuoc", maDonThuoc); // <-- Thêm dòng này
        return "bacsi/taochitietdonthuoc";
    }

    @GetMapping("/xem")
    public String xemChiTietDonThuoc(@RequestParam("maDonThuoc") Integer maDonThuoc, Model model) {
        Optional<DonThuoc> donThuocOpt = donThuocService.findById(maDonThuoc);
        if (donThuocOpt.isEmpty()) {
            model.addAttribute("errorMessage", "Không tìm thấy đơn thuốc.");
            return "redirect:/bacsi/trangchu/benhan/danh-sach-benh-an";
        }

        DonThuoc donThuoc = donThuocOpt.get();
        List<ChiTietDonThuoc> danhSachChiTiet = chiTietDonThuocService.findByMaDonThuoc(maDonThuoc);

        Map<String, String> thuocMap = thuocService.timkiemthuoc(null).stream()
            .collect(Collectors.toMap(Thuoc::getMaThuoc, Thuoc::getTenThuoc));

        model.addAttribute("donThuoc", donThuoc);
        model.addAttribute("danhSachChiTiet", danhSachChiTiet);
        model.addAttribute("thuocMap", thuocMap);

        return "bacsi/xemchitietdonthuoc";
    }

    @GetMapping("/xem-theo-benh-an")
    public String xemChiTietTheoBenhAn(@RequestParam("maBenhAn") Integer maBenhAn, Model model) {
        // Lấy danh sách đơn thuốc theo mã bệnh án
        List<DonThuoc> danhSachDonThuoc = donThuocService.findByBenhAn(maBenhAn);

        // Tạo map chứa danh sách chi tiết đơn thuốc theo từng đơn
        Map<Integer, List<ChiTietDonThuoc>> mapChiTiet = danhSachDonThuoc.stream()
            .collect(Collectors.toMap(
                DonThuoc::getMaDonThuoc,
                dt -> chiTietDonThuocService.findByMaDonThuoc(dt.getMaDonThuoc())
            ));

        // Map maThuoc → tenThuoc để hiển thị
        Map<String, String> thuocMap = thuocService.timkiemthuoc(null).stream()
            .collect(Collectors.toMap(Thuoc::getMaThuoc, Thuoc::getTenThuoc));

        // Lấy thông tin bệnh án để hiển thị thêm
        BenhAn benhAn = benhAnService.getBenhAnById(maBenhAn).orElse(null);

        model.addAttribute("danhSachDonThuoc", danhSachDonThuoc);
        model.addAttribute("mapChiTiet", mapChiTiet);
        model.addAttribute("thuocMap", thuocMap);
        model.addAttribute("maBenhAn", maBenhAn);

        // Thêm thông tin cho giao diện
        model.addAttribute("tenBenhAn", benhAn.getTenBenhAn());
        model.addAttribute("trieuChung", benhAn.getTrieuChung());

        return "bacsi/xemchitiettheobenhan";
    }

    @GetMapping("/xoa")
public String xoaChiTietDonThuoc(@RequestParam("maChiTietDt") Integer maChiTietDt,
                                  @RequestParam("maDonThuoc") Integer maDonThuoc,
                                  RedirectAttributes redirectAttributes) {
    // Kiểm tra sự tồn tại của chi tiết đơn thuốc
    Optional<ChiTietDonThuoc> chiTietOpt = chiTietDonThuocService.findById(maChiTietDt);
    if (chiTietOpt.isPresent()) {
        ChiTietDonThuoc chiTiet = chiTietOpt.get();

        // Tăng lại số lượng thuốc vào kho
        khoThuocService.tangSoLuong(chiTiet.getMaThuoc(), chiTiet.getSoLuong());

        // Xóa chi tiết đơn thuốc khỏi DB
        chiTietDonThuocService.deleteById(maChiTietDt);

        // Thêm thông báo thành công
        redirectAttributes.addFlashAttribute("successMessage", "Xóa thuốc thành công!");
    } else {
        // Thêm thông báo lỗi nếu không tìm thấy chi tiết
        redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy chi tiết đơn thuốc!");
    }

    // Thêm tham số maDonThuoc vào redirect để duy trì trạng thái
    redirectAttributes.addAttribute("maDonThuoc", maDonThuoc);
    
    // Redirect về trang tạo chi tiết đơn thuốc
    return "redirect:/bacsi/trangchu/benhan/chitietdonthuoc/tao";
}





    

    @PostMapping("/luu")
    public String luuChiTietDonThuoc(@ModelAttribute("chiTietDonThuoc") ChiTietDonThuoc chiTiet,
                                     RedirectAttributes redirectAttrs,
                                     Model model) {
        try {
            chiTietDonThuocService.keThuoc(chiTiet);
    
            // Gửi thông báo thành công về sau khi redirect
            redirectAttrs.addFlashAttribute("successMessage", "Đã thêm thuốc vào đơn thành công!");
    
        } catch (RuntimeException ex) {
            List<Thuoc> danhSachThuoc = thuocService.timkiemthuoc(null);
            List<ChiTietDonThuoc> chiTietDaKe = chiTietDonThuocService.findByMaDonThuoc(chiTiet.getMaDonThuoc());
    
            Map<String, String> thuocMap = danhSachThuoc.stream()
                .collect(Collectors.toMap(Thuoc::getMaThuoc, Thuoc::getTenThuoc));
    
            model.addAttribute("chiTietDonThuoc", chiTiet);
            model.addAttribute("danhSachThuoc", danhSachThuoc);
            model.addAttribute("chiTietDaKe", chiTietDaKe);
            model.addAttribute("thuocMap", thuocMap);
            model.addAttribute("errorMessage", "Kê thuốc thất bại: " + ex.getMessage());
    
            return "bacsi/taochitietdonthuoc";
        }
    
        return "redirect:/bacsi/trangchu/benhan/chitietdonthuoc/tao?maDonThuoc=" + chiTiet.getMaDonThuoc();
    }
    

}

