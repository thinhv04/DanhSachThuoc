package com.example.quanlybenhvien;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class QuanlybenhvienApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuanlybenhvienApplication.class, args);
        //  BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // String encodedPassword = encoder.encode("123"); // Đổi "123" thành mật khẩu bạn muốn
        // System.out.println("Mật khẩu đã mã hóa: " + encodedPassword);
    }

}
