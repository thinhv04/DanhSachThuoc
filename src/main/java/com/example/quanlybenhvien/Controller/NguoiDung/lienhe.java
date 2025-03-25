package com.example.quanlybenhvien.Controller.NguoiDung;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class lienhe {
    @RequestMapping("/lienhe")
    public String requestMethodName() {
        return "lienhe";
    }
}
