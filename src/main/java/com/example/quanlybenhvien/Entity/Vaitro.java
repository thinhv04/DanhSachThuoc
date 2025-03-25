package com.example.quanlybenhvien.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "VAITRO")
public class Vaitro {
    @Id
    @Column(name = "ma_vai_tro")
    private String maVaiTro;

    @Column(name = "ten_vai_tro")
    private String tenVaiTro;

    @OneToMany(mappedBy = "vaiTro", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<BacSi> bacSiList;

    @OneToMany(mappedBy = "vaiTro", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<NhanVien> nhanVienList;

    @OneToOne(mappedBy = "vaiTro", cascade = CascadeType.ALL)
    @ToString.Exclude
    private QuanLy quanLy;
}
