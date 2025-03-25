package com.example.quanlybenhvien.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quanlybenhvien.Entity.DichVu;

public interface DichVuDao extends JpaRepository<DichVu, String>{
    List<DichVu> findByTenDichVuContainingIgnoreCase(String keyword);
}
