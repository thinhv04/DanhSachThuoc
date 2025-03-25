package com.example.quanlybenhvien.Entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "HOADON_CHITIET_DONTHUOC")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HoaDonChiTietDonThuoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_chi_tiet_hd")
    private Integer maChiTietHD;

    @ManyToOne
    @JoinColumn(name = "ma_hoa_don", nullable = false)
    private HoaDonDonThuoc hoaDon;

    @ManyToOne
    @JoinColumn(name = "ma_don_thuoc", nullable = false)
    private DonThuoc donThuoc;

    @Column(name = "tong_tien", nullable = false, precision = 10, scale = 2)
    private BigDecimal tongTien;
}
