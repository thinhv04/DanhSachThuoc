package com.example.quanlybenhvien.Dao;

import com.example.quanlybenhvien.Entity.NhapThuoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NhapThuocDao extends JpaRepository<NhapThuoc, Integer> {
    List<NhapThuoc> findByNhanVien_MaNhanVien(String maNhanVien);
}