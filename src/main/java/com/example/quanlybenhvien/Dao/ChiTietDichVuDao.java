package com.example.quanlybenhvien.Dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quanlybenhvien.Entity.ChiTietDichVu;

import com.example.quanlybenhvien.Entity.LichKham;

public interface ChiTietDichVuDao extends JpaRepository<ChiTietDichVu, Integer> {
    List<ChiTietDichVu> findByLichKham(LichKham lichKham);
}
