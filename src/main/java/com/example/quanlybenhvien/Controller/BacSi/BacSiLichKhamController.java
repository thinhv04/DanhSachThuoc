package com.example.quanlybenhvien.Controller.BacSi;

import com.example.quanlybenhvien.Entity.BacSi;
import com.example.quanlybenhvien.Entity.LichKham;
import com.example.quanlybenhvien.Service.BacSiService;
import com.example.quanlybenhvien.Service.LichKhamService;
import com.example.quanlybenhvien.Service.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/bacsi/trangchu/lichkham")
public class BacSiLichKhamController {

    @Autowired
    private LichKhamService lichKhamService;

    @Autowired
    private BacSiService bacSiService;

     @Autowired
    private MailService mailService;

    // Hiển thị trang "Lịch khám đã xác nhận" với 2 bảng:
    // 1. Lịch khám đang chờ ("Đã xác nhận bởi bác sĩ")
    // 2. Lịch khám có bệnh nhân đã đến
    @GetMapping("/da-xac-nhan")
    public String hienThiLichKhamDaXacNhan(Model model, RedirectAttributes redirectAttributes) {
        BacSi bacSiDangNhap = bacSiService.getBacSiDangNhap();
        if (bacSiDangNhap == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn chưa đăng nhập!");
            return "redirect:/bacsi/login";
        }

        List<LichKham> lichKhamsDaXacNhan = lichKhamService.getLichKhamDaXacNhanTheoBacSi(bacSiDangNhap);
        List<LichKham> benhNhanDaDen = lichKhamService.getLichKhamBenhNhanDaDenTheoBacSi(bacSiDangNhap);
        List<LichKham> hoanThanhLichKham = lichKhamService.getHoanThanhLichKham(bacSiDangNhap);
        model.addAttribute("lichKhams", lichKhamsDaXacNhan);
        model.addAttribute("benhNhanDen", benhNhanDaDen);
        model.addAttribute("hoanThanhLichKham", hoanThanhLichKham);
        return "bacsi/lichkham-da-xacnhan"; // Template nằm trong: src/main/resources/templates/bacsi/lichkham-da-xacnhan.html
    }

    @PostMapping("/xac-nhan")
    public String xacNhanLichKham(@RequestParam int maLichKham,
                                  @RequestParam String trangThai,
                                  @RequestParam String ghiChu,
                                  RedirectAttributes redirectAttributes) {
        BacSi bacSiDangNhap = bacSiService.getBacSiDangNhap();
        if (bacSiDangNhap == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy thông tin bác sĩ đăng nhập!");
            return "redirect:/bacsi/login";
        }

        try {
            LichKham lichKham = lichKhamService.xacNhanLichKhamTheoBacSi(maLichKham, trangThai, ghiChu, bacSiDangNhap);

            // Gửi email cho bệnh nhân
            String email = lichKham.getBenhNhan().getEmail();
            String hoTen = lichKham.getBenhNhan().getHoTen();
            String thoiGian = lichKham.getGioKham().toString();

            String subject = "Xác nhận lịch khám của bạn tại Phòng khám đa khoa Nhóm 2";
            String content = String.format("""
                <p>Xin chào <strong>%s</strong>,</p>
                
                <p>Chúng tôi xin xác nhận lịch khám của bạn tại <strong>Phòng khám đa khoa Nhóm 2</strong> vào thời gian:</p>
                <p><strong>%s</strong></p>
                
                <p>Trạng thái hiện tại của lịch khám: <strong>%s</strong>.</p>
                
                <p><strong>Ghi chú từ bác sĩ:</strong><br>%s</p>
                
                <hr>
                <p>Nếu bạn có bất kỳ thắc mắc nào, vui lòng liên hệ với chúng tôi qua số điện thoại hoặc email hỗ trợ.</p>
                <p>Trân trọng,<br><strong>Phòng khám đa khoa Nhóm 2</strong><br>Email: vonguyenduytan12cb9@gmail.com<br>Điện thoại: 0377307488</p>
            """, hoTen, thoiGian, trangThai, ghiChu);
            

            mailService.sendMail(email, subject, content);
            System.out.println("Đang gửi mail tới: " + email);

            redirectAttributes.addFlashAttribute("successMessage", "Xác nhận thành công và đã gửi email cho bệnh nhân.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }

        return "redirect:/bacsi/trangchu/lichkham/cho-xac-nhan";
    }
    // @PostMapping("/xac-nhan")
    // public String xacNhanLichKham(@RequestParam int maLichKham,
    //                               @RequestParam String trangThai,
    //                               @RequestParam String ghiChu,
    //                               RedirectAttributes redirectAttributes) {
    //     BacSi bacSiDangNhap = bacSiService.getBacSiDangNhap();
    //     if (bacSiDangNhap == null) {
    //         redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy thông tin bác sĩ đăng nhập!");
    //         return "redirect:/bacsi/login";
    //     }
    //     try {
    //         // Ví dụ: nếu bác sĩ xác nhận, trạng thái sẽ là "Đã xác nhận bởi bác sĩ"
    //         // nếu hủy, trạng thái là "Hủy bởi bác sĩ"
    //         lichKhamService.xacNhanLichKhamTheoBacSi(maLichKham, trangThai, ghiChu, bacSiDangNhap);
    //         redirectAttributes.addFlashAttribute("successMessage", "Xác nhận lịch khám thành công!");
    //     } catch (Exception e) {
    //         redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
    //     }
    //     return "redirect:/bacsi/trangchu/lichkham/cho-xac-nhan";
    // }
    // // Đánh dấu lịch khám khi bệnh nhân đã đến
    // @GetMapping("/benhnhan-den")
    // public String danhDauBenhNhanDaDen(@RequestParam("maLichKham") Integer maLichKham,
    //                                     RedirectAttributes redirectAttributes) {
    //     boolean success = lichKhamService.danhDauBenhNhanDaDen(maLichKham);
    //     if (success) {
    //         redirectAttributes.addFlashAttribute("successMessage", "✅ Đã đánh dấu bệnh nhân đã đến!");
    //     } else {
    //         redirectAttributes.addFlashAttribute("errorMessage", "❌ Không thể đánh dấu bệnh nhân đã đến!");
    //     }
    //     return "redirect:/bacsi/trangchu/lichkham/da-xac-nhan";
    // }

    // // Huỷ lịch khám
    // @GetMapping("/huy-lich")
    // public String huyLichKham(@RequestParam("maLichKham") Integer maLichKham,
    //                           RedirectAttributes redirectAttributes) {
    //     boolean success = lichKhamService.huyLichKham(maLichKham);
    //     if (success) {
    //         redirectAttributes.addFlashAttribute("successMessage", "✅ Lịch khám đã được huỷ!");
    //     } else {
    //         redirectAttributes.addFlashAttribute("errorMessage", "❌ Không thể huỷ lịch khám!");
    //     }
    //     return "redirect:/bacsi/trangchu/lichkham/da-xac-nhan";
    // }

    // // Điều hướng đến trang thêm dịch vụ cho lịch khám (nếu cần)
    // @GetMapping("/dichvu/them")
    // public String themDichVu(@RequestParam("maLichKham") Integer maLichKham, Model model, RedirectAttributes redirectAttributes) {
    //     LichKham lichKham = lichKhamService.getLichKhamById(maLichKham);
    //     if (lichKham == null) {
    //         redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy lịch khám!");
    //         return "redirect:/bacsi/trangchu/lichkham/da-xac-nhan";
    //     }
    //     model.addAttribute("lichKham", lichKham);
    //     return "bacsi/them-dichvu"; // Template thêm dịch vụ, ví dụ: src/main/resources/templates/bacsi/them-dichvu.html
    // }
    // Endpoint đánh dấu bệnh nhân đã đến
    @GetMapping("/benhnhan-den")
    public String danhDauBenhNhanDaDen(@RequestParam("maLichKham") Integer maLichKham,
                                        RedirectAttributes redirectAttributes) {
        boolean success = lichKhamService.danhDauBenhNhanDaDen(maLichKham);
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "✅ Đã đánh dấu bệnh nhân đã đến!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Không thể đánh dấu bệnh nhân đã đến!");
        }
        return "redirect:/bacsi/trangchu/lichkham/da-xac-nhan";
    }

    @GetMapping("/hoanthanh")
    public String hoanThanhLichKham(@RequestParam("maLichKham") Integer maLichKham,
                                        RedirectAttributes redirectAttributes) {
        boolean success = lichKhamService.hoanThanhLichKham(maLichKham);
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "✅ Đã đánh dấu hoàn thành lịch khám!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Không thể đánh dấu không hoàn thành !");
        }
        return "redirect:/bacsi/trangchu/lichkham/da-xac-nhan";
    }

    // Endpoint huỷ lịch khám
    @GetMapping("/huy-lich")
    public String huyLichKham(@RequestParam("maLichKham") Integer maLichKham,
                              RedirectAttributes redirectAttributes) {
        boolean success = lichKhamService.huyLichKham(maLichKham);
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "✅ Lịch khám đã được huỷ!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Không thể huỷ lịch khám!");
        }
        return "redirect:/bacsi/trangchu/lichkham/da-xac-nhan";
    }

    // Endpoint điều hướng đến trang thêm dịch vụ cho lịch khám
    @GetMapping("/dichvu/them")
    public String themDichVu(@RequestParam("maLichKham") Integer maLichKham,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        LichKham lichKham = lichKhamService.getLichKhamById(maLichKham);
        if (lichKham == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy lịch khám!");
            return "redirect:/bacsi/trangchu/lichkham/da-xac-nhan";
        }
        model.addAttribute("lichKham", lichKham);
        return "bacsi/them-dichvu"; // Template thêm dịch vụ: src/main/resources/templates/bacsi/them-dichvu.html
    }

    // (Tùy chọn) Endpoint hiển thị trang lịch khám chờ xác nhận
    @GetMapping("/cho-xac-nhan")
    public String hienThiLichKhamChoXacNhan(Model model, RedirectAttributes redirectAttributes) {
        BacSi bacSiDangNhap = bacSiService.getBacSiDangNhap();
        if (bacSiDangNhap == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn chưa đăng nhập!");
            return "redirect:/bacsi/login";
        }
        // Giả sử lichKhamService có phương thức lấy lịch khám chờ xác nhận theo bác sĩ
        model.addAttribute("lichKhams", lichKhamService.getLichKhamChoXacNhanTheoBacSi(bacSiDangNhap));
        return "bacsi/lichkham-xacnhan"; // Template hiển thị lịch khám chờ xác nhận
    }
}
