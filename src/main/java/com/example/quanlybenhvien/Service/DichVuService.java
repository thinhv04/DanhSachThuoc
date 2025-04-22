package com.example.quanlybenhvien.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.quanlybenhvien.Dao.DichVuDao;
import com.example.quanlybenhvien.Entity.DichVu;

@Service
public class DichVuService {
    private final DichVuDao dichVuRepository;

    public DichVuService(DichVuDao dichVuRepository) {
        this.dichVuRepository = dichVuRepository;
    }

    public List<DichVu> layTatCa() {
        return dichVuRepository.findAll();
    }

    public DichVu timTheoId(String id) {
        return dichVuRepository.findById(id).orElse(null);
    }

    public List<DichVu> timKiem(String keyword) {
        return dichVuRepository.findByTenDichVuContainingIgnoreCase(keyword);
    }

    public void luu(DichVu dichVu) {
        dichVuRepository.save(dichVu);
    }

    public void xoa(String id) {
        dichVuRepository.deleteById(id);
    }
    public List<DichVu> getAllDichVu() {
        return dichVuRepository.findAll();  // Sử dụng JPA repository
    }
    public DichVu getDichVuById(String maDichVu) {
        return dichVuRepository.findById(maDichVu).orElse(null);
    }
    
    
}
