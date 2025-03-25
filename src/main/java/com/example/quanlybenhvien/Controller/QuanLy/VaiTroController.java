package com.example.quanlybenhvien.Controller.QuanLy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.quanlybenhvien.Entity.Vaitro;
import com.example.quanlybenhvien.Service.VaiTroService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/quanly/trangchu")
public class VaiTroController {
    @Autowired
    VaiTroService vaiTroService;

    @GetMapping("/vaitro")
    public String findAllVaiTro(@RequestParam(value = "maVaiTro", required = false) String maVaiTro, Model model) {
        List<Vaitro> vaiTroList = vaiTroService.findAll();
        model.addAttribute("vaiTroList", vaiTroList);
        model.addAttribute("vaitro", new Vaitro()); // Nếu thêm mới
        return "admin/vaitro"; // Đúng đường dẫn file Thymeleaf
    }

    // @GetMapping("/vaitro/reset")
    // public String reset(@RequestParam(value = "maVaiTro", required = false)
    // String maVaiTro, Model model) {
    // model.addAttribute("vaitro", new Vaitro());
    // return "admin/vaitro";
    // }
    @PostMapping("/vaitro")
    public String saveRole(@ModelAttribute Vaitro vaitro, Model model, RedirectAttributes redirectAttributes) {
        if (vaitro.getMaVaiTro() == null || vaitro.getMaVaiTro().isEmpty()) {
            List<Vaitro> vaiTroList = vaiTroService.findAll();
            model.addAttribute("vaiTroList", vaiTroList);
            model.addAttribute("successMessage", "Mã vai trò không được để trống!");
            return "admin/vaitro"; // Quay lại form
        }
        Vaitro existingRole = vaiTroService.findVaitroByID(vaitro.getMaVaiTro());

        if (existingRole == null) {
            redirectAttributes.addFlashAttribute("successMessage", "Thêm vai trò thành công!");
        } else {
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật vai trò thành công!");
        }

        vaiTroService.saveVaiTro(vaitro);
        return "redirect:/quanly/trangchu/vaitro";
    }

    // Sửa vai trò (Load dữ liệu lên form)
    @GetMapping("/vaitro/edit/{maVaiTro}")
    public String editRole(@PathVariable String maVaiTro, Model model) {
        Vaitro role = vaiTroService.findVaitroByID(maVaiTro);
        if (role != null) {
            model.addAttribute("vaitro", role);
        } else {
            model.addAttribute("vaitro", new Vaitro());
        }
        model.addAttribute("vaiTroList", vaiTroService.findAll());
        return "admin/vaitro";
    }

    // Xóa vai trò
    @GetMapping("/vaitro/delete/{maVaiTro}")
    public String deleteRole(@PathVariable String maVaiTro, RedirectAttributes redirectAttributes) {
        String vaitro = "VT00";
        if (maVaiTro.equals(vaitro)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không được phép xóa vai trò này!");
            return "redirect:/quanly/trangchu/vaitro";
        }
        vaiTroService.deleteVaiTro(maVaiTro);
        redirectAttributes.addFlashAttribute("successMessage", "xóa vai trò thành công!");
        return "redirect:/quanly/trangchu/vaitro";
    }

    @GetMapping("/vaitro/timkiem")
    public String listRoles(@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword, Model model) {
    List<Vaitro> vaiTroList ;

    // Nếu danh sách null, gán danh sách rỗng để tránh lỗi template
    if (keyword.trim().isEmpty()) {
        vaiTroList = vaiTroService.findAll();
    }
    else
    {
        vaiTroList = vaiTroService.timKiemVaiTro(keyword);
    }
    model.addAttribute("vaiTroList", vaiTroList);  // Đổi tên cho thống nhất
    model.addAttribute("vaitro", new Vaitro());
    model.addAttribute("keyword", keyword);

    System.out.println("Danh sách Vai Trò: " + vaiTroList);
    return "admin/vaitro";
}
}
