package Nhom5_ASMGD1.ctrl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Nhom5_ASMGD1.DAO.ChuyenDAO;
import Nhom5_ASMGD1.DAO.HoaDonDAO;
import Nhom5_ASMGD1.DAO.UserDao;
import Nhom5_ASMGD1.DAO.VeDAO;
import Nhom5_ASMGD1.Model.HoaDon;
import Nhom5_ASMGD1.Model.Ve;
import Nhom5_ASMGD1.Service.VNPayService;
import Nhom5_ASMGD1.Ultil.SendMail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class VnPayCtrler {
    @Autowired
    private VNPayService vnPayService;
	@Autowired
    private SendMail emailService;
	@Autowired
	HttpSession sessionHoaDon;
	
	@Autowired
	HoaDonDAO hoaDonDAO;
	
	@Autowired
	VeDAO veDAO;
	
	@Autowired
	ChuyenDAO chuyenDAO;
	
	@Autowired
	UserDao userDao;
    @PostMapping("/submitOrder")
    public String submidOrder(@RequestParam("tongTien") Integer orderTotal,
  
                            @RequestParam("emailThanhToan") String emailThanhToan,
                            HttpServletRequest request){
    	sessionHoaDon.setAttribute("emailThanhToan", emailThanhToan);
    	String orderInfo="Thanh Toan Dat Ve SAIGONWATERBUS";
    	
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model){
        int paymentStatus =vnPayService.orderReturn(request);
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        int realPrice = Integer.parseInt(totalPrice)/100;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
			Date date = sdf.parse(paymentTime);
			SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String formattedDate = outputFormat.format(date);
	        model.addAttribute("paymentTime", formattedDate);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			
		}
        
        
        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", realPrice+" VND");
        System.out.println("tổng tiền là"+totalPrice);
        model.addAttribute("transactionId", transactionId);
        if(paymentStatus == 1 ) {
        	sessionHoaDon.setAttribute("thanhTien", realPrice);
	        buildMailHoaDon();
        	setStatusVe();
        	sessionHoaDon.invalidate();
        	 return "ordersuccess";
        }
        return  "orderfail";
    }
    public void buildMailHoaDon () {
    	StringBuilder emailContent = new StringBuilder();
    	emailContent.append("Tên chuyến: ").append(sessionHoaDon.getAttribute("tenChuyen")).append("\n");
    	emailContent.append("Bến đi: ").append(sessionHoaDon.getAttribute("benDi")).append("\n");
    	emailContent.append("Bến dừng: ").append(sessionHoaDon.getAttribute("benDung")).append("\n");
    	emailContent.append("Ngày khởi hành: ").append(sessionHoaDon.getAttribute("ngayKH")).append("\n");
    	emailContent.append("Giờ đi: ").append(sessionHoaDon.getAttribute("gioDi")).append("\n");
    	emailContent.append("Số lượng ghế: ").append(sessionHoaDon.getAttribute("soLuongGhe")).append("\n");
    	emailContent.append("Thành Tiền: ").append(sessionHoaDon.getAttribute("thanhTien")).append("\n");

    	emailContent.append("Ghế đã đặt: ").append(sessionHoaDon.getAttribute("tenGhe")).append("\n");
    	emailContent.append("\n\n\n\n");
    	emailContent.append("Cảm ơn"+ sessionHoaDon.getAttribute("emailThanhToan").toString()+" đã sử dụng dịch vụ, hãy giữ lại MAIL này để đối chiếu vé khi tới bến");
    	emailService.sendSimpleMessage(sessionHoaDon.getAttribute("emailThanhToan").toString(), "Hoá đơn thanh toán cho vé đặt tại SaiGonWaterBus", emailContent.toString());
    }
    
    public HoaDon hoaDonCreate()throws NumberFormatException {
    	HoaDon hd= new HoaDon();
    	if(userDao.findByEmail(sessionHoaDon.getAttribute("emailThanhToan").toString())!=null) {
    		hd.setUser(userDao.findByEmail(sessionHoaDon.getAttribute("emailThanhToan").toString()));
    	}else {
        	hd.setUser(null);
    	}
    	Integer  soLuongGhe=Integer.parseInt(sessionHoaDon.getAttribute("soLuongGhe").toString());
    	Double thanhtien=Double.parseDouble(sessionHoaDon.getAttribute("thanhTien").toString());
    	hd.setSoLuongGhe(soLuongGhe);
    	hd.setNgayTao(LocalDate.now());
    	hd.setTenGhe((String) sessionHoaDon.getAttribute("tenGhe"));
    	hd.setThanhTien(thanhtien);
    	return hd;
    }
    
    public void setStatusVe() {
    	HoaDon hoaDon= hoaDonCreate();
    	hoaDonDAO.save(hoaDon);
    	String tenGhe = (String) sessionHoaDon.getAttribute("tenGhe");
    	String[] dsIdVe = tenGhe.split(",");
        if (dsIdVe != null) {
            for (int i = 0; i < dsIdVe.length; i++) {
               Ve ve = veDAO.findByTenGhe(dsIdVe[i]);
               ve.setStatus(true);
               ve.setHoaDon(hoaDonDAO.findById(hoaDon.getId()).get());
               veDAO.save(ve);
            }
        } else {
        }
    }


    	
    
}
