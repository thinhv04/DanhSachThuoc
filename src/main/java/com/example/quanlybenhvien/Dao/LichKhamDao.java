package com.example.quanlybenhvien.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quanlybenhvien.Entity.BenhNhan;
import com.example.quanlybenhvien.Entity.LichKham;

@Repository
public interface LichKhamDao extends JpaRepository<LichKham, Integer> {
    List<LichKham> findByTrangThai(String trangThai);

    List<LichKham> findByBenhNhan(BenhNhan benhNhan);
}
