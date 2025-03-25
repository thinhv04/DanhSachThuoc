package com.example.quanlybenhvien.Service;

import org.springframework.stereotype.Service;

import com.example.quanlybenhvien.Dao.ThuocDao;
import com.example.quanlybenhvien.Entity.Thuoc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ThuocService {
    private final ThuocDao thuocRepository;

    public ThuocService(ThuocDao thuocRepository) {
        this.thuocRepository = thuocRepository;
    }

    public Thuoc themthuocmoi(Thuoc thuoc) {
        if (thuoc.getMaThuoc().isEmpty() || thuoc.getTenThuoc().isEmpty()) {
            throw new RuntimeException("Mã thuốc và tên thuốc không được để trống.");
        }
        if (thuoc.getGiaThuoc().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Giá thuốc nên lớn hơn 0.");
        }
        return thuocRepository.save(thuoc);
    }

    public List<Thuoc> timkiemthuoc(String keyword) {
        System.out.println("🔍 Tìm kiếm thuốc với từ khóa: " + keyword);

        if (keyword == null || keyword.trim().isEmpty()) {
            return thuocRepository.findAll();
        }

        return thuocRepository.findByMaThuocContainingOrTenThuocContaining(keyword, keyword);
    }

    public Optional<Thuoc> timtheomathuoc(String maThuoc) {
        return thuocRepository.findById(maThuoc);
    }

    public Thuoc capnhatthuoc(String maThuoc, Thuoc thuocCapNhat) {
        System.out.println("🔍 Đang cập nhật thuốc với mã: " + maThuoc);
        System.out.println("📌 Dữ liệu nhận được: " + thuocCapNhat);

        return thuocRepository.findById(maThuoc).map(thuoc -> {
            System.out.println("✅ Tìm thấy thuốc: " + thuoc);

            thuoc.setTenThuoc(thuocCapNhat.getTenThuoc());
            thuoc.setMoTa(thuocCapNhat.getMoTa());
            thuoc.setDonVi(thuocCapNhat.getDonVi());
            thuoc.setGiaThuoc(thuocCapNhat.getGiaThuoc());
            thuoc.setHanSuDung(thuocCapNhat.getHanSuDung());

            System.out.println("📝 Sau khi cập nhật: " + thuoc);
            return thuocRepository.save(thuoc);
        }).orElseThrow(() -> {
            System.out.println("❌ Không tìm thấy thuốc với mã: " + maThuoc);
            return new RuntimeException("Không tìm thấy thuốc với mã: " + maThuoc);
        });
    }

    public void xoathuoc(String maThuoc) {
        System.out.println("🛠 Xóa thuốc: " + maThuoc); // Debug log

        if (thuocRepository.existsById(maThuoc)) {
            thuocRepository.deleteById(maThuoc);
            System.out.println("✅ Xóa thành công: " + maThuoc);
        } else {
            System.out.println("❌ Không tìm thấy thuốc với mã: " + maThuoc);
            throw new RuntimeException("Không tìm thấy thuốc với mã: " + maThuoc);
        }
    }

}
