package com.example.quanlybenhvien.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.quanlybenhvien.Dao.BacSiDao;
import com.example.quanlybenhvien.Entity.BacSi;

@Service
public class BacSiService {
    @Autowired
    private BacSiDao bacsiDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Lấy tất cả bác sĩ
    public List<BacSi> getAllBacSi() {
        return bacsiDao.findAll();
    }
    // @Override
    // public UserDetails loadUserByUsername(String email) throws
    // UsernameNotFoundException {
    // BacSi bacSi = bacsiDao.findByEmail(email)
    // .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy bác sĩ với
    // email: " + email));

    // return User.builder()
    // .username(bacSi.getEmail())
    // .password(bacSi.getMatKhau()) // Đã mã hóa trong DB
    // .roles("VT01") // Gán quyền là "BACSI"
    // .build();
    // }
    // Lấy bác sĩ theo trang
    // public Page<BacSi> getBacSiByPage(int page, int size) {
    // Pageable pageable = PageRequest.of(page, size);
    // return bacsiDao.findAll(pageable);
    // }

    // Lưu bác sĩ
    public void save(BacSi bacSi) {
        bacSi.setMatKhau(passwordEncoder.encode(bacSi.getMatKhau()));
        bacsiDao.save(bacSi);
    }

    // Tìm bác sĩ theo ID
    public Optional<BacSi> findById(String maBacSi) {
        return bacsiDao.findById(maBacSi);
    }

    // Xóa bác sĩ theo ID
    public void deleteById(String maBacSi) {
        bacsiDao.deleteById(maBacSi);
    }

    // Kiểm tra xem bác sĩ có tồn tại không theo ID
    public boolean existsById(String maBacSi) {
        return bacsiDao.existsByMaBacSi(maBacSi);
    }

    public boolean existsByEmail(String email) {
        return bacsiDao.existsByEmail(email);
    }

    public boolean existsByCccd(String cccd) {
        return bacsiDao.existsByCccd(cccd);
    }

    public boolean existsBySdt(String sdt) {
        return bacsiDao.existsBySdt(sdt);
    }

    // Tìm bác sĩ theo tên
    public List<BacSi> searchByName(String keyword) {
        return bacsiDao.findByHoTenContainingIgnoreCase(keyword);
    }

    public BacSi getBacSiByEmail(String email) {
        return bacsiDao.findByEmail(email).orElse(null);
    }

    public List<BacSi> getBacSiByChuyenKhoa(String maChuyenKhoa) {
        return bacsiDao.findByChuyenKhoa_MaChuyenKhoa(maChuyenKhoa);
    }

}
