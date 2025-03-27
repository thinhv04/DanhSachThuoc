package com.example.quanlybenhvien.Service;

import com.example.quanlybenhvien.Dao.ThuocDao;
import com.example.quanlybenhvien.Dao.NhapThuocDao;
import com.example.quanlybenhvien.Dao.KhoThuocDao;
import com.example.quanlybenhvien.Dao.NhanVienDao;
import com.example.quanlybenhvien.Entity.NhapThuoc;
import com.example.quanlybenhvien.Entity.Thuoc;
import com.example.quanlybenhvien.Entity.KhoThuoc;
import com.example.quanlybenhvien.Entity.NhanVien;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NhapThuocService {
    @Autowired
    private ThuocDao thuocRepository;
    
    @Autowired
    private NhapThuocDao nhapThuocRepository;
    
    @Autowired
    private KhoThuocDao khoThuocRepository;

    @Autowired
    private NhanVienDao nhanVienDao;
    
    @Transactional
    public NhapThuoc themNhapThuoc(NhapThuoc nhapThuoc) {
        System.out.println("📌 Dữ liệu nhập thuốc nhận được: " + nhapThuoc);

        if (nhapThuoc == null || nhapThuoc.getThuoc() == null || nhapThuoc.getNhanVien() == null) {
            throw new IllegalArgumentException("⚠ Dữ liệu nhập thuốc không hợp lệ!");
        }

        // 🔹 Kiểm tra dữ liệu nhân viên từ request
        if (nhapThuoc.getNhanVien() == null || nhapThuoc.getNhanVien().getMaNhanVien() == null) {
            throw new RuntimeException("⚠ Không tìm thấy thông tin nhân viên từ request!");
        }

        // 🔹 Chuẩn hóa mã nhân viên (loại bỏ khoảng trắng thừa)
        String maNV = nhapThuoc.getNhanVien().getMaNhanVien().trim();
        System.out.println("🔍 Mã nhân viên từ request (đã chuẩn hóa): " + maNV);

        // 🔹 Kiểm tra xem nhân viên có tồn tại trong DB không
        Optional<NhanVien> nhanVienOpt = nhanVienDao.findByMaNhanVien(maNV);
        if (nhanVienOpt.isEmpty()) {
            throw new RuntimeException("⚠ Nhân viên không tồn tại trong hệ thống! Mã: " + maNV);
        }

        // ✅ Nhân viên hợp lệ
        NhanVien nhanVien = nhanVienOpt.get();
        System.out.println("✅ Nhân viên hợp lệ: " + nhanVien.getHoTen());

        // 🔹 Gán nhân viên tìm thấy vào đối tượng nhập thuốc
        nhapThuoc.setNhanVien(nhanVien);

        // 🔹 Kiểm tra thuốc có tồn tại không
        Optional<Thuoc> optionalThuoc = thuocRepository.findById(nhapThuoc.getThuoc().getMaThuoc());
        if (optionalThuoc.isEmpty()) {
            throw new RuntimeException("⚠ Thuốc không tồn tại! Hãy thêm thuốc trước khi nhập.");
        }

        // ✅ Thuốc hợp lệ
        Thuoc thuoc = optionalThuoc.get();
        nhapThuoc.setThuoc(thuoc);
        System.out.println("✅ Thuốc hợp lệ: " + thuoc.getTenThuoc());

        // 🔥 Lưu vào bảng NHAPTHUOC
        NhapThuoc savedNhapThuoc = nhapThuocRepository.save(nhapThuoc);
        System.out.println("✅ Thuốc đã được nhập thành công vào NHAPTHUOC");

        // 🔄 Cập nhật kho thuốc
        Optional<KhoThuoc> optionalKhoThuoc = khoThuocRepository.findById(nhapThuoc.getThuoc().getMaThuoc());
        KhoThuoc khoThuoc;

        if (optionalKhoThuoc.isPresent()) {
            // Cập nhật số lượng thuốc trong kho
            khoThuoc = optionalKhoThuoc.get();
            khoThuoc.setSoLuongHienCo(khoThuoc.getSoLuongHienCo() + nhapThuoc.getSoLuongNhap());
            System.out.println("🔄 Cập nhật số lượng thuốc trong kho: " + khoThuoc.getSoLuongHienCo());
        } else {
            // Nếu thuốc chưa có trong kho, thêm mới
            khoThuoc = new KhoThuoc();
            khoThuoc.setMaThuoc(nhapThuoc.getThuoc().getMaThuoc());
            khoThuoc.setSoLuongHienCo(nhapThuoc.getSoLuongNhap());
            System.out.println("🆕 Thêm thuốc vào kho: " + khoThuoc.getSoLuongHienCo());
        }

        // ✅ Lưu kho thuốc
        khoThuocRepository.save(khoThuoc);
        return savedNhapThuoc;
    }

    public List<NhapThuoc> getAllNhapThuoc() {
        List<NhapThuoc> list = nhapThuocRepository.findAll();
        System.out.println("📌 Danh sách nhập thuốc: " + list);
        return list;
    }
}
