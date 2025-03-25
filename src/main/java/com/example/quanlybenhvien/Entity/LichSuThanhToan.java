package com.example.quanlybenhvien.Entity;

import java.math.BigDecimal;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LICHSUTHANHTOAN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LichSuThanhToan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_lich_su")
    private Integer maLichSu;

    @ManyToOne
    @JoinColumn(name = "ma_benh_nhan", nullable = false)
    private BenhNhan benhNhan;

    @ManyToOne
    @JoinColumn(name = "ma_hoa_don", nullable = false)
    private HoaDonDonThuoc hoaDon;

    @ManyToOne
    @JoinColumn(name = "ma_hoa_don_donthuoc")
    private HoaDonDonThuoc hoaDonDonThuoc;

    @ManyToOne
    @JoinColumn(name = "ma_dich_vu", nullable = false)
    private DichVu dichVu;

    @Column(name = "ngay_su_dung", nullable = false)
    private LocalDateTime ngaySuDung;

    @Column(name = "so_luong", nullable = false)
    private int soLuong;

    @Column(name = "thanh_tien", nullable = false, precision = 10, scale = 2)
    private BigDecimal thanhTien;

    @Column(name = "ghi_chu", length = 255)
    private String ghiChu;
}
