package com.example.quanlybenhvien.Controller.BacSi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.quanlybenhvien.Service.BacSiService;
import com.example.quanlybenhvien.Service.QuanLyService;

@Controller
@RequestMapping("/bacsi")
public class BacSiBSController {
@Autowired
    BacSiService bacSiService;

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
    public String showAdminHome() {
        return "bacsi/bacsi";
    }
}
