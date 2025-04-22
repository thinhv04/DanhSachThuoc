package com.example.quanlybenhvien.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CHITIETDICHVU")
public class ChiTietDichVu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_chi_tiet_dich_vu")
    private Integer maChiTietDichVu;

    @ManyToOne
    @JoinColumn(name = "ma_lich_kham", nullable = false)
    private LichKham lichKham;

    @ManyToOne
    @JoinColumn(name = "ma_dich_vu", nullable = false)
    private DichVu dichVu;

    @Column(name = "soluong", nullable = false)
    private Integer soLuong;
}