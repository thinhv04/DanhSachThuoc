package com.example.quanlybenhvien.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quanlybenhvien.Dao.HoadDonDao;
import com.example.quanlybenhvien.Entity.HoaDonLichKham;

@Service
public class HoaDonService {
    @Autowired
    private HoadDonDao hoadDonDao;

    // Phương thức để tìm hóa đơn theo mã hóa đơn
    public Optional<HoaDonLichKham> findById(Integer maHoaDon) {
        return hoadDonDao.findById(maHoaDon);
    }

    public List<HoaDonLichKham> findAll() {
        return hoadDonDao.findAll(); // Giả sử bạn đã có phương thức này trong HoadDonDao
    }

    // Phương thức để tính tổng tiền cho hóa đơn (nếu bạn có thông tin dịch vụ)
    public Double tinhTongTien(HoaDonLichKham hoaDonLichKham) {
        return hoaDonLichKham.getTongTien();
    }

    public void hoaDonLichKham(HoaDonLichKham hoaDonLichKham) {
        hoadDonDao.save(hoaDonLichKham);
    }
}
