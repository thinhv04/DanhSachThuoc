package com.example.quanlybenhvien.Entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "THUOC")
public class Thuoc {
    @Id
    @Column(name = "ma_thuoc", length = 20)
    private String maThuoc;

    @Column(name = "ten_thuoc", nullable = false)
    private String tenThuoc;

    @Column(name = "mo_ta", nullable = false)
    private String moTa;

    @Column(name = "gia_thuoc", nullable = false)
    private BigDecimal giaThuoc;

    @Column(name = "don_vi", nullable = false)
    private String donVi;

    @Column(name = "han_su_dung", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date hanSuDung;

    @OneToOne(mappedBy = "thuoc", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private KhoThuoc khoThuoc;

    @OneToMany(mappedBy = "thuoc", cascade = CascadeType.ALL)
    private List<NhapThuoc> danhSachNhapThuoc;
}
