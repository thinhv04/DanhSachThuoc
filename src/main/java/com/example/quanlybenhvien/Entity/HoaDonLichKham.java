package com.example.quanlybenhvien.Entity;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "HOADON")
public class HoaDonLichKham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_hoa_don")
    private Integer maHoaDon;

    @OneToOne
    @JoinColumn(name = "ma_lich_kham", nullable = false, unique = true)
    private LichKham lichKham;

    @Column(name = "ngay_thanh_toan", nullable = false)
    private LocalDate ngayThanhToan;

    @Column(name = "tong_tien", nullable = false)
    private Double tongTien;

    @Column(name = "hinh_thuc", nullable = false)
    private String hinhThuc;

    @Column(name = "trang_thai", nullable = false)
    private String trangThai;
}
