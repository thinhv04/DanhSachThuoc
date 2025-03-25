package com.example.quanlybenhvien.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CHITIETDONTHUOC")
public class ChiTietDonThuoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_chi_tiet_dt")
    private Integer maChiTietDt;

    @Column(name = "ma_don_thuoc", nullable = false)
    private Integer maDonThuoc;
    
    @Column(name = "ma_thuoc", length = 20, nullable = false)
    private String maThuoc;

    @Column(name = "soluong", nullable = false)
    private Integer soLuong;

    @Column(name = "lieu_luong", nullable = false)
    private String lieuLuong;
}


