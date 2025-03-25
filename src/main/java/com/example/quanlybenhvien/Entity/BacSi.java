package com.example.quanlybenhvien.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BACSI")
public class BacSi {
    @Id
    @Column(name = "ma_bac_si", length = 20, nullable = false)
    private String maBacSi;

    @Column(name = "ho_ten", nullable = false)
    private String hoTen;

    @Column(name = "mat_khau", nullable = false)
    private String matKhau;

    @Column(name = "gioi_tinh", length = 10, nullable = false)
    private String gioiTinh;

    @Column(name = "dia_chi", nullable = false)
    private String diaChi;

    @Column(name = "SDT", length = 15, unique = true, nullable = false)
    private String sdt;

    @Column(name = "cccd", length = 20, unique = true, nullable = false)
    private String cccd;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "hinh", nullable = false)
    private String hinh;

    @Column(name = "ghi_chu", nullable = false)
    private String ghiChu;

    @ManyToOne
    @JoinColumn(name = "chuyen_khoa")
    @ToString.Exclude
    private ChuyenKhoa chuyenKhoa;

    @ManyToOne
    @JoinColumn(name = "vai_tro")
    @ToString.Exclude
    private Vaitro vaiTro;

    @OneToMany(mappedBy = "bacSi", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<BenhAn> danhSachBenhAn;

    @OneToMany(mappedBy = "bacSi", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<DonThuoc> danhSachDonThuoc;
}
