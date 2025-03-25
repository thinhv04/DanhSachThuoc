package com.example.quanlybenhvien.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "KHO_THUOC")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class KhoThuoc {
    @Id
    @Column(name = "ma_thuoc", length = 20)
    private String maThuoc; // Mã thuốc (Khóa chính)

    @Column(name = "so_luong_hien_co", nullable = false)
    private int soLuongHienCo; // Số lượng hiện có trong kho

    @OneToOne
    @JoinColumn(name = "ma_thuoc", referencedColumnName = "ma_thuoc", insertable = false, updatable = false)
    private Thuoc thuoc; // Liên kết với bảng THUOC
}
