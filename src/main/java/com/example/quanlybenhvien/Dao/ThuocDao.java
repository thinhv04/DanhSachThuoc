package com.example.quanlybenhvien.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quanlybenhvien.Entity.Thuoc;

@Repository
public interface ThuocDao extends JpaRepository<Thuoc, String>{
    List<Thuoc> findByTenThuocContaining(String tenThuoc);
    List<Thuoc> findByMaThuocContainingOrTenThuocContaining(String maThuoc, String tenThuoc);
}
