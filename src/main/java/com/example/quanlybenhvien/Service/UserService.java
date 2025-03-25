package com.example.quanlybenhvien.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.quanlybenhvien.Dao.BacSiDao;
import com.example.quanlybenhvien.Dao.NhanVienDao;
import com.example.quanlybenhvien.Dao.QuanLyDao;
import com.example.quanlybenhvien.Entity.BacSi;
import com.example.quanlybenhvien.Entity.NhanVien;
import com.example.quanlybenhvien.Entity.QuanLy;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    QuanLyDao quanLyDao;

    @Autowired
    BacSiDao bacSiDao;

    @Autowired
    NhanVienDao nhanVienDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Check NhanVien
        Optional<NhanVien> optionalNhanVien = nhanVienDao.findByEmail(email);
        if (optionalNhanVien.isPresent()) {
            NhanVien nhanVien = optionalNhanVien.get();
            System.out.println("Đăng nhập NhanVien: " + nhanVien.getEmail());
            System.out.println("Đăng nhập Vaitro: " + nhanVien.getVaiTro().getTenVaiTro());
            System.out.println("Đăng nhập Vaitro: " + nhanVien.getVaiTro().getMaVaiTro());
            return User.builder()
                    .username(nhanVien.getEmail())
                    .password(nhanVien.getMatKhau())
                    .roles(nhanVien.getVaiTro().getMaVaiTro().trim()) // lấy từ entity NhanVien
                    .build();

        }

        // Check QuanLy
        Optional<QuanLy> optionalQuanLy = quanLyDao.findByEmail(email);
        if (optionalQuanLy.isPresent()) {
            QuanLy admin = optionalQuanLy.get();
            System.out.println("Đăng nhập QuanLy: " + admin.getEmail());
            return User.builder()
                    .username(admin.getEmail())
                    .password(admin.getMatKhau())
                    .roles(admin.getVaiTro().getMaVaiTro()) // lấy từ entity QuanLy
                    .build();
        }

        // Check BacSi
        Optional<BacSi> optionalBacSi = bacSiDao.findByEmail(email);
        if (optionalBacSi.isPresent()) {
            BacSi bacSi = optionalBacSi.get();
            return User.builder()
                    .username(bacSi.getEmail())
                    .password(bacSi.getMatKhau())
                    .roles(bacSi.getVaiTro().getMaVaiTro()) // lấy từ entity BacSi
                    .build();
        }

        throw new UsernameNotFoundException("Không tìm thấy tài khoản: " + email);
    }

    // Các phương thức register nếu cần
    public void registerUser(QuanLy user) {
        user.setMatKhau(passwordEncoder.encode(user.getMatKhau()));
        quanLyDao.save(user);
    }

    public void registerBacSi(BacSi bacSi) {
        bacSi.setMatKhau(passwordEncoder.encode(bacSi.getMatKhau()));
        bacSiDao.save(bacSi);
    }

    public void registerNhanVien(NhanVien nhanVien) {
        nhanVien.setMatKhau(passwordEncoder.encode(nhanVien.getMatKhau()));
        nhanVienDao.save(nhanVien);
    }

}
