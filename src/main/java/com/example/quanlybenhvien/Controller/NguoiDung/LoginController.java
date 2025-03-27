package com.example.quanlybenhvien.Controller.NguoiDung;

import com.example.quanlybenhvien.Dao.BenhNhanDao;
import com.example.quanlybenhvien.Entity.BenhNhan;
import com.example.quanlybenhvien.Service.BenhNhanService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    private BenhNhanDao benhNhanDao;

    @Autowired
    private BenhNhanService benhNhanService;

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        BenhNhan benhNhan = (BenhNhan) session.getAttribute("loggedInUser");
        model.addAttribute("user", benhNhan);
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("matKhau") String matKhau,
                        HttpSession session,
                        HttpServletRequest request,
                        Model model) {
        // Tìm user theo email
        BenhNhan user = benhNhanDao.findByEmail(email).orElse(null);
    
        // Kiểm tra email tồn tại hay không
        if (user == null) {
            model.addAttribute("error", "Email không tồn tại.");
            return "login";
        }
    
        // Kiểm tra mật khẩu với BCrypt
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(matKhau, user.getMatKhau())) {
            model.addAttribute("error", "Mật khẩu không đúng.");
            return "login";
        }
    
        // ✅ Đăng nhập thành công:
        // Lưu user vào session (thống nhất sử dụng loggedInUser)
        session.setAttribute("loggedInUser", user);
        model.addAttribute("user", user);
    
        // Setup Authentication thủ công cho Spring Security
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("USER"));
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                user.getEmail(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
    
        // Ghi security context vào HttpSession để Spring Security nhận diện
        request.getSession().setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );
    
        // Debug log
        System.out.println("Đăng nhập thành công cho user: " + user.getEmail());
    
        // Redirect về trang chủ
        return "redirect:/index";
    }
    

    @GetMapping("/loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal OAuth2User principal, Model model, HttpSession session) {
        // Lấy thông tin từ OAuth2User
        String email = principal.getAttribute("email");
        String name = principal.getAttribute("name");
        String picture = null;

        // Kiểm tra và xử lý trường "picture"
        Object pictureAttribute = principal.getAttribute("picture");
        if (pictureAttribute instanceof String) {
            // Google trả về URL dạng chuỗi
            picture = (String) pictureAttribute;
        } else if (pictureAttribute instanceof LinkedHashMap) {
            // Facebook trả về đối tượng chứa URL
            LinkedHashMap<String, Object> pictureMap = (LinkedHashMap<String, Object>) pictureAttribute;
            LinkedHashMap<String, String> data = (LinkedHashMap<String, String>) pictureMap.get("data");
            picture = data.get("url");
        }

        // Kiểm tra người dùng trong cơ sở dữ liệu
        BenhNhan user = benhNhanDao.findByEmail(email).orElse(null);

        if (user == null) {
            // Nếu chưa tồn tại, tạo mới người dùng
            user = new BenhNhan();
            user.setEmail(email);
            user.setHoTen(name);
            user.setHinh(picture); // Lưu URL hình ảnh
            benhNhanDao.save(user);
        }
        // Lưu thông tin người dùng vào session
        session.setAttribute("loggedInUser", user);
        // Thêm thông tin người dùng vào model để hiển thị
        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }

    @GetMapping("/dangky")
    public String dangKyForm(Model model) {
        model.addAttribute("benhNhan", new BenhNhan());
        return "dangky"; // Tên file html là dangky.html hoặc dangky.jsp tùy project
    }

    @PostMapping("/dangky")
    public String dangKySubmit(@ModelAttribute("benhNhan") BenhNhan benhNhan, Model model) {
        // Kiểm tra trùng email
        if (benhNhanDao.findByEmail(benhNhan.getEmail()).isPresent()) {
            model.addAttribute("error", "Email đã tồn tại!");
            return "dangky";
        }

        // Kiểm tra nhập lại mật khẩu
        if (!benhNhan.getMatKhau().equals(benhNhan.getNhapLaiMatKhau())) {
            model.addAttribute("error", "Mật khẩu nhập lại không khớp!");
            return "dangky";
        }

        // Mã hóa mật khẩu
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        benhNhan.setMatKhau(encoder.encode(benhNhan.getMatKhau()));

        // Lưu vào DB
        benhNhanDao.save(benhNhan);

        return "redirect:/login?registerSuccess=true";
    }
    @ModelAttribute("user")
    public BenhNhan getCurrentUser(HttpSession session) {
        return (BenhNhan) session.getAttribute("user");
    }
}
