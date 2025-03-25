package com.example.quanlybenhvien.Controller.ControllerAdvice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.quanlybenhvien.Entity.BenhNhan;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class UserControllerAdvice {
    @ModelAttribute("user")
    public BenhNhan addBenhNhan(HttpSession session)
    {
        return (BenhNhan) session.getAttribute("loggedInUser");
    }
}
