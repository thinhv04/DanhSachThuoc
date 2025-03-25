package com.example.quanlybenhvien.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quanlybenhvien.Dao.LichKhamDao;
import com.example.quanlybenhvien.Entity.BenhNhan;
import com.example.quanlybenhvien.Entity.LichKham;

@Service
public class LichKhamService {
    @Autowired
    private LichKhamDao lichKhamDao;

    public void save(LichKham lichKham) {
        lichKhamDao.save(lichKham);
    }

    // Lấy danh sách lịch khám theo trạng thái
    public List<LichKham> layLichKhamTheoTrangThai(String trangThai) {
        return lichKhamDao.findByTrangThai(trangThai);
    }

    public List<LichKham> findByBenhNhan(BenhNhan benhNhan) {
        return lichKhamDao.findByBenhNhan(benhNhan);
    }
}
