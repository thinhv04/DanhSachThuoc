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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.example.quanlybenhvien.Entity.ChuyenKhoa;
import com.example.quanlybenhvien.Service.ChuyenKhoaService;

@Controller
@RequestMapping("/quanly/trangchu")
public class ChuyenKhoaController {
    @Autowired
    private ChuyenKhoaService chuyenKhoaService;

    // Hiển thị danh sách chuyên khoa
    @GetMapping("/chuyenkhoa")
    public String listChuyenkhoa(Model model) {
        List<ChuyenKhoa> list = chuyenKhoaService.getAllChuyenKhoa();
        model.addAttribute("chuyenkhoas", list);
        model.addAttribute("chuyenKhoa", new ChuyenKhoa()); // Đối tượng rỗng để nhập dữ liệu mới
        model.addAttribute("isEditing", false); // Không ở chế độ chỉnh sửa
        return "admin/chuyenkhoa";
    }

    // Thêm chuyên khoa mới
    @PostMapping("/chuyenkhoa")
    public String addChuyenkhoa(@Validated @ModelAttribute("chuyenKhoa") ChuyenKhoa chuyenkhoa,
            BindingResult result, Model model, RedirectAttributes redirect, @RequestParam("file") MultipartFile file) {

        if (result.hasErrors() || chuyenkhoa.getTenChuyenKhoa().trim().isEmpty()) {
            model.addAttribute("message", "Không được để trống các trường!");
            model.addAttribute("chuyenkhoas", chuyenKhoaService.getAllChuyenKhoa());
            model.addAttribute("isEditing", false);
            return "admin/chuyenkhoa";
        }

        if (chuyenKhoaService.existsById(chuyenkhoa.getMaChuyenKhoa())) {
            model.addAttribute("message", "Mã chuyên khoa đã tồn tại!");
            model.addAttribute("chuyenkhoas", chuyenKhoaService.getAllChuyenKhoa());
            model.addAttribute("isEditing", false);
            return "admin/chuyenkhoa";
        }

        if (chuyenKhoaService.existsByTenCK(chuyenkhoa.getTenChuyenKhoa())) {
            model.addAttribute("message", "Tên chuyên khoa đã tồn tại!");
            model.addAttribute("chuyenkhoas", chuyenKhoaService.getAllChuyenKhoa());
            model.addAttribute("isEditing", false);
            return "admin/chuyenkhoa";
        }

        // Lưu ảnh vào thư mục static/images/imagesCK
        if (!file.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path path = Paths.get("src/main/resources/static/images/imagesCK", fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                chuyenkhoa.setHinh(fileName);
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("message", "Lỗi khi tải ảnh lên!");
                model.addAttribute("chuyenkhoas", chuyenKhoaService.getAllChuyenKhoa());
                model.addAttribute("isEditing", false);
                return "/admin/chuyenkhoa";
            }
        }

        chuyenKhoaService.save(chuyenkhoa);
        redirect.addFlashAttribute("success", "Chuyên khoa đã được thêm thành công!");
        return "redirect:/quanly/trangchu/chuyenkhoa";
    }

    // Chỉnh sửa chuyên khoa
    @GetMapping("/chuyenkhoa/edit/{id}")
    public String editChuyenKhoa(@PathVariable String id, Model model) {
        Optional<ChuyenKhoa> chuyenKhoa = chuyenKhoaService.findById(id);
        if (chuyenKhoa.isPresent()) {
            model.addAttribute("chuyenKhoa", chuyenKhoa.get());
            model.addAttribute("isEditing", true);
        } else {
            model.addAttribute("chuyenKhoa", new ChuyenKhoa());
            model.addAttribute("isEditing", false);
        }
        model.addAttribute("chuyenkhoas", chuyenKhoaService.getAllChuyenKhoa());
        return "admin/chuyenkhoa";
    }

    // Cập nhật chuyên khoa
    @PostMapping("/chuyenkhoa/update")
    public String updateChuyenkhoa(@Validated @ModelAttribute("chuyenKhoa") ChuyenKhoa chuyenkhoa,
            BindingResult result, Model model, RedirectAttributes redirect, @RequestParam("file") MultipartFile file) {

        if (result.hasErrors() || chuyenkhoa.getTenChuyenKhoa().trim().isEmpty()) {
            model.addAttribute("message", "Không được để trống các trường!");
            model.addAttribute("chuyenkhoas", chuyenKhoaService.getAllChuyenKhoa());
            model.addAttribute("isEditing", true);
            return "admin/chuyenkhoa";
        }

        Optional<ChuyenKhoa> existingChuyenKhoa = chuyenKhoaService.findById(chuyenkhoa.getMaChuyenKhoa());
        if (existingChuyenKhoa.isPresent()) {
            // Kiểm tra nếu tên chuyên khoa mới đã tồn tại (tránh trùng với chuyên khoa
            // khác)
            if (!existingChuyenKhoa.get().getTenChuyenKhoa().equals(chuyenkhoa.getTenChuyenKhoa())
                    && chuyenKhoaService.existsByTenCK(chuyenkhoa.getTenChuyenKhoa())) {
                model.addAttribute("message", "Tên chuyên khoa đã tồn tại!");
                model.addAttribute("chuyenkhoas", chuyenKhoaService.getAllChuyenKhoa());
                model.addAttribute("isEditing", true);
                return "admin/chuyenkhoa";
            }

            // Nếu có file ảnh mới, lưu ảnh vào thư mục
            if (!file.isEmpty()) {
                try {
                    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    Path path = Paths.get("src/main/resources/static/images/imagesCK", fileName);
                    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    chuyenkhoa.setHinh(fileName);
                } catch (IOException e) {
                    model.addAttribute("message", "Lỗi khi tải ảnh lên!");
                    model.addAttribute("chuyenkhoas", chuyenKhoaService.getAllChuyenKhoa());
                    model.addAttribute("isEditing", true);
                    return "admin/chuyenkhoa";
                }
            }

            // Cập nhật chuyên khoa
            ChuyenKhoa existingCK = existingChuyenKhoa.get();
            existingCK.setTenChuyenKhoa(chuyenkhoa.getTenChuyenKhoa());
            existingCK.setGhiChu(chuyenkhoa.getGhiChu());
            existingCK.setHinh((chuyenkhoa.getHinh())); // Cập nhật ảnh nếu có
            chuyenKhoaService.save(existingCK);
            redirect.addFlashAttribute("success", "Chuyên khoa đã được cập nhật thành công!");
        }

        return "redirect:/quanly/trangchu/chuyenkhoa";
    }

    // Xóa chuyên khoa
    @GetMapping("/chuyenkhoa/delete/{id}")
    public String deleteChuyenKhoa(@PathVariable String id, RedirectAttributes redirect) {
        chuyenKhoaService.deleteById(id);
        redirect.addFlashAttribute("success", "Chuyên khoa đã được xóa thành công!");
        return "redirect:/quanly/trangchu/chuyenkhoa";
    }

    @GetMapping("/chuyenkhoa/search")
    public String searchChuyenkhoa(@RequestParam("keyword") String keyword, Model model) {
        List<ChuyenKhoa> result;
        if (keyword.trim().isEmpty()) {
            result = chuyenKhoaService.getAllChuyenKhoa(); // Hiển thị tất cả nếu không nhập từ khóa
        } else {
            result = chuyenKhoaService.searchByName(keyword);
        }
        model.addAttribute("chuyenkhoas", result);
        model.addAttribute("chuyenKhoa", new ChuyenKhoa());
        model.addAttribute("isEditing", false);
        return "admin/chuyenkhoa";
    }
}
