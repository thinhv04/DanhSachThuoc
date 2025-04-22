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

    public void giamSoLuong(String maThuoc, int soLuongGiam) {
        KhoThuoc khoThuoc = khoThuocRepository.findById(maThuoc).orElse(null);
        
    
        if (khoThuoc == null) {
            throw new RuntimeException("Không tìm thấy thuốc trong kho.");
        }
    
        int hienCo = khoThuoc.getSoLuongHienCo();
        if (hienCo < soLuongGiam) {
            throw new RuntimeException("Số lượng thuốc trong kho không đủ. Hiện còn: " + hienCo);
        }
    
        khoThuoc.setSoLuongHienCo(hienCo - soLuongGiam);
        khoThuocRepository.save(khoThuoc);
    }
    
    // Tăng số lượng thuốc vào kho
    public void tangSoLuong(String maThuoc, int soLuong) {
        KhoThuoc khoThuoc = khoThuocRepository.findById(maThuoc).orElse(null);
        
        if (khoThuoc == null) {
            throw new RuntimeException("Không tìm thấy thuốc trong kho.");
        }
        
        int hienCo = khoThuoc.getSoLuongHienCo();
        khoThuoc.setSoLuongHienCo(hienCo + soLuong);  // Tăng số lượng thuốc
        khoThuocRepository.save(khoThuoc);  // Lưu lại thay đổi
    }
    
}
