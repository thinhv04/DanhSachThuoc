package com.example.quanlybenhvien.Entity;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LICHKHAM")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LichKham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_lich_kham")
    private Integer maLichKham;

    @ManyToOne
    @JoinColumn(name = "ma_benh_nhan", nullable = false)
    private BenhNhan benhNhan;

    @ManyToOne
    @JoinColumn(name = "ma_nhan_vien", nullable = true)
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "ma_chuyen_khoa", nullable = false)
    private ChuyenKhoa chuyenKhoa;

    @ManyToOne
    @JoinColumn(name = "ma_bac_si")
    private BacSi bacSi;

    @Column(name = "ngay_kham", nullable = false)
    private LocalDate ngayKham;

    @Column(name = "gio_kham", nullable = false)
    private LocalTime gioKham;

    @Column(name = "trang_thai", nullable = false)
    private String trangThai;

    @Column(name = "ghi_chu", nullable = false)
    private String ghiChu;

    @OneToMany(mappedBy = "lichKham", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChiTietDichVu> chiTietDichVus;
}
