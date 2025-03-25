package com.example.quanlybenhvien.Entity;

import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "HOADON_DONTHUOC")
public class HoaDonDonThuoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_hoa_don")
    private Integer maHoaDon;

    @ManyToOne
    @JoinColumn(name = "ma_benh_nhan", nullable = false)
    private BenhNhan benhNhan;

    @Column(name = "ngay_thanh_toan", nullable = false)
    private LocalDateTime ngayThanhToan;

    @Column(name = "hinh_thuc", nullable = false, length = 100)
    private String hinhThuc;

    @Column(name = "trang_thai", nullable = false, length = 50)
    private String trangThai;
}