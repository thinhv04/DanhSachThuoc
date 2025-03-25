package com.example.quanlybenhvien.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.quanlybenhvien.Dao.QuanLyDao;
import com.example.quanlybenhvien.Entity.QuanLy;

import jakarta.transaction.Transactional;

@Service
public class QuanLyService {
    @Autowired
    QuanLyDao quanlLyDao;
    @Autowired
    PasswordEncoder passwordEncoder;

    // @Override
    //  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    //     Optional<QuanLy> optionalNguoiDung = quanlLyDao.findByEmail(email);
    //     if (optionalNguoiDung.isEmpty()) {
    //         throw new UsernameNotFoundException("Không tìm thấy người dùng");
    //     }

    //     QuanLy admin = optionalNguoiDung.get();
    //     if (!"VT00".equals(admin.getVaiTro().getMaVaiTro())) {
    //         throw new UsernameNotFoundException("Bạn không có quyền đăng nhập vào hệ thống ADMIN");
    //     }

    //     return User.builder()
    //             .username(admin.getEmail())
    //             .password(admin.getMatKhau())
    //             .roles("VT00") // Chỉ admin
    //             .build();
    // }
    // public void registerUser(QuanLy user) {
    //     user.setMatKhau(passwordEncoder.encode(user.getMatKhau())); // Mã hóa mật khẩu
    //     quanlLyDao.save(user);
    // }

    // @Transactional
    // public void updatePassword(String email, String newPassword) {
    //     QuanLy user = quanlLyDao.findByEmail(email)
    //             .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        
    //     // Mã hóa mật khẩu trước khi cập nhật
    //     user.setMatKhau(passwordEncoder.encode(newPassword));
    //     quanlLyDao.save(user);
    // }    

    public QuanLy findQuanLyByID(String maQuanLy)
    {
        return quanlLyDao.findByMaQuanLy(maQuanLy).orElse(null);
    }
    public void saveQuanLy(QuanLy maQuanLy)
    {
        quanlLyDao.save(maQuanLy);
    }
}


