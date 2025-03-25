package com.example.quanlybenhvien.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.quanlybenhvien.Entity.NhanVien;

@Repository
public interface NhanVienDao extends JpaRepository<NhanVien, String> {
    Optional<NhanVien> findByHoTen(String hoTen); // Tìm nhân viên theo họ tên

    boolean existsByMaNhanVien(String maNhanVien);

    boolean existsByEmail(String email);

    boolean existsByCccd(String cccd);

    boolean existsBySdt(String sdt);

    List<NhanVien> findByHoTenContainingIgnoreCase(String hoTen);

    Optional<NhanVien> findByEmail(String email);
}
