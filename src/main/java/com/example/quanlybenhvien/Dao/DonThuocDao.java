package com.example.quanlybenhvien.Dao;

import com.example.quanlybenhvien.Entity.DonThuoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonThuocDao extends JpaRepository<DonThuoc, Integer> {
    List<DonThuoc> findByBenhAn_MaBenhAn(Integer maBenhAn);
}
