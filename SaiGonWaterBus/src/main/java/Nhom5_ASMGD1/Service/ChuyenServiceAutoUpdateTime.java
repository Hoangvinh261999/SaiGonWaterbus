package Nhom5_ASMGD1.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import Nhom5_ASMGD1.DAO.ChuyenDAO;
import Nhom5_ASMGD1.DAO.VeDAO;
import Nhom5_ASMGD1.Model.Chuyen;
import Nhom5_ASMGD1.Model.Ve;
@Service
@EnableScheduling
public class ChuyenServiceAutoUpdateTime {
    @Autowired
    ChuyenDAO chuyenDao;
	@Autowired
	VeDAO veDao;
    @Scheduled(cron = "*/15 * * * * *")
//    @Scheduled(cron = "0 0 0 * * *") 
    public void autoupdateDate() {
        LocalDate dateNow = LocalDate.now();
        System.out.println("Thời gian hiện tại: " + dateNow);
        LocalDate oldDate = chuyenDao.findOldate();
        System.out.println("Thời gian cũ: " + oldDate);

        if (oldDate != null && ChronoUnit.DAYS.between(oldDate, dateNow) >= 1) {
            List<Chuyen> chuyenList = chuyenDao.findAll();
            for (Chuyen chuyen : chuyenList) {
                LocalDate newStartDate = chuyen.getNgayKhoiHanh().plusDays(1);    
                chuyen.setNgayKhoiHanh(newStartDate);
            }
            chuyenDao.saveAll(chuyenList);
        }
    }
//    @Scheduled(fixedRate = 60000) // Chạy mỗi phút
    @Scheduled(fixedRate = 180000) // Chạy mỗi phút

	public void updateChuyenDi() {
		List<Chuyen> listChuyen = chuyenDao.findAll();
		LocalDate dateNow = LocalDate.now();
		LocalTime gioNow = LocalTime.now();
		
	  for (Chuyen chuyen : listChuyen) {
            LocalTime gioDi = LocalTime.parse(chuyen.getGioDi(), DateTimeFormatter.ofPattern("HH:mm"));
            LocalDateTime gioDiToday = LocalDateTime.of(dateNow, gioDi);
            
            if (chuyen.getNgayKhoiHanh().equals(dateNow)) {
				// Kiểm tra nếu giờ khởi hành đã trôi qua
	            if (gioDiToday.isBefore(LocalDateTime.now())) {
	                chuyen.setStatus(true);
	                System.out.println("đã update status");
	                chuyenDao.save(chuyen);
	            }
            }
            // Kiểm tra nếu đến hết ngày thì cập nhật ngày khởi hành
            if (chuyen.getNgayKhoiHanh().isBefore(dateNow) && chuyen.getCoDinh()) {
                chuyen.setNgayKhoiHanh(dateNow);
                chuyen.setStatus(false);
                chuyenDao.save(chuyen);
                System.out.println("đã update ngày mới");
                List<Ve> listVe = veDao.findByChuyenId(chuyen.getId());
    	        for (Ve ve : listVe) {
    	            ve.setStatus(false);
    	            veDao.save(ve);
    	        }
    	        System.out.println("đã update vé ngày mới");
            }
            
            //kiểm tra update vé sau kh 10p
            if (chuyen.getNgayKhoiHanh().equals(dateNow)) {
            	
	        	if (gioDiToday.plusMinutes(10).isBefore(LocalDateTime.now())) {
	        	        List<Ve> listVe = veDao.findByChuyenId(chuyen.getId());
	        	        for (Ve ve : listVe) {
	        	            ve.setStatus(true);
	        	            veDao.save(ve);
	        	        }
	        	        System.out.println("đã update vé");
	        	 }
            }
	  	}
	}
}
