package com.example.quanlybenhvien.Service;

import com.example.quanlybenhvien.Dao.KhoThuocDao;
// import com.example.quanlybenhvien.Dao.KhoThuocRepository;
import com.example.quanlybenhvien.Entity.KhoThuoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KhoThuocService {
    @Autowired
    private KhoThuocDao khoThuocRepository;

    public List<KhoThuoc> getAllKhoThuoc() {
        return khoThuocRepository.findAll();
    }

    public KhoThuoc getKhoThuocById(String maThuoc) {
        return khoThuocRepository.findById(maThuoc).orElse(null);
    }

    public KhoThuoc saveKhoThuoc(KhoThuoc khoThuoc) {
        return khoThuocRepository.save(khoThuoc);
    }

    // Tìm kiếm thuốc trong kho theo mã hoặc tên thuốc
    public List<KhoThuoc> searchKhoThuoc(String keyword) {
        return khoThuocRepository.searchByKeyword(keyword);
    }

    // Cập nhật số lượng thuốc
    public void updateSoLuongThuoc(String maThuoc, int soLuongMoi) {
        KhoThuoc khoThuoc = khoThuocRepository.findById(maThuoc).orElse(null);
        if (khoThuoc != null) {
            khoThuoc.setSoLuongHienCo(soLuongMoi);
            khoThuocRepository.save(khoThuoc);
        }
    }
}
