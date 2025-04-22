package com.example.quanlybenhvien.Service;

import com.example.quanlybenhvien.Dao.BenhAnDao;
import com.example.quanlybenhvien.Dao.LichKhamDao;
import com.example.quanlybenhvien.Entity.BenhAn;
import com.example.quanlybenhvien.Entity.LichKham;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BenhAnService {
    @Autowired
    private BenhAnDao benhAnDao;
    @Autowired
    private LichKhamDao lichKhamDao;

    public List<BenhAn> getAllBenhAn() {
        return benhAnDao.findAll();
    }

    public Optional<BenhAn> getBenhAnById(Integer id) {
        return benhAnDao.findById(id);
    }

    public void saveBenhAn(BenhAn benhAn) {
        benhAnDao.save(benhAn);
    }

    public void deleteBenhAn(Integer id) {
        benhAnDao.deleteById(id);
    }

    public boolean existsById(Integer id) {
        return benhAnDao.existsById(id);
    }

    // public List<BenhAn> getBenhAnByMaBenhNhan(Integer maBenhNhan) {
    //     return benhAnDao.findByBenhNhan_MaBenhNhan(maBenhNhan);
    // }

    // Thêm bệnh án cho lịch khám
    public BenhAn themBenhAnChoLichKham(Integer maLichKham, String tenBenhAn, String trieuChung, String dieuTri,
            String ghiChu) {
        // Tìm lịch khám theo maLichKham
        Optional<LichKham> lichKhamOpt = lichKhamDao.findById(maLichKham);
        if (lichKhamOpt.isPresent()) {
            LichKham lichKham = lichKhamOpt.get();

            // Tạo đối tượng BenhAn mới
            BenhAn benhAn = new BenhAn();
            benhAn.setTenBenhAn(tenBenhAn);
            benhAn.setBacSi(lichKham.getBacSi()); // Lấy thông tin bác sĩ từ lịch khám
            benhAn.setNgayKham(java.sql.Date.valueOf(lichKham.getNgayKham())); // Ngày khám từ lịch khám
            benhAn.setTrieuChung(trieuChung);
            benhAn.setDieuTri(dieuTri);
            benhAn.setGhiChu(ghiChu);

            // Lưu bệnh án vào cơ sở dữ liệu
            return benhAnDao.save(benhAn);
        } else {
            // Nếu không tìm thấy lịch khám, trả về null hoặc thông báo lỗi
            return null;
        }
    }
}
