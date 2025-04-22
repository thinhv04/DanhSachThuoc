package com.example.quanlybenhvien.Controller.BacSi;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.quanlybenhvien.Entity.BenhAn;
import com.example.quanlybenhvien.Entity.LichKham;
import com.example.quanlybenhvien.Service.BenhAnService;
import com.example.quanlybenhvien.Service.LichKhamService;

@Controller
@RequestMapping("/bacsi/trangchu/benhan")
public class BenhAnController {

    @Autowired
    private BenhAnService benhAnService;

    @Autowired
    private LichKhamService lichKhamService;

    @GetMapping()
    public String hienThilichKham(Model model) {
        List<LichKham> dslichKham = lichKhamService.getAllLichKhams();
        model.addAttribute("dslichKham", dslichKham);
        model.addAttribute("activeTab", "lich-kham");
        return "bacsi/benhan";
    }

    @GetMapping("/danh-sach-benh-an")
    public String hienThiDanhSachBenhAn(Model model) {
        List<BenhAn> danhSachBenhAn = benhAnService.getAllBenhAn();
        model.addAttribute("danhSachBenhAn", danhSachBenhAn);
        model.addAttribute("activeTab", "danh-sach-benh-an");
        return "bacsi/hienthibenhan";
    }

    // Trang thêm mới bệnh án
    @GetMapping("/add/{maLichKham}")
    public String themBenhAnForm(@PathVariable("maLichKham") Integer maLichKham, Model model) {
        Optional<LichKham> lichKhamOpt = lichKhamService.findById(maLichKham);

        if (lichKhamOpt.isPresent()) {
            LichKham lichKham = lichKhamOpt.get();
            model.addAttribute("lichKham", lichKham);
        }

        model.addAttribute("benhAn", new BenhAn());
        model.addAttribute("isEdit", false);
        return "bacsi/thembenhan";
    }

    // Lưu bệnh án mới
    @PostMapping("/save")
    public String saveBenhAn(@RequestParam("lichKhamId") Integer lichKhamId,
                             @RequestParam("trieuChung") String trieuChung,
                             @RequestParam("dieuTri") String dieuTri,
                             @RequestParam("ghiChu") String ghiChu,
                             @RequestParam("tenBenhAn") String tenBenhAn,
                             Model model) {

        Optional<LichKham> lichKhamOpt = lichKhamService.findById(lichKhamId);

        if (lichKhamOpt.isPresent()) {
            LichKham lichKham = lichKhamOpt.get();

            if (lichKham.getBacSi() != null) {
                BenhAn benhAn = new BenhAn();
                benhAn.setLichKham(lichKham);
                benhAn.setTrieuChung(trieuChung);
                benhAn.setDieuTri(dieuTri);
                benhAn.setGhiChu(ghiChu);
                benhAn.setTenBenhAn(tenBenhAn);
                benhAn.setBacSi(lichKham.getBacSi());
                benhAn.setNgayKham(Date.valueOf(LocalDate.now()));

                benhAnService.saveBenhAn(benhAn);
            } else {
                model.addAttribute("error", "Bác sĩ không hợp lệ.");
                return "bacsi/thembenhan";
            }
        } else {
            model.addAttribute("error", "Không tìm thấy thông tin lịch khám.");
            return "bacsi/thembenhan";
        }

        return "redirect:/bacsi/trangchu/benhan/danh-sach-benh-an";
    }

    // Cập nhật bệnh án
    @PostMapping("/update/{benhAnId}")
    public String updateBenhAn(@PathVariable("benhAnId") Integer benhAnId,
                               @ModelAttribute("benhAn") BenhAn benhAn, Model model) {

        Optional<BenhAn> benhAnOpt = benhAnService.getBenhAnById(benhAnId);

        if (benhAnOpt.isPresent()) {
            BenhAn existingBenhAn = benhAnOpt.get();

            existingBenhAn.setTenBenhAn(benhAn.getTenBenhAn());
            existingBenhAn.setTrieuChung(benhAn.getTrieuChung());
            existingBenhAn.setDieuTri(benhAn.getDieuTri());
            existingBenhAn.setGhiChu(benhAn.getGhiChu());

            benhAnService.saveBenhAn(existingBenhAn);

            return "redirect:/bacsi/trangchu/benhan/danh-sach-benh-an";
        } else {
            model.addAttribute("error", "Không tìm thấy bệnh án.");
            return "bacsi/thembenhan";
        }
    }

    // Sửa bệnh án
    @GetMapping("/edit/{id}")
    public String editBenhAnForm(@PathVariable("id") Integer id, Model model) {
        Optional<BenhAn> benhAnOpt = benhAnService.getBenhAnById(id);

        if (benhAnOpt.isPresent()) {
            BenhAn benhAn = benhAnOpt.get();
            model.addAttribute("benhAn", benhAn);
            model.addAttribute("lichKham", benhAn.getLichKham());
            model.addAttribute("isEdit", true);
            return "bacsi/thembenhan";
        } else {
            model.addAttribute("error", "Không tìm thấy bệnh án.");
            return "bacsi/benhan";
        }
    }
} 