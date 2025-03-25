package com.example.quanlybenhvien.Dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quanlybenhvien.Entity.QuanLy;




@Repository
public interface QuanLyDao extends JpaRepository<QuanLy, String> {
    Optional<QuanLy> findByEmail(String email);
    Optional<QuanLy> findByMaQuanLy(String maQuanLy);
}
