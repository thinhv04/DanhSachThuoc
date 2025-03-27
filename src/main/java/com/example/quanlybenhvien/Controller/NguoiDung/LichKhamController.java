package com.example.quanlybenhvien.Controller.NguoiDung;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.quanlybenhvien.Entity.BacSi;
import com.example.quanlybenhvien.Entity.BenhNhan;
import com.example.quanlybenhvien.Entity.ChuyenKhoa;
import com.example.quanlybenhvien.Entity.LichKham;
import com.example.quanlybenhvien.Service.BacSiService;
import com.example.quanlybenhvien.Service.BenhNhanService;
import com.example.quanlybenhvien.Service.ChuyenKhoaService;
import com.example.quanlybenhvien.Service.LichKhamService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/nguoidung/lichkham")
public class LichKhamController {

    @Autowired
    private LichKhamService lichKhamService;

    @Autowired
    private ChuyenKhoaService chuyenKhoaService;

    @Autowired
    private BacSiService bacSiService;

    @Autowired
    BenhNhanService benhNhanService;

    @GetMapping
    public String lichkham(@RequestParam(value = "maChuyenKhoa", required = false) String maChuyenKhoa,
            HttpSession session, Model model) {
        List<ChuyenKhoa> listChuyenKhoa = chuyenKhoaService.getAllChuyenKhoa();
        List<BacSi> listBacSi;

        // Lấy user từ SecurityContext hoặc Session
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BenhNhan benhNhan = null;

        if (auth.getPrincipal() instanceof BenhNhan) {
            benhNhan = (BenhNhan) auth.getPrincipal();
        } else if (auth.getPrincipal() instanceof DefaultOAuth2User oauth2User) {
            String email = oauth2User.getAttribute("email");
            benhNhan = benhNhanService.findByEmail(email);
        }
        session.setAttribute("loggedInUser", benhNhan);
        model.addAttribute("user", benhNhan);

        if (maChuyenKhoa != null && !maChuyenKhoa.isEmpty()) {
            listBacSi = bacSiService.getBacSiByChuyenKhoa(maChuyenKhoa);
        } else {
            listBacSi = bacSiService.getAllBacSi();
        }

        model.addAttribute("dsChuyenKhoa", listChuyenKhoa);
        model.addAttribute("dsBacSi", listBacSi);
        model.addAttribute("selectedChuyenKhoa", maChuyenKhoa);
        model.addAttribute("lichKham", new LichKham());

        return "lichkhamND";
    }

    // Xử lý khi người dùng đặt lịch
    @PostMapping
    public String datLich(@ModelAttribute("lichKham") LichKham lichKham,
            @RequestParam("maChuyenKhoa") String maChuyenKhoa,
            RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return "redirect:/login";
        }

        Object principal = auth.getPrincipal();

        // Lấy chuyên khoa từ database trước khi set
        Optional<ChuyenKhoa> chuyenKhoa = chuyenKhoaService.findById(maChuyenKhoa);
        if (chuyenKhoa.isPresent()) {
            lichKham.setChuyenKhoa(chuyenKhoa.get()); // Gán chuyên khoa hợp lệ vào lịch khám
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Chuyên khoa không hợp lệ!");
            return "redirect:/nguoidung/lichkham";
        }

        // Kiểm tra nếu người dùng đăng nhập bằng tài khoản thông thường
        if (principal instanceof BenhNhan user) {
            lichKham.setBenhNhan(user);
            lichKham.setTrangThai("Chờ xác nhận");
            lichKhamService.save(lichKham);

            redirectAttributes.addFlashAttribute("successMessage", "Đặt lịch thành công! Chúng tôi sẽ sớm xác nhận.");
            return "redirect:/nguoidung/lichkham?success";
        }

        // Kiểm tra nếu người dùng đăng nhập bằng OAuth2 (Google, Facebook, ...)
        if (principal instanceof DefaultOAuth2User oauth2User) {
            String email = oauth2User.getAttribute("email");
            BenhNhan benhNhan = benhNhanService.findByEmail(email);

            if (benhNhan != null) {
                lichKham.setBenhNhan(benhNhan);
                lichKham.setTrangThai("Chờ xác nhận");
                lichKhamService.save(lichKham);

                redirectAttributes.addFlashAttribute("successMessage",
                        "Đặt lịch thành công! Chúng tôi sẽ sớm xác nhận.");
                return "redirect:/nguoidung/lichkham?success";
            }
        }

        return "redirect:/login";
    }

    @GetMapping("/lichsu")
    public String lichSuLichKham(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return "redirect:/login";
        }

        Object principal = auth.getPrincipal();
        BenhNhan benhNhan = null;

        if (principal instanceof BenhNhan) {
            benhNhan = (BenhNhan) principal;
        } else if (principal instanceof DefaultOAuth2User) {
            String email = ((DefaultOAuth2User) principal).getAttribute("email");
            benhNhan = benhNhanService.findByEmail(email);
        }

        if (benhNhan != null) {
            List<LichKham> lichSu = lichKhamService.findByBenhNhan(benhNhan);
            model.addAttribute("lichSu", lichSu);
            model.addAttribute("benhNhan", benhNhan); // <-- Gửi thêm thông tin bệnh nhân ra view
        }

        return "lichsudatlich";
    }

}
