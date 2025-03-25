package com.example.quanlybenhvien.Controller.QuanLy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.quanlybenhvien.Entity.NhanVien;
import com.example.quanlybenhvien.Entity.Vaitro;
import com.example.quanlybenhvien.Service.NhanVienService;
import com.example.quanlybenhvien.Service.VaiTroService;

@Controller
@RequestMapping("/quanly/trangchu/nhanvien")
public class NhanVienController {

    @Autowired
    private NhanVienService nhanVienService;

    @Autowired
    private VaiTroService vaiTroService;

    @GetMapping
    public String listNhanVien(Model model) {
        List<NhanVien> listNhanVien = nhanVienService.getAllNhanVien();
        List<Vaitro> listVaiTro = vaiTroService.findAll();

        model.addAttribute("dsNhanVien", listNhanVien);
        model.addAttribute("dsVaiTro", listVaiTro);
        model.addAttribute("nhanvien", new NhanVien());
        model.addAttribute("isEdit", false);
        return "admin/nhanvien";
    }

    @PostMapping("/add")
    public String addNhanVien(@Validated @ModelAttribute("nhanvien") NhanVien nhanVien,
            BindingResult result,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors() || nhanVien.getHoTen().trim().isEmpty() || nhanVien.getEmail().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Không được để trống các trường!");
            return "redirect:/quanly/trangchu/nhanvien";
        }

        if (nhanVienService.existsById(nhanVien.getMaNhanVien())) {
            redirectAttributes.addFlashAttribute("error", "Mã nhân viên đã tồn tại!");
            return "redirect:/quanly/trangchu/nhanvien";
        }

        if (!file.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path path = Paths.get("src/main/resources/static/images/", fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                nhanVien.setHinh(fileName);
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("error", "Lỗi upload ảnh!");
                return "redirect:/quanly/trangchu/nhanvien";
            }
        } else {
            nhanVien.setHinh("default.png");
        }

        nhanVienService.save(nhanVien);
        redirectAttributes.addFlashAttribute("success", "Thêm nhân viên thành công!");
        return "redirect:/quanly/trangchu/nhanvien";
    }

    @GetMapping("/edit/{id}")
    public String editNhanVien(@PathVariable String id, Model model, RedirectAttributes redirect) {
        Optional<NhanVien> nhanVien = nhanVienService.findById(id);
        if (nhanVien.isPresent()) {
            model.addAttribute("nhanvien", nhanVien.get());
            model.addAttribute("isEdit", true);
        } else {
            redirect.addFlashAttribute("error", "Không tìm thấy nhân viên cần chỉnh sửa!");
            return "redirect:/quanly/trangchu/nhanvien";
        }
        model.addAttribute("dsNhanVien", nhanVienService.getAllNhanVien());
        model.addAttribute("dsVaiTro", vaiTroService.findAll());
        return "admin/nhanvien";
    }

    @PostMapping("/update")
    public String updateNhanVien(@Validated @ModelAttribute("nhanvien") NhanVien nhanVien,
            BindingResult result,
            @RequestParam(value = "file", required = false) MultipartFile file,
            RedirectAttributes redirect) {

        if (result.hasErrors() || nhanVien.getHoTen().trim().isEmpty()) {
            redirect.addFlashAttribute("error", "Không được để trống các trường!");
            return "redirect:/quanly/trangchu/nhanvien";
        }

        Optional<NhanVien> existingNhanVien = nhanVienService.findById(nhanVien.getMaNhanVien());
        if (existingNhanVien.isPresent()) {
            NhanVien existingEntity = existingNhanVien.get();
            existingEntity.setHoTen(nhanVien.getHoTen());
            existingEntity.setEmail(nhanVien.getEmail());
            existingEntity.setVaiTro(nhanVien.getVaiTro());
            existingEntity.setSdt(nhanVien.getSdt());
            existingEntity.setDiaChi(nhanVien.getDiaChi());

            if (file != null && !file.isEmpty()) {
                try {
                    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    Path path = Paths.get("src/main/resources/static/images/", fileName);
                    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    existingEntity.setHinh(fileName);
                } catch (IOException e) {
                    redirect.addFlashAttribute("error", "Lỗi khi tải ảnh lên!");
                    return "redirect:/quanly/trangchu/nhanvien";
                }
            }
            nhanVienService.save(existingEntity);
            redirect.addFlashAttribute("success", "Cập nhật nhân viên thành công!");
        } else {
            redirect.addFlashAttribute("error", "Không tìm thấy nhân viên để cập nhật!");
        }
        return "redirect:/quanly/trangchu/nhanvien";
    }

    @GetMapping("/delete/{id}")
    public String deleteNhanVien(@PathVariable String id, RedirectAttributes redirectAttributes) {
        if (nhanVienService.existsById(id)) {
            nhanVienService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Nhân viên đã được xóa thành công!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy nhân viên để xóa!");
        }
        return "redirect:/quanly/trangchu/nhanvien";
    }
}