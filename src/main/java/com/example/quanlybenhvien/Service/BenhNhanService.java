package com.example.quanlybenhvien.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.quanlybenhvien.Dao.BenhNhanDao;
import com.example.quanlybenhvien.Entity.BenhNhan;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BenhNhanService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    BenhNhanDao benhNhanDao;

    // Constructor đúng tên lớp và tiêm PasswordEncoder

    public BenhNhanService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String dangKyNguoiDung(BenhNhan benhNhan) {
        // Kiểm tra nếu email bị null hoặc rỗng
        if (benhNhan.getEmail() == null || benhNhan.getEmail().isEmpty()) {
            return "Email không được để trống!";
        }

        // Kiểm tra email đã tồn tại
        if (benhNhanDao.findByEmail(benhNhan.getEmail()).isPresent()) {
            return "Email đã tồn tại!";
        }

        // Kiểm tra mật khẩu và xác nhận mật khẩu
        if (!benhNhan.getMatKhau().equals(benhNhan.getNhapLaiMatKhau())) {
            return "Mật khẩu và xác nhận mật khẩu không trùng khớp!";
        }

        // Mã hóa mật khẩu
        benhNhan.setMatKhau(passwordEncoder.encode(benhNhan.getMatKhau()));

        // Lưu người dùng vào cơ sở dữ liệu
        try {
            benhNhanDao.save(benhNhan);
        } catch (Exception e) {
            return "Đã xảy ra lỗi khi lưu người dùng!";
        }

        return "Đăng ký thành công!";
    }

    public BenhNhan findById(Integer id) {
        return benhNhanDao.findById(id).orElse(null);
    }

    // Cập nhật bệnh nhân
    public BenhNhan updateBenhNhan(Integer id, BenhNhan benhNhan) {
        // Kiểm tra xem bệnh nhân có tồn tại không
        BenhNhan existingBenhNhan = benhNhanDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bệnh nhân không tồn tại"));

        // Cập nhật các trường dữ liệu
        existingBenhNhan.setHoTen(benhNhan.getHoTen());
        existingBenhNhan.setNamSinh(benhNhan.getNamSinh());
        existingBenhNhan.setGioiTinh(benhNhan.getGioiTinh());
        existingBenhNhan.setSdt(benhNhan.getSdt());
        existingBenhNhan.setEmail(benhNhan.getEmail());
        existingBenhNhan.setMatKhau(benhNhan.getMatKhau());
        existingBenhNhan.setHinh(benhNhan.getHinh());
        existingBenhNhan.setTinhTp(benhNhan.getTinhTp());
        existingBenhNhan.setQuanHuyen(benhNhan.getQuanHuyen());
        existingBenhNhan.setDuong(benhNhan.getDuong());

        // Lưu lại bản ghi đã cập nhật
        return benhNhanDao.save(existingBenhNhan);
    }

    public void deleteBenhNhan(Integer id) {
        benhNhanDao.deleteById(id);
    }

    public List<BenhNhan> getAllBenhNhans() {
        return benhNhanDao.findAll();
    }

    // Tìm kiếm bệnh nhân theo tên
    public List<BenhNhan> searchByKeyword(String keyword) {
        return benhNhanDao.findByHoTenContainingIgnoreCase(keyword);
    }

    public void saveBenhNhan(BenhNhan benhNhan) {
        benhNhanDao.save(benhNhan);
    }

    public BenhNhan findByEmail(String email) {
        return benhNhanDao.findByEmail(email).orElse(null);
    }

}
