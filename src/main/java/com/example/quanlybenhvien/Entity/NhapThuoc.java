package com.example.quanlybenhvien.Entity;

import java.time.LocalDate;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "NHAPTHUOC")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NhapThuoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_nhap_thuoc")
    private Integer maNhapThuoc; // Mã nhập thuốc (Primary Key, tự động tăng)

    @ManyToOne
    @JoinColumn(name = "ma_nhan_vien", nullable = false)
    private NhanVien nhanVien; // Nhân viên nhập thuốc (FK -> NHANVIEN)

    @ManyToOne
    @JoinColumn(name = "ma_thuoc", nullable = false)
    private Thuoc thuoc; // Thuốc được nhập (FK -> THUOC)

    @Column(name = "so_luong_nhap", nullable = false)
    private Integer soLuongNhap; // Số lượng nhập (phải > 0)

    @Column(name = "ngay_nhap", nullable = false)
    private LocalDate ngayNhap; // Ngày nhập thuốc (mặc định GETDATE)

    @Column(name = "nha_cung_cap", nullable = false, length = 255)
    private String nhaCungCap; // Nhà cung cấp thuốc

    @Column(name = "ghi_chu", length = 255)
    private String ghiChu; // Ghi chú (nếu có)

    @Override
    public String toString() {
        return "NhapThuoc(maNhapThuoc=" + maNhapThuoc + ")";
    }
}
