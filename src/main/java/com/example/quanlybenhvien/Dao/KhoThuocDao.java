package com.example.quanlybenhvien.Dao;

import com.example.quanlybenhvien.Entity.KhoThuoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KhoThuocDao extends JpaRepository<KhoThuoc, String> {
    
    // Tìm kiếm thuốc theo mã hoặc tên thuốc
    @Query("SELECT k FROM KhoThuoc k WHERE k.maThuoc LIKE %?1% OR k.thuoc.tenThuoc LIKE %?1%")
    List<KhoThuoc> searchByKeyword(String keyword);
}
