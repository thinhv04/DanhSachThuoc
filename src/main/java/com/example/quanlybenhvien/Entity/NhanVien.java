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

@Entity
@Table(name = "NHANVIEN")
@Data
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

    @OneToMany(mappedBy = "nhanVien", cascade = CascadeType.ALL)
    private List<NhapThuoc> danhSachNhapThuoc;

}
