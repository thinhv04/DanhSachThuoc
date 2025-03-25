package com.example.quanlybenhvien.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "QUANLY")
public class QuanLy {
    @Id
    @Column(name = "ma_quan_ly", length = 5)
    private String maQuanLy;

    @Column(name = "ho_ten", nullable = false)
    private String hoTen;

    @Column(name = "mat_khau", nullable = false)
    private String matKhau;

    @Column(name = "SDT", nullable = false, unique = true, length = 15)
    private String sdt; 

    @Column(name = "cccd", nullable = false, unique = true, length = 20)
    private String cccd;

    @Column(name = "email", nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "vai_tro", referencedColumnName = "ma_vai_tro")
    private Vaitro vaiTro;

    @Column(name = "hinh")
    private String hinh;

    @Column(name = "dia_chi", length = 255)
    private String diaChi;
}
