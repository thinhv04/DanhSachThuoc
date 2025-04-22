package com.example.quanlybenhvien.Entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class LichKhamDichVuDTO {
    private LichKham lichKham;
    // Khởi tạo mặc định 1 dòng dịch vụ, bác sĩ có thể thêm nhiều dòng hơn từ giao diện.
    private List<ChiTietDichVu> chiTietDichVus = new ArrayList<>();
}