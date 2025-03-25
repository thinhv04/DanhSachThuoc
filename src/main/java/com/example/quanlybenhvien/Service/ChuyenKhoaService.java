package com.example.quanlybenhvien.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quanlybenhvien.Dao.ChuyenKhoaDao;
import com.example.quanlybenhvien.Entity.ChuyenKhoa;

@Service
public class ChuyenKhoaService {
    @Autowired
    private ChuyenKhoaDao chuyenKhoaDao;

    public List<ChuyenKhoa> getAllChuyenKhoa() {
        return chuyenKhoaDao.findAll();
    }

    public void save(ChuyenKhoa chuyenKhoa) {
        chuyenKhoaDao.save(chuyenKhoa);
    }

    // Tìm chuyên khoa theo ID
    public Optional<ChuyenKhoa> findById(String id) {
        return chuyenKhoaDao.findById(id);
    }

    // Tìm chuyên khoa theo tên
    public Optional<ChuyenKhoa> findByName(String ten_chuyen_khoa) {
        return chuyenKhoaDao.findByTenChuyenKhoa(ten_chuyen_khoa);
    }

    public List<ChuyenKhoa> searchByName(String keyword) {
        return chuyenKhoaDao.findByTenChuyenKhoaContainingIgnoreCase(keyword);
    }

    // Xóa chuyên khoa theo ID
    public void deleteById(String id) {
        chuyenKhoaDao.deleteById(id);
    }

    public boolean existsById(String id) {
        return chuyenKhoaDao.existsById(id);
    }

    public boolean existsByTenCK(String ten_chuyen_khoa) {
        return chuyenKhoaDao.existsByTenChuyenKhoa(ten_chuyen_khoa);
    }
}
