package com.example.quanlybenhvien.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quanlybenhvien.Entity.HoaDonLichKham;

@Repository
public interface HoadDonDao extends JpaRepository<HoaDonLichKham, Integer> {

}
