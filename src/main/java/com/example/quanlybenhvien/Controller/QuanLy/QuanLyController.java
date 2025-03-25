package com.example.quanlybenhvien.Controller.QuanLy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quanlybenhvien.Dao.QuanLyDao;
import com.example.quanlybenhvien.Entity.QuanLy;
import com.example.quanlybenhvien.Entity.Vaitro;
import com.example.quanlybenhvien.Service.QuanLyService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/quanly/trangchu")
public class QuanLyController {
    @Autowired
    QuanLyDao quanLyDao;

    @Autowired
    QuanLyService quanLyService;

    @GetMapping("/quanly")
    public String findAllQuanLy(Model model) {
        model.addAttribute("quanly", quanLyDao.findAll());
        model.addAttribute("ql", new QuanLy());
        return "admin/quanly";
    }

    @PostMapping("/quanly")
    public String save(@ModelAttribute QuanLy ql , Model model) {
        //TODO: process POST request
        QuanLy quanLy = quanLyService.findQuanLyByID(ql.getMaQuanLy());
        if (quanLy == null) {
            model.addAttribute("message", "Thêm quản lý thành công!");
        } else {
            model.addAttribute("message", "Cập nhật quản lý thành công!");
        }

        quanLyService.saveQuanLy(quanLy);
        return "redirect:/quanly/trangchu/quanly";
    }
    
}
