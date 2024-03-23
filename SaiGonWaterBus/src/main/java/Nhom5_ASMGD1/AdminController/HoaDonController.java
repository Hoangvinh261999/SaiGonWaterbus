package Nhom5_ASMGD1.AdminController;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import Nhom5_ASMGD1.DAO.HoaDonDAO;
import Nhom5_ASMGD1.Model.HoaDon;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Controller
public class HoaDonController {
	@Autowired
	HoaDonDAO hoaDonDao;

	
	@PostMapping("/add-HoaDon")
	public String addDriver(HoaDon hoaDon, HttpServletRequest request, HttpServletResponse response) throws IOException {
		//TODO: process POST request
		String annouce = "Create HoaDon success!";
		hoaDonDao.save(hoaDon); 
		return "redirect:/tool/HoaDon?annouce="+annouce;
	}
//	
//	@RequestMapping("/tool/HoaDon/{id}")
//	public String requestMethodName(Model model, @PathVariable("id") Long id) {
//		HoaDon hoaDon = hoaDonDao.findById(id).get();
//		model.addAttribute("hoaDon", hoaDon);
//		return "forward:/tool/HoaDon/id=" + id;
//	}
	
	@PostMapping("/update-HoaDon")
	public String updateDriver(Model model, @ModelAttribute HoaDon hoaDon) {
		//TODO: process POST request
		System.out.println("hoa don id: " + hoaDon.getId());
		if (hoaDonDao.existsById(hoaDon.getId())) {
			String annouce = "Update HoaDon success!";
			hoaDonDao.save(hoaDon); 
			return "redirect:/tool/HoaDon?annouce="+annouce;
		} else {
			throw new RuntimeException("Không tồn tại hóa đơn có id: " + hoaDon.getId());
		}
	}
	
	@PostMapping("/delete-HoaDon")
	public String deleteDriver(Model model, @ModelAttribute HoaDon hoaDon) {
		//TODO: process POST request
		System.out.println("hoa don id delete: " + hoaDon.getId());
		if (hoaDonDao.existsById(hoaDon.getId())) {
			String annouce = "Delete HoaDon success!";
			hoaDon.setStatus(false);
			hoaDonDao.save(hoaDon); 
			return "redirect:/tool/HoaDon?annouce="+annouce;
		} else {
			throw new RuntimeException("Không tồn tại hóa đơn có id: " + hoaDon.getId());
		}
	}
	
}
