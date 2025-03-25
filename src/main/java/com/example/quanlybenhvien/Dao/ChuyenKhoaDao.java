package com.example.quanlybenhvien.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quanlybenhvien.Entity.ChuyenKhoa;


@Repository
public interface ChuyenKhoaDao  extends JpaRepository<ChuyenKhoa,String>{
    Optional<ChuyenKhoa> findByTenChuyenKhoa(String ten_chuyen_khoa); // Tìm chuyên khoa theo tên

    boolean existsById(@SuppressWarnings("null") String id); // Kiểm tra mã CK có tồn tại không

    boolean existsByTenChuyenKhoa(String ten_chuyen_khoa); // Kiểm tra tên CK có tồn tại không

    List<ChuyenKhoa> findByTenChuyenKhoaContainingIgnoreCase(String ten_chuyen_khoa);

}
