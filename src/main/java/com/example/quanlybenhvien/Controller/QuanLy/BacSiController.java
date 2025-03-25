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
import org.springframework.dao.DataIntegrityViolationException;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.quanlybenhvien.Entity.BacSi;
import com.example.quanlybenhvien.Entity.ChuyenKhoa;
import com.example.quanlybenhvien.Entity.Vaitro;
import com.example.quanlybenhvien.Service.ChuyenKhoaService;
import com.example.quanlybenhvien.Service.VaiTroService;
import com.example.quanlybenhvien.Service.BacSiService;

@Controller
@RequestMapping("/quanly/trangchu/bacsi")
public class BacSiController {

    @Autowired
    private BacSiService bacSiService;

    @Autowired
    private ChuyenKhoaService chuyenkhoaService;

    @Autowired
    private VaiTroService vaiTroService;

    // Hiển thị danh sách bác sĩ
    @GetMapping
    public String listBacSi(Model model) {
        List<BacSi> listBacSi = bacSiService.getAllBacSi();
        List<ChuyenKhoa> listChuyenKhoa = chuyenkhoaService.getAllChuyenKhoa();
        List<Vaitro> listVaiTro = vaiTroService.findAll();

        model.addAttribute("dsBacSi", listBacSi);
        model.addAttribute("dsChuyenKhoa", listChuyenKhoa);
        model.addAttribute("dsVaiTro", listVaiTro);
        model.addAttribute("bacsi", new BacSi());
        model.addAttribute("isEdit", false);
        return "admin/bacsi";
    }

    @PostMapping("/add")
    public String addBacSi(@Validated @ModelAttribute("bacsi") BacSi bacsi,
            BindingResult result,
            @RequestParam("file") MultipartFile file,
            Model model, RedirectAttributes redirectAttributes) {

        // Kiểm tra lỗi validate
        if (result.hasErrors() || bacsi.getHoTen().trim().isEmpty() || bacsi.getEmail().trim().isEmpty()) {
            model.addAttribute("error", "Không được để trống các trường!");
            model.addAttribute("dsBacSi", bacSiService.getAllBacSi());
            model.addAttribute("isEdit", false);
            return "admin/bacsi";
        }

        // Kiểm tra mã bác sĩ đã tồn tại chưa
        if (bacSiService.existsById(bacsi.getMaBacSi())) {
            redirectAttributes.addFlashAttribute("error", "Mã bác sĩ đã tồn tại, vui lòng nhập mã khác!");
            return "redirect:/quanly/trangchu/bacsi";
        }

        // Kiểm tra email đã tồn tại chưa
        if (bacSiService.existsByEmail(bacsi.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "Email đã tồn tại, vui lòng nhập email khác!");
            return "redirect:/quanly/trangchu/bacsi";
        }

        // Kiểm tra CCCD đã tồn tại chưa
        if (bacSiService.existsByCccd(bacsi.getCccd())) {
            redirectAttributes.addFlashAttribute("error", "CCCD đã tồn tại, vui lòng nhập CCCD khác!");
            return "redirect:/quanly/trangchu/bacsi";
        }

        // Kiểm tra số điện thoại đã tồn tại chưa
        if (bacSiService.existsBySdt(bacsi.getSdt())) {
            redirectAttributes.addFlashAttribute("error", "Số điện thoại đã tồn tại, vui lòng nhập số khác!");
            return "redirect:/quanly/trangchu/bacsi";
        }

        // Xử lý lưu ảnh vào static/images
        if (!file.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path path = Paths.get("src/main/resources/static/images/", fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                bacsi.setHinh(fileName);
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("error", "Lỗi upload ảnh!");
                return "redirect:/quanly/trangchu/bacsi";
            }
        } else {
            bacsi.setHinh("default.png");
        }

        // Lưu bác sĩ mới
        try {
            bacSiService.save(bacsi);
            redirectAttributes.addFlashAttribute("success", "Thêm bác sĩ thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi không xác định khi lưu dữ liệu!");
        }
        return "redirect:/quanly/trangchu/bacsi";
    }

    @GetMapping("/edit/{id}")
    public String editBacSi(@PathVariable String id, Model model, RedirectAttributes redirect) {
        Optional<BacSi> bacSi = bacSiService.findById(id);
        if (bacSi.isPresent()) {
            model.addAttribute("bacsi", bacSi.get());
            model.addAttribute("isEdit", true);
        } else {
            redirect.addFlashAttribute("error", "Không tìm thấy bác sĩ cần chỉnh sửa!");
            return "redirect:/quanly/trangchu/bacsi";
        }
        model.addAttribute("dsBacSi", bacSiService.getAllBacSi());
        model.addAttribute("dsChuyenKhoa", chuyenkhoaService.getAllChuyenKhoa());
        model.addAttribute("dsVaiTro", vaiTroService.findAll());
        return "admin/bacsi";
    }

    @PostMapping("/update")
    public String updateBacSi(@Validated @ModelAttribute("bacsi") BacSi bacsiEntity,
            BindingResult result,
            @RequestParam(value = "file", required = false) MultipartFile file,
            Model model, RedirectAttributes redirect) {

        if (result.hasErrors() || bacsiEntity.getHoTen().trim().isEmpty()) {
            model.addAttribute("error", "Không được để trống các trường!");
            model.addAttribute("bacsi", bacSiService.getAllBacSi());
            model.addAttribute("isEdit", true);
            return "admin/bacsi";
        }

        Optional<BacSi> existingBacSi = bacSiService.findById(bacsiEntity.getMaBacSi());
        if (existingBacSi.isPresent()) {
            BacSi existingEntity = existingBacSi.get();

            // Cập nhật thông tin
            existingEntity.setHoTen(bacsiEntity.getHoTen());
            existingEntity.setEmail(bacsiEntity.getEmail());
            existingEntity.setChuyenKhoa((bacsiEntity.getChuyenKhoa()));
            existingEntity.setVaiTro(bacsiEntity.getVaiTro());
            existingEntity.setCccd(bacsiEntity.getCccd());
            existingEntity.setDiaChi(bacsiEntity.getDiaChi());
            existingEntity.setGioiTinh(bacsiEntity.getGioiTinh());
            // existingEntity.setHinh(bacsiEntity.getHinh());
            existingEntity.setMatKhau(bacsiEntity.getMatKhau());
            existingEntity.setSdt(bacsiEntity.getSdt());
            existingEntity.setGhiChu(bacsiEntity.getGhiChu());

            // Kiểm tra nếu có ảnh mới được tải lên
            if (file != null && !file.isEmpty()) {
                try {
                    // Tạo tên file duy nhất
                    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    String uploadDir = new File("src/main/resources/static/images/").getAbsolutePath();

                    // Tạo thư mục nếu chưa tồn tại
                    File uploadFolder = new File(uploadDir);
                    if (!uploadFolder.exists()) {
                        uploadFolder.mkdirs();
                    }

                    // Lưu file vào thư mục
                    File destinationFile = new File(uploadFolder, fileName);
                    file.transferTo(destinationFile);

                    // Xóa ảnh cũ nếu có
                    if (existingEntity.getHinh() != null && !existingEntity.getHinh().equals("default.png")) {
                        File oldFile = new File(uploadFolder, existingEntity.getHinh());
                        if (oldFile.exists()) {
                            oldFile.delete();
                        }
                    }

                    // Cập nhật ảnh mới vào database
                    existingEntity.setHinh(fileName);
                } catch (Exception e) {
                    model.addAttribute("error", "Lỗi khi tải ảnh lên!");
                    return "admin/bacsi";
                }
            }

            // Lưu dữ liệu cập nhật vào database
            bacSiService.save(existingEntity);
            redirect.addFlashAttribute("success", "Bác sĩ đã được cập nhật thành công!");
        } else {
            redirect.addFlashAttribute("error", "Không tìm thấy bác sĩ để cập nhật!");
        }

        return "redirect:/quanly/trangchu/bacsi";
    }

    // Xóa bác sĩ
    @GetMapping("/delete/{id}")
    public String deleteBacSi(@PathVariable String id, RedirectAttributes redirectAttributes) {
        Optional<BacSi> bacsiOpt = bacSiService.findById(id);
        if (bacsiOpt.isPresent()) {
            bacSiService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Bác sĩ đã được xóa thành công!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy bác sĩ để xóa!");
        }
        return "redirect:/quanly/trangchu/bacsi";

    }

    // Tìm kiếm bác sĩ theo tên
    @GetMapping("/search")
    public String searchBacSi(@RequestParam("keyword") String keyword, Model model) {
        List<BacSi> result;
        if (keyword.trim().isEmpty()) {
            result = bacSiService.getAllBacSi();
        } else {
            result = bacSiService.searchByName(keyword);
        }

        model.addAttribute("dsBacSi", result);
        model.addAttribute("bacsi", new BacSi());
        model.addAttribute("isEdit", false);
        return "admin/bacsi";
    }
}
