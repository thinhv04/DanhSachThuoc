package com.example.quanlybenhvien.Controller.QuanLy;

    import java.util.List;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.ModelAttribute;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestParam;
    import org.springframework.web.servlet.mvc.support.RedirectAttributes;

    import com.example.quanlybenhvien.Entity.BenhNhan;
    import com.example.quanlybenhvien.Entity.Vaitro;
    import com.example.quanlybenhvien.Service.BenhNhanService;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestBody;

    @Controller
    @RequestMapping("/quanly/trangchu")
    public class BenhNhanController {
        @Autowired
        private BenhNhanService benhNhanService;

        @GetMapping("/benhnhan")
        public String listBenhNhan(Model model) {
            List<BenhNhan> list = benhNhanService.getAllBenhNhans();
            model.addAttribute("benhnhans", list);
            model.addAttribute("benhnhan", new BenhNhan());
            return "admin/benhnhan"; 
        }
        

        @PostMapping("/benhnhan/save")
    public String saveBenhNhan(@ModelAttribute BenhNhan benhNhan, RedirectAttributes redirectAttributes) {
        try {
            benhNhanService.saveBenhNhan(benhNhan);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm bệnh nhân thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi thêm bệnh nhân!");
        }
        return "redirect:/quanly/trangchu/benhnhan";
    }

        @GetMapping("/benhnhan/delete/{id}")
        public String deleteBenhNhan(@PathVariable Integer id,RedirectAttributes redirectAttributes) {
            benhNhanService.deleteBenhNhan(id);
            redirectAttributes.addFlashAttribute("message", "Xóa bệnh nhân thành công!");
            return "redirect:/quanly/trangchu/benhnhan";
        }

        @GetMapping("/benhnhan/search")
    public String searchBenhNhan(@RequestParam("keyword") String keyword, Model model) {
        List<BenhNhan> searchResults = benhNhanService.searchByKeyword(keyword);
        model.addAttribute("benhnhans", searchResults);
        model.addAttribute("benhnhan", new BenhNhan()); 
        return "admin/benhnhan"; 
    }

    @GetMapping("/benhnhan/edit/{id}")
    public String editBenhNhan(@PathVariable Integer id, Model model) {
        BenhNhan benhNhan = benhNhanService.findById(id);
        model.addAttribute("benhnhan", benhNhan != null ? benhNhan : new BenhNhan());
        model.addAttribute("benhnhans", benhNhanService.getAllBenhNhans());
        return "admin/benhnhan";
    }
    
        }
        
    
    
        // @PostMapping("/update/{id}")
        // public String updateBenhNhan(@PathVariable Integer id,
        // @ModelAttribute("benhnhan") BenhNhan benhNhan) {
        // benhNhan.setMaBN(id);
        // benhNhanService.saveBenhNhan(benhNhan);
        // return "redirect:/benhnhan";
        // }

