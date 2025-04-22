package com.example.quanlybenhvien.Dao;

import com.example.quanlybenhvien.Entity.ChiTietDonThuoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChiTietDonThuocDao extends JpaRepository<ChiTietDonThuoc, Integer> {
    List<ChiTietDonThuoc> findByMaDonThuoc(Integer maDonThuoc);
}
