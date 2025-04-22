package com.example.quanlybenhvien.Controller.BacSi;

import com.example.quanlybenhvien.Entity.ChiTietDichVu;
import com.example.quanlybenhvien.Entity.LichKham;
import com.example.quanlybenhvien.Entity.LichKhamDichVuDTO;
import com.example.quanlybenhvien.Service.BacSiService;
import com.example.quanlybenhvien.Service.ChiTietDichVuService;
import com.example.quanlybenhvien.Service.DichVuService;
import com.example.quanlybenhvien.Service.LichKhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/bacsi/trangchu/dichvu")
public class ChiTietDichVuController {

    @Autowired
    private ChiTietDichVuService chiTietDichVuService;

    @Autowired
    private LichKhamService lichKhamService;

    @Autowired
    private BacSiService bacSiService;
    
    @Autowired
    private DichVuService dichVuService;

    // Hiển thị form thêm nhiều dịch vụ vào lịch khám
    @GetMapping("/them")
    public String hienThiFormThemDichVu(@RequestParam("maLichKham") int maLichKham, Model model, RedirectAttributes redirectAttributes) {
        if (bacSiService.getBacSiDangNhap() == null) {
            return "redirect:/bacsi/login";
        }

        LichKham lichKham = lichKhamService.getLichKhamById(maLichKham);
        if (lichKham == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy lịch khám với mã: " + maLichKham);
            return "redirect:/bacsi/trangchu/lichkham/cho-xac-nhan";
        }

        // Đảm bảo DTO có đối tượng lichKham hợp lệ
        LichKhamDichVuDTO dto = new LichKhamDichVuDTO();
        dto.setLichKham(lichKham);

        // Kiểm tra tránh null
        if (dto.getLichKham() == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lịch khám không tồn tại!");
            return "redirect:/bacsi/trangchu/lichkham/cho-xac-nhan";
        }

        // Thêm một chi tiết dịch vụ mặc định
        dto.getChiTietDichVus().add(new ChiTietDichVu());

        model.addAttribute("dichVus", dichVuService.getAllDichVu());
        model.addAttribute("lichKhamDichVuDTO", dto);
        model.addAttribute("maLichKham", lichKham.getMaLichKham());  // Truyền riêng mã lịch khám

        return "bacsi/them-chitietdichvu";
    }

    // Xử lý thêm danh sách dịch vụ vào lịch khám
    @PostMapping("/them")
    public String themNhieuDichVu(@ModelAttribute("lichKhamDichVuDTO") LichKhamDichVuDTO dto,
                                  RedirectAttributes redirectAttributes) {
        if (bacSiService.getBacSiDangNhap() == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bác sĩ chưa đăng nhập!");
            return "redirect:/bacsi/login";
        }
        try {
            // Kiểm tra và thêm các dịch vụ vào lịch khám
            for (ChiTietDichVu chiTiet : dto.getChiTietDichVus()) {
                // Kiểm tra dữ liệu: số lượng phải > 0 và mã dịch vụ không được null
                if (chiTiet.getSoLuong() != null && chiTiet.getSoLuong() > 0 &&
                    chiTiet.getDichVu() != null && chiTiet.getDichVu().getMaDichVu() != null) {
                    
                    // Gán lịch khám cho từng chi tiết dịch vụ
                    chiTiet.setLichKham(dto.getLichKham());

                    // Kiểm tra nếu lichKham chưa được gán
                    if (chiTiet.getLichKham() == null) {
                        throw new IllegalArgumentException("Lịch khám không hợp lệ!");
                    }

                    // Lưu vào cơ sở dữ liệu
                    chiTietDichVuService.themDichVuVaoLichKham(chiTiet);
                }
            }

            redirectAttributes.addFlashAttribute("successMessage", "Thêm dịch vụ thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi thêm dịch vụ: " + e.getMessage());
        }
        return "redirect:/bacsi/trangchu/dichvu/danh-sach?maLichKham=" + dto.getLichKham().getMaLichKham();

    }

    // Hiển thị danh sách dịch vụ đã thêm cho lịch khám
    @GetMapping("/danh-sach")
    public String hienThiDanhSachDichVu(@RequestParam("maLichKham") int maLichKham, Model model, RedirectAttributes redirectAttributes) {
        // Kiểm tra đăng nhập của bác sĩ
        if (bacSiService.getBacSiDangNhap() == null) {
            return "redirect:/bacsi/login";
        }

        LichKham lichKham = lichKhamService.getLichKhamById(maLichKham);
        if (lichKham == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy lịch khám với mã: " + maLichKham);
            return "redirect:/bacsi/trangchu/lichkham/cho-xac-nhan";
        }

        // Lịch khám chứa danh sách các dịch vụ đã thêm (thông qua lichKham.getChiTietDichVus())
        model.addAttribute("lichKham", lichKham);
        return "bacsi/danh-sach-dichvu";  // View hiển thị danh sách dịch vụ
    }
}
