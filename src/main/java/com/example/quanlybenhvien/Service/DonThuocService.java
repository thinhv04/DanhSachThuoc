package com.example.quanlybenhvien.Service;

import com.example.quanlybenhvien.Dao.DonThuocDao;
import com.example.quanlybenhvien.Entity.DonThuoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DonThuocService {
    void save(DonThuoc donThuoc);
    DonThuoc saveAndReturn(DonThuoc donThuoc);
    List<DonThuoc> findByBenhAn(Integer maBenhAn);  // Thay tên phương thức cho đồng nhất
}


@Service
class DonThuocServiceImpl implements DonThuocService {

    @Autowired
    private DonThuocDao donThuocDao;

    @Override
    public void save(DonThuoc donThuoc) {
        donThuocDao.save(donThuoc);
    }

    @Override
    public List<DonThuoc> findByBenhAn(Integer maBenhAn) {  // Đổi tên phương thức cho đồng nhất
        return donThuocDao.findByBenhAn_MaBenhAn(maBenhAn);
    }

    @Override
    public DonThuoc saveAndReturn(DonThuoc donThuoc) {
        return donThuocDao.save(donThuoc); // Hibernate sẽ gán maDonThuoc sau khi lưu
    }
    

}
