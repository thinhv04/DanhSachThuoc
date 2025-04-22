package com.example.quanlybenhvien.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.quanlybenhvien.Dao.NhanVienDao;
import com.example.quanlybenhvien.Entity.NhanVien;

@Service
public class NhanVienService {
    @Autowired
    private NhanVienDao nhanVienDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Lấy tất cả nhân viên
    public List<NhanVien> getAllNhanVien() {
        return nhanVienDao.findAll();
    }

    // Lưu nhân viên
    public void save(NhanVien nhanVien) {
        nhanVien.setMatKhau(passwordEncoder.encode(nhanVien.getMatKhau()));
        nhanVienDao.save(nhanVien);
    }

    // Tìm nhân viên theo ID
    public Optional<NhanVien> findById(String maNhanVien) {
        return nhanVienDao.findById(maNhanVien);
    }

    // Xóa nhân viên theo ID
    public void deleteById(String maNhanVien) {
        nhanVienDao.deleteById(maNhanVien);
    }

    // Kiểm tra xem nhân viên có tồn tại không theo ID
    public boolean existsById(String maNhanVien) {
        return nhanVienDao.existsByMaNhanVien(maNhanVien);
    }

    public boolean existsByEmail(String email) {
        return nhanVienDao.existsByEmail(email);
    }

    public boolean existsByCccd(String cccd) {
        return nhanVienDao.existsByCccd(cccd);
    }

    public boolean existsBySdt(String sdt) {
        return nhanVienDao.existsBySdt(sdt);
    }

    // Tìm nhân viên theo tên
    public List<NhanVien> searchByName(String keyword) {
        return nhanVienDao.findByHoTenContainingIgnoreCase(keyword);
    }

    public NhanVien getNhanVienByEmail(String email) {
        return nhanVienDao.findByEmail(email).orElse(null);
    }

     // **Lấy thông tin nhân viên đang đăng nhập**
    public NhanVien getNhanVienDangNhap() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return getNhanVienByEmail(authentication.getName());
        }
        return null;
    }
}
