package com.example.quanlybenhvien.Entity;

import java.sql.Date;
import java.time.LocalDateTime;

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
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DONTHUOC")
public class DonThuoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ma_don_thuoc")
    private Integer maDonThuoc;

    @ManyToOne
    @JoinColumn(name = "ma_benh_an", nullable = false)
    private BenhAn benhAn;

    @ManyToOne
    @JoinColumn(name = "ma_bac_si", nullable = false)
    private BacSi bacSi;

    @Column(name = "ngay_lap", nullable = false)
    private LocalDateTime ngayLap = LocalDateTime.now();
}
