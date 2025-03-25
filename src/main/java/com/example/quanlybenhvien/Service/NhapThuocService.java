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
    private NhanVienDao nhanVienRepository;
    
    @Transactional
    public NhapThuoc themNhapThuoc(NhapThuoc nhapThuoc) {
        System.out.println("📌 Dữ liệu nhập thuốc: " + nhapThuoc);
        System.out.println("🔍 Mã nhân viên nhận từ form: " + nhapThuoc.getNhanVien().getMaNhanVien());


        // 🔍 Kiểm tra nhân viên có tồn tại không
        if (nhapThuoc.getNhanVien() == null || nhapThuoc.getNhanVien().getMaNhanVien() == null) {
            throw new RuntimeException("⚠ Không tìm thấy thông tin nhân viên!");
        }

        NhanVien nhanVien = nhanVienRepository.findById(nhapThuoc.getNhanVien().getMaNhanVien())
            .orElseThrow(() -> new RuntimeException("⚠ Nhân viên không tồn tại trong hệ thống!"));

        nhapThuoc.setNhanVien(nhanVien); // Gán nhân viên tìm thấy vào NhapThuoc

        // 🔍 Kiểm tra thuốc có tồn tại không
        Optional<Thuoc> optionalThuoc = thuocRepository.findById(nhapThuoc.getThuoc().getMaThuoc());
        if (optionalThuoc.isEmpty()) {
            throw new RuntimeException("⚠ Thuốc không tồn tại! Hãy thêm thuốc trước khi nhập.");
        }

        nhapThuoc.setThuoc(optionalThuoc.get());

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

        khoThuocRepository.save(khoThuoc);
        return savedNhapThuoc;
    }


    public List<NhapThuoc> getAllNhapThuoc() {
        List<NhapThuoc> list = nhapThuocRepository.findAll();
        System.out.println("📌 Danh sách nhập thuốc: " + list);
        return list;
    }

}