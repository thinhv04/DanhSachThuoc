package com.example.quanlybenhvien.Controller.NguoiDung;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.quanlybenhvien.Entity.BenhNhan;
import com.example.quanlybenhvien.Service.BenhNhanService;

import jakarta.persistence.EntityNotFoundException;

@Controller
@RequestMapping("/benhnhan")
public class EditBenhNhanController {

    @Autowired
    private BenhNhanService benhNhanService;

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        BenhNhan benhNhan = benhNhanService.findById(id);
        if (benhNhan != null) {
            // Kiểm tra nếu namSinh đang null, đặt giá trị mặc định
            if (benhNhan.getNamSinh() == null) {
                benhNhan.setNamSinh(LocalDate.now()); // Đặt ngày mặc định là hôm nay
            }
            model.addAttribute("benhnhan", benhNhan);
            return "thongtincanhan";
        }
        model.addAttribute("error", "Bệnh nhân không tồn tại");
        return "error";
    }

    @PostMapping("/update/{id}")
    public String updateBenhNhan(@PathVariable("id") Integer id,
            @ModelAttribute("benhnhan") BenhNhan benhNhan,
            RedirectAttributes redirectAttributes) {
        System.out.println("Cập nhật bệnh nhân ID: " + id);
        System.out.println("Thông tin nhận được: " + benhNhan);

        try {
            benhNhanService.updateBenhNhan(id, benhNhan);
            redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin bệnh nhân thành công!");
            System.out.println("kkkkkk");
            return "redirect:/benhnhan/edit/" + id;
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy bệnh nhân.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi khi cập nhật thông tin.");
            e.printStackTrace(); // In lỗi ra console

        }
        
        return "redirect:/benhnhan/edit/" + id;
    }
}
