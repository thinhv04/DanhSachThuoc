package com.example.quanlybenhvien.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DICHVU")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DichVu {
    @Id
    @Column(name = "ma_dich_vu")
    private String maDichVu;

    @Column(name = "ten_dich_vu")
    private String tenDichVu;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(nullable = false, name = "gia")
    private Double gia;
    
    
}
