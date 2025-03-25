package com.example.quanlybenhvien.Entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BENHAN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BenhAn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_benh_an")
    private Integer maBenhAn;

    @Column(name = "ten_benh_an")
    private String tenBenhAn;

    @ManyToOne
    @JoinColumn(name = "ma_benh_nhan")
    private BenhNhan benhNhan;

    @ManyToOne
    @JoinColumn(name = "ma_bac_si")
    private BacSi bacSi;

    @Column(name = "ngay_kham")
    private Date ngayKham;

    @Column(name = "trieu_chung")
    private String trieuChung;

    @Column(name = "dieu_tri")
    private String dieuTri;

    @Column(name = "ghi_chu")
    private String ghiChu;
}
