package com.example.quanlybenhvien.Service;


import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.example.quanlybenhvien.Dao.VaiTroDao;
import com.example.quanlybenhvien.Entity.Vaitro;


@Service
public class VaiTroService {
    private final VaiTroDao vaiTroDao;
    public VaiTroService(VaiTroDao vaiTroDao)
    {
        this.vaiTroDao = vaiTroDao;
    }

    public List<Vaitro> findAll()
    {
        return vaiTroDao.findAll();
    }
    public Vaitro findVaitroByID(String maVaiTro)
    {
        return vaiTroDao.findByMaVaiTro(maVaiTro).orElse(null);
    }
    public void saveVaiTro(Vaitro maVaiTro)
    {
        vaiTroDao.save(maVaiTro);
    }
    public void deleteVaiTro(String maVaiTro)
    {
        vaiTroDao.deleteById(maVaiTro);
    }
    public List<Vaitro> timKiemVaiTro(String keyword) {
    if (keyword == null || keyword.trim().isEmpty()) {
        return vaiTroDao.findAll();  // Trả về danh sách đầy đủ
    }
    List<Vaitro> list = vaiTroDao.findByTenVaiTroContainingIgnoreCase(keyword);
    
    // Kiểm tra nếu list null, trả về danh sách rỗng để tránh lỗi
    return list != null ? list : new ArrayList<>();
}
}
