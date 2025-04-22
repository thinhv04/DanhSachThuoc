package com.example.quanlybenhvien.Controller.BacSi;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.quanlybenhvien.Entity.BacSi;
import com.example.quanlybenhvien.Service.BacSiService;

@Controller
@RequestMapping("/bacsi")
public class BacSiBSController {

    private static final String UPLOAD_DIR = "quanlyphongkham/uploads"; // ← thêm dòng này
    @Autowired
    private BacSiService bacSiService;

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {
        if (error != null) {
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu!");
        }
        if (logout != null) {
            model.addAttribute("message", "Đã đăng xuất thành công!");
        }
        return "bacsi/bacsi-login";
    }

    @GetMapping("/trangchu")
    public String showHomePage() {
        return "bacsi/bacsi";
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        BacSi bacSi = bacSiService.getBacSiDangNhap();
        if (bacSi != null) {
            model.addAttribute("bacSi", bacSi);
        } else {
            model.addAttribute("error", "Không thể lấy thông tin bác sĩ.");
        }
        return "bacsi/profileBS";
    }

    @GetMapping("/editProfile")
    public String editProfile(Model model, RedirectAttributes redirectAttributes) {
        BacSi bacSi = bacSiService.getBacSiDangNhap();
        if (bacSi == null) {
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy thông tin bác sĩ.");
            return "redirect:/bacsi/profile";
        }
        model.addAttribute("bacSi", bacSi);
        return "bacsi/editProfileBS";
    }

    // Xử lý cập nhật thông tin cá nhân + ảnh đại diện cho bác sĩ
    @PostMapping("/updateProfileBS")
    public String updateProfile(
            @ModelAttribute("bacSi") BacSi bacSi,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
        try {
            // Xử lý ảnh nếu có
            if (!file.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename().replaceAll("\\s+", "_");
                String uploadDir = System.getProperty("user.dir") + "/uploads/";

                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs(); // Tạo thư mục nếu chưa có
                }

                File saveFile = new File(uploadDir + fileName);
                file.transferTo(saveFile);

                bacSi.setHinh(fileName); // Lưu tên file vào DB
            }

            bacSiService.update(bacSi); // Cập nhật thông tin bác sĩ

            redirectAttributes.addFlashAttribute("success", "Cập nhật thông tin bác sĩ thành công!");
            return "redirect:/bacsi/profile";
        } catch (Exception e) {
            e.printStackTrace(); // Ghi log lỗi
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xử lý file. Vui lòng thử lại.");
            return "redirect:/bacsi/editProfileBS";
        }
    }

}
