package com.example.quanlybenhvien.Service;

import com.example.quanlybenhvien.Dao.ChiTietDonThuocDao;
import com.example.quanlybenhvien.Entity.ChiTietDonThuoc;
import org.springframework.stereotype.Service;
import com.example.quanlybenhvien.Dao.KhoThuocDao;
import com.example.quanlybenhvien.Entity.KhoThuoc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ChiTietDonThuocService {

    private final ChiTietDonThuocDao chiTietDonThuocDao;
    private final KhoThuocService khoThuocService;

    public ChiTietDonThuocService(ChiTietDonThuocDao chiTietDonThuocDao, KhoThuocService khoThuocService) {
        this.chiTietDonThuocDao = chiTietDonThuocDao;
        this.khoThuocService = khoThuocService;
    }

    @Transactional
    public void keThuoc(ChiTietDonThuoc chiTiet) {
        // Gọi service trừ kho để xử lý logic và validation
        khoThuocService.giamSoLuong(chiTiet.getMaThuoc(), chiTiet.getSoLuong());

        // Lưu chi tiết đơn thuốc
        chiTietDonThuocDao.save(chiTiet);
    }

    public List<ChiTietDonThuoc> findByMaDonThuoc(Integer maDonThuoc) {
        return chiTietDonThuocDao.findByMaDonThuoc(maDonThuoc);
    }
    

    // Phương thức tìm chi tiết đơn thuốc theo id
    public Optional<ChiTietDonThuoc> findById(Integer id) {
        return chiTietDonThuocDao.findById(id);
    }

    // Phương thức xóa chi tiết đơn thuốc theo id
    @Transactional
public void deleteById(Integer id) {
    Optional<ChiTietDonThuoc> chiTietOpt = chiTietDonThuocDao.findById(id);
    if (chiTietOpt.isPresent()) {
        chiTietDonThuocDao.delete(chiTietOpt.get());
    }
}

}
