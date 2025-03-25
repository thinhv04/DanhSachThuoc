package com.example.quanlybenhvien.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quanlybenhvien.Entity.Vaitro;




@Repository
public interface VaiTroDao extends JpaRepository<Vaitro,String>{
    Optional<Vaitro> findByMaVaiTro(String maVaiTro);
    List<Vaitro> findByTenVaiTroContainingIgnoreCase(String keyword);
}
