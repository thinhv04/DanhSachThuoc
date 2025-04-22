package com.example.quanlybenhvien.Dao;

import com.example.quanlybenhvien.Entity.BenhAn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BenhAnDao extends JpaRepository<BenhAn, Integer> {
    // List<BenhAn> findByBenhNhan_MaBenhNhan(Integer maBenhNhan); // Tìm bệnh án theo mã bệnh nhân
}

