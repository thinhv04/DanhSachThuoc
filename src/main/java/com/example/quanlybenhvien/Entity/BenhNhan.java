package com.example.quanlybenhvien.Entity;


import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BENHNHAN")
public class BenhNhan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_benh_nhan")
    private Integer maBenhNhan;

    @Column(name = "ho_ten")
    private String hoTen;

    @Column(name = "nam_sinh")
    private LocalDate namSinh;

    @Column(name = "gioi_tinh")
    private String gioiTinh;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "email")
    private String email;

    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "hinh")
    private String hinh;

    @Column(name = "tinh_tp")
    private String tinhTp;

    @Column(name = "quan_huyen")
    private String quanHuyen;

    @Column(name = "duong")
    private String duong;
    @Transient
    private String nhapLaiMatKhau;

    @OneToMany(mappedBy = "benhNhan", cascade = CascadeType.ALL)
    private List<BenhAn> danhSachBenhAn;
}
