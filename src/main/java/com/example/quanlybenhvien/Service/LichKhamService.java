package com.example.quanlybenhvien.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.quanlybenhvien.Dao.ChiTietDichVuDao;
import com.example.quanlybenhvien.Dao.LichKhamDao;
import com.example.quanlybenhvien.Entity.BacSi;
import com.example.quanlybenhvien.Entity.BenhNhan;
import com.example.quanlybenhvien.Entity.ChiTietDichVu;
import com.example.quanlybenhvien.Entity.LichKham;
import com.example.quanlybenhvien.Entity.NhanVien;

@Service
public class LichKhamService {

    private final ChiTietDichVuDao chiTietDichVuDao;
    private final LichKhamDao lichKhamDao;

    // Constructor Injection
    public LichKhamService(ChiTietDichVuDao chiTietDichVuDao, LichKhamDao lichKhamDao) {
        this.chiTietDichVuDao = chiTietDichVuDao;
        this.lichKhamDao = lichKhamDao;
    }

    // Lưu lịch khám
    public void save(LichKham lichKham) {
        lichKhamDao.save(lichKham);
    }

    // Lấy danh sách lịch khám theo trạng thái (ví dụ: "Chờ xác nhận", "Đã xác nhận
    // bởi bác sĩ", v.v.)
    public List<LichKham> layLichKhamTheoTrangThai(String trangThai) {
        return lichKhamDao.findByTrangThai(trangThai);
    }

    // Lấy lịch khám theo bệnh nhân
    public List<LichKham> findByBenhNhan(BenhNhan benhNhan) {
        return lichKhamDao.findByBenhNhan(benhNhan);
    }

    // Lấy danh sách lịch khám chờ xác nhận (ví dụ trạng thái "Chờ xác nhận")
    public List<LichKham> getLichKhamChoXacNhan() {
        return lichKhamDao.findByTrangThai("Chờ xác nhận");
    }

    // Phương thức xác nhận lịch khám của nhân viên (nếu cần)
    public void xacNhanLichKham(int maLichKham, String trangThai, String ghiChu, NhanVien nhanVien) {
        Optional<LichKham> optional = lichKhamDao.findById(maLichKham);
        if (optional.isPresent()) {
            LichKham lichKham = optional.get();
            // Ví dụ: đặt trạng thái ban đầu khi nhân viên xác nhận
            lichKham.setTrangThai("Chờ bác sĩ xác nhận");
            lichKham.setGhiChu(ghiChu);
            lichKham.setNhanVien(nhanVien);
            lichKhamDao.save(lichKham);
        } else {
            throw new RuntimeException("Không tìm thấy lịch khám với mã: " + maLichKham);
        }
    }

    // Lấy danh sách lịch khám chờ bác sĩ xác nhận theo bác sĩ đăng nhập
    public List<LichKham> getLichKhamChoXacNhanTheoBacSi(BacSi bacSi) {
        // Đảm bảo trong LichKhamDao có phương thức: findByBacSiAndTrangThai(BacSi
        // bacSi, String trangThai)
        return lichKhamDao.findByBacSiAndTrangThai(bacSi, "Chờ bác sĩ xác nhận");
    }

    // Cập nhật lịch khám khi bác sĩ xác nhận hoặc hủy
    public LichKham xacNhanLichKhamTheoBacSi(int maLichKham, String trangThai, String ghiChu, BacSi bacSi) {
        LichKham lichKham = lichKhamDao.findById(maLichKham)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lịch khám với mã: " + maLichKham));

        lichKham.setTrangThai(trangThai);
        lichKham.setGhiChu(ghiChu);
        lichKham.setBacSi(bacSi);
        return lichKhamDao.save(lichKham);
    }

    // Lấy danh sách lịch khám đã xác nhận bởi bác sĩ (chưa có cập nhật khác)
    public List<LichKham> getLichKhamDaXacNhanTheoBacSi(BacSi bacSi) {
        return lichKhamDao.findByBacSiAndTrangThai(bacSi, "Đã xác nhận bởi bác sĩ");
    }

    public List<LichKham> getHoanThanhLichKham(BacSi bacSi) {
        return lichKhamDao.findByBacSiAndTrangThai(bacSi, "Đã hoàn thành");
    }

    // Lấy lịch khám theo ID
    public LichKham getLichKhamById(int maLichKham) {
        return lichKhamDao.findById(maLichKham).orElse(null);
    }

    // Đánh dấu lịch khám khi bệnh nhân đã đến
    public boolean danhDauBenhNhanDaDen(Integer maLichKham) {
        Optional<LichKham> optional = lichKhamDao.findById(maLichKham);
        if (optional.isPresent()) {
            LichKham lichKham = optional.get();
            // Chỉ cho phép đánh dấu nếu trạng thái hiện tại là "Đã xác nhận bởi bác sĩ"
            if (!"Đã xác nhận bởi bác sĩ".equals(lichKham.getTrangThai())) {
                System.out.println("⚠️ Lịch khám không ở trạng thái cho phép đánh dấu bệnh nhân đến!");
                return false;
            }
            lichKham.setTrangThai("Bệnh nhân đã đến");
            lichKhamDao.save(lichKham);
            System.out.println("✅ Đánh dấu bệnh nhân đã đến thành công!");
            return true;
        }
        System.out.println("❌ Không tìm thấy lịch khám với mã: " + maLichKham);
        return false;
    }

    // Đánh dấu lịch khám khi bệnh nhân đã đến
    public boolean hoanThanhLichKham(Integer maLichKham) {
        Optional<LichKham> optional = lichKhamDao.findById(maLichKham);
        if (optional.isPresent()) {
            LichKham lichKham = optional.get();
            // Chỉ cho phép đánh dấu nếu trạng thái hiện tại là "Đã xác nhận bởi bác sĩ"
            if (!"Bệnh nhân đã đến".equals(lichKham.getTrangThai())) {
                System.out.println("⚠️ Lịch khám không ở trạng thái cho phép đánh dấu hoàn thành !");
                return false;
            }
            lichKham.setTrangThai("Đã hoàn thành");
            lichKhamDao.save(lichKham);
            System.out.println("✅ Đánh dấu đã hoàn thành thành công!");
            return true;
        }
        System.out.println("❌ Không tìm thấy lịch khám với mã: " + maLichKham);
        return false;
    }

    // Huỷ lịch khám (không cho phép huỷ nếu bệnh nhân đã đến)
    public boolean huyLichKham(Integer maLichKham) {
        Optional<LichKham> optional = lichKhamDao.findById(maLichKham);
        if (optional.isPresent()) {
            LichKham lichKham = optional.get();
            if ("Bệnh nhân đã đến".equals(lichKham.getTrangThai())) {
                System.out.println("⚠️ Không thể huỷ lịch khám khi bệnh nhân đã đến!");
                return false;
            }
            lichKham.setTrangThai("Hủy bởi bác sĩ");
            lichKhamDao.save(lichKham);
            System.out.println("✅ Huỷ lịch khám thành công!");
            return true;
        }
        System.out.println("❌ Không tìm thấy lịch khám với mã: " + maLichKham);
        return false;
    }

    // Thêm dịch vụ vào lịch khám (kết hợp ChiTietDichVu)
    public LichKham addLichKhamWithDichVu(LichKham lichKham, List<ChiTietDichVu> chiTietDichVus) {
        // Bước 1: Lưu lịch khám
        LichKham savedLichKham = lichKhamDao.save(lichKham);
        // Bước 2: Lưu từng ChiTietDichVu và liên kết với lịch khám
        for (ChiTietDichVu chiTiet : chiTietDichVus) {
            chiTiet.setLichKham(savedLichKham);
            chiTietDichVuDao.save(chiTiet);
        }
        // Bước 3: Cập nhật lại danh sách dịch vụ trong lịch khám (nếu cần)
        savedLichKham.getChiTietDichVus().addAll(chiTietDichVus);
        return lichKhamDao.save(savedLichKham);
    }

    public List<LichKham> getLichKhamBenhNhanDaDenTheoBacSi(BacSi bacSi) {
        return lichKhamDao.findByBacSiAndTrangThai(bacSi, "Bệnh nhân đã đến");
    }

    public Optional<LichKham> findById(int maLichKham) {
        return lichKhamDao.findById(maLichKham);
    }

    public List<LichKham> getLichKhamDaHuy() {
        return lichKhamDao.findByTrangThai("Đã hủy");
    }

    public List<LichKham> getChoBacSi() {
        return lichKhamDao.findByTrangThai("Chờ bác sĩ xác nhận");
    }

    public List<LichKham> getLichKhamHoanThanh() {
        return lichKhamDao.findByTrangThai("Đã hoàn thành");
    }

    public List<LichKham> getAllLichKhams() {
        return lichKhamDao.findAll();
    }
}
