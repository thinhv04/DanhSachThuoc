package com.example.quanlybenhvien.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quanlybenhvien.Entity.BacSi;

@Repository
public interface BacSiDao extends JpaRepository<BacSi, String> {
    Optional<BacSi> findByhoTen(String hoTen); // Tìm bác sĩ theo họ tên

    boolean existsByMaBacSi(String maBacSi);

    boolean existsByEmail(String email);

    boolean existsByCccd(String cccd);

    boolean existsBySdt(String sdt);

    List<BacSi> findByHoTenContainingIgnoreCase(String hoTen);

    Optional<BacSi> findByEmail(String email);

    List<BacSi> findByChuyenKhoa_MaChuyenKhoa(String maChuyenKhoa);

    List<BacSi> findByHoTenOrSdt(String hoTen, String sdt);

    Optional<BacSi> findByMaBacSi(String maBacSi);

}
