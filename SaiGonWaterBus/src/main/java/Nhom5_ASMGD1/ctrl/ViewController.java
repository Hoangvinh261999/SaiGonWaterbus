package Nhom5_ASMGD1.ctrl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import Nhom5_ASMGD1.DAO.ChuyenDAO;
import Nhom5_ASMGD1.DAO.UserDao;
import Nhom5_ASMGD1.DAO.VeDAO;
import Nhom5_ASMGD1.Model.Chuyen;
import Nhom5_ASMGD1.Model.Ve;






@Controller
public class ViewController {
	@Autowired
	VeDAO veDao;
	@Autowired
	UserDao userDao;
	@Autowired
	ChuyenDAO chuyenDao;
	
	@GetMapping ("/saigonwaterbus")
	public String getIndex(Model model) {
		model.addAttribute("view", "/WEB-INF/views/user/trangchu.jsp");
		return "user/index";
	}
	
	@GetMapping("/saigonwaterbus/{path}")
    public String getView(@PathVariable("path") String pathName, Model model,
    		@ModelAttribute("ve") Ve ve, 
    		@RequestParam(value = "benDi", required = false) String benDi,
    		@RequestParam(value = "benDen", required = false) String benDen,
    		@RequestParam(value = "benDung", required = false) String benDung,
    		@RequestParam(value = "ngayKhoiHanh", required = false) String ngayKhoiHanh,
    		@RequestParam(value = "editidChuyen", required = false) String idChuyen
    		) {
		if ("ga-tau-thuy-bach-dang".equals(pathName)) {
			model.addAttribute("view", "/WEB-INF/views/user/ga.jsp");
		} else if ("ga-tau-thuy-binh-an".equals(pathName)) {
			model.addAttribute("view", "/WEB-INF/views/user/ga.jsp");
		}
		else if("profile".equals(pathName)){
			model.addAttribute("view", "/WEB-INF/views/user/profile.jsp");
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (authentication != null && authentication.isAuthenticated()) {
				model.addAttribute("user",userDao.findByEmail( authentication.getName()));
			
			   
			}
		}
		else if ("ga-tau-thuy-thanh-da".equals(pathName)) {
			model.addAttribute("view", "/WEB-INF/views/user/ga.jsp");
		} else if ("ga-tau-thuy-hiep-binh-chanh".equals(pathName)) {
			model.addAttribute("view", "/WEB-INF/views/user/ga.jsp");
		}

		else if ("ga-tau-thuy-linh-dong".equals(pathName)) {
			model.addAttribute("view", "/WEB-INF/views/user/ga.jsp");
		}
		else if ("lich-khoi-hanh".equals(pathName)) {
			model.addAttribute("view", "/WEB-INF/views/user/lich.jsp");
		}
		else if ("tim-ve".equals(pathName)) {
			LocalDate ngayKH;
			List<Chuyen> listChuyen;
			if (ngayKhoiHanh != null && !ngayKhoiHanh.isEmpty()) {
			    ngayKH = LocalDate.parse(ngayKhoiHanh);
			    System.out.println("ngày kh: " + ngayKH);
			    listChuyen = chuyenDao.findChuyenByDate(ngayKH,benDi,benDen);
			    System.out.println(listChuyen.size() + "size list");
			} else {
				listChuyen = null;
				System.out.println("ngày kh: " + ngayKhoiHanh);
			}
			model.addAttribute("listChuyen", listChuyen);
			
			model.addAttribute("view", "/WEB-INF/views/user/datve.jsp");
		}
		else if ("kiem-tra-ve".equals(pathName)) {
			model.addAttribute("view", "/WEB-INF/views/user/kiemtrave.jsp");
		}
		else if ("lien-he".equals(pathName)) {
			model.addAttribute("view", "/WEB-INF/views/user/lienhe.jsp");
		}
		else {
			return "user/index";
		}
		return "user/index";
	}
}
	

