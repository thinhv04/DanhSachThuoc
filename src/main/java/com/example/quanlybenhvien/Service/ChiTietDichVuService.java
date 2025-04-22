package com.example.quanlybenhvien.Service;

import com.example.quanlybenhvien.Dao.ChiTietDichVuDao;
import com.example.quanlybenhvien.Entity.ChiTietDichVu;
import com.example.quanlybenhvien.Entity.DichVu;
import com.example.quanlybenhvien.Entity.LichKham;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ChiTietDichVuService {

    @Autowired
    private ChiTietDichVuDao chiTietDichVuDao;

    public void themDichVuVaoLichKham(ChiTietDichVu chiTietDichVu) {
        chiTietDichVuDao.save(chiTietDichVu);
    }
}
