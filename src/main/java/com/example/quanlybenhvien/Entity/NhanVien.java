package com.example.quanlybenhvien.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "NHANVIEN")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NhanVien {
    @Id
    @Column(name = "ma_nhan_vien")
    private String maNhanVien;

    @Column(name = "ho_ten")
    private String hoTen;

    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "gioi_tinh")
    private String gioiTinh;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "SDT")
    private String sdt;

    @Column(name = "cccd")
    private String cccd;

    @Column(name = "email")
    private String email;

    @Column(name = "hinh")
    private String hinh;

    @Column(name = "ghi_chu")
    private String ghiChu;

    @ManyToOne
    @JoinColumn(name = "vai_tro", referencedColumnName = "ma_vai_tro")
    private Vaitro vaiTro;

    @OneToMany(mappedBy = "nhanVien", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<NhapThuoc> danhSachNhapThuoc;

    @Override
    public String toString() {
        return "NhanVien(maNhanVien=" + maNhanVien + ", hoTen=" + hoTen + ")";
    }
}
