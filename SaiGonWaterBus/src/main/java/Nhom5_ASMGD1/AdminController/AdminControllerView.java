package Nhom5_ASMGD1.AdminController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import Nhom5_ASMGD1.DAO.ChuyenDAO;
import Nhom5_ASMGD1.DAO.HoaDonDAO;
import Nhom5_ASMGD1.DAO.TaiXeDAO;
import Nhom5_ASMGD1.DAO.TauDAO;
import Nhom5_ASMGD1.DAO.TuyenDAO;
import Nhom5_ASMGD1.DAO.VeDAO;
import Nhom5_ASMGD1.Model.Chuyen;
import Nhom5_ASMGD1.Model.HoaDon;
import Nhom5_ASMGD1.Model.TaiXe;
import Nhom5_ASMGD1.Model.Tau;
import Nhom5_ASMGD1.Model.Tuyen;
import Nhom5_ASMGD1.Model.Ve;

@Controller
public class AdminControllerView {
	@Autowired
	TaiXeDAO taiXeDao;
	
	@Autowired
	ChuyenDAO chuyenDao;
	
	@Autowired
	TuyenDAO tuyenDao;
	
	@Autowired
	HoaDonDAO HoaDonDao;
	
	@Autowired
	VeDAO VeDao;
	@Autowired
	private TauDAO daoTau;
	@Autowired
	private TuyenDAO daoTuyen;
	@GetMapping("/indexAdmin")
	public String index(Model model) {
		model.addAttribute("viewad", "/WEB-INF/views/admin/banner.jsp");
		return "admin/home";
	}



	@GetMapping("/tool/{toolName}")
	public String yourHandlerMethod(@PathVariable String toolName, Model model, @RequestParam(required = false) String keyword ,
            @RequestParam(name = "p", required = false, defaultValue = "0") Integer p,
            @ModelAttribute("taiXe") TaiXe taiXe,
            @ModelAttribute("chuyen") Chuyen chuyen,
            @ModelAttribute("Ve") Ve ve, 
            @ModelAttribute("hoaDon") HoaDon hoaDon) {
		if ("tools".equals(toolName)) {
			model.addAttribute("viewad", "/WEB-INF/views/admin/tools.jsp");
		} else if ("add".equals(toolName)) {
			model.addAttribute("viewad", "/WEB-INF/views/admin/addticket.jsp");
		}
		else if ("update".equals(toolName)) {
			model.addAttribute("viewad", "/WEB-INF/views/admin/update.jsp");
		} else if ("routeship".equals(toolName)) {
		 	if( keyword==null || keyword.isEmpty()) {
				model.addAttribute("listTuyen", daoTuyen.findAll());
		 	}else {
				model.addAttribute("listTuyen", daoTuyen.searchByTenTuyen(keyword));
		 	}
			model.addAttribute("tuyen", new Tuyen());
			model.addAttribute("listTau", daoTau.findByTauStatus());
			model.addAttribute("viewad", "/WEB-INF/views/admin/ShipRoutes/routeship.jsp");
		}
		 else if ("toolship".equals(toolName)) {
			 	if( keyword==null || keyword.isEmpty()) {
					model.addAttribute("listTau", daoTau.findByTauStatus());
			 	}else {
					model.addAttribute("listTau", daoTau.searchByKeyword(keyword));
			 	}
				model.addAttribute("tau", new Tau());
				model.addAttribute("viewad", "/WEB-INF/views/admin/Ship/toolship.jsp");
			}
	 else if ("driver".equals(toolName)) {
		System.out.println("keyword: " + keyword);
		List<TaiXe> listTaiXe;
		if (keyword == null || keyword.isEmpty()) {
			listTaiXe = taiXeDao.findAllByStatusTrue();
		} else {
			listTaiXe = taiXeDao.findByHoTenOrSoDT(keyword);
		}
		model.addAttribute("listTaiXe", listTaiXe);
		model.addAttribute("viewad", "/WEB-INF/views/admin/driver/driver.jsp");
	} else if ("trip".equals(toolName)) {
		model.addAttribute("listChuyen", chuyenDao.findAll());
		model.addAttribute("listTuyen", tuyenDao.findAll());
		model.addAttribute("listTaiXe", taiXeDao.getAllTaiXe());
		model.addAttribute("viewad", "/WEB-INF/views/admin/trip/trip.jsp");
	} else if ("ve".equals(toolName)) {
		System.out.println("keyword: " + keyword);
		Pageable pageable = PageRequest.of(p, 15);
		Page<Ve> listVe;
		if (keyword == null || keyword.isEmpty()) {
			listVe = VeDao.findAllByStatusFalse(pageable);
		} else {
			listVe = VeDao.findBytenGheOrid(keyword, pageable);
		}
		model.addAttribute("listVe", listVe);
		model.addAttribute("viewad", "/WEB-INF/views/admin/diem/ve.jsp");
	}
	
	else if ("hoadon".equals(toolName)) {
		List<HoaDon> listHoaDon;
		if (keyword == null || keyword.isEmpty()) {
			listHoaDon = HoaDonDao.findAllByStatusTrue();
		} else {
			listHoaDon = HoaDonDao.findBytenGheOridAccount(keyword);
		}
		model.addAttribute("listHoaDon", listHoaDon);
		model.addAttribute("viewad", "/WEB-INF/views/admin/hoadonadmin.jsp");
	}

			else {
				return "admin/home";
			}
		return "admin/home";
	}

}