package com.example.quanlybenhvien.Dao;

import com.example.quanlybenhvien.Entity.BenhNhan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BenhNhanDao extends JpaRepository<BenhNhan, Integer> {
    Optional<BenhNhan> findByEmail(String email);
    Optional<BenhNhan> findById(Integer maBenhNhan);
    boolean existsByEmail(String email);
    Optional<BenhNhan> findBySdt(String sdt);
    List<BenhNhan> findByHoTenContainingIgnoreCase(String hoTen);
    
}
