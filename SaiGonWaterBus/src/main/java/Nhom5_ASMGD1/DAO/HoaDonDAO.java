package Nhom5_ASMGD1.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Nhom5_ASMGD1.Model.HoaDon;

public interface HoaDonDAO extends JpaRepository<HoaDon, Long>{
	@Query("SELECT o FROM HoaDon o WHERE o.status = true")
	List<HoaDon> findAllByStatusTrue();
    @org.springframework.data.jpa.repository.Query(value = "SELECT * FROM hoa_don WHERE ten_ghe LIKE %?1% OR id_account LIKE ?1", nativeQuery = true)
    List<HoaDon> findBytenGheOridAccount(String keyword);
    @Query(value = "SELECT * FROM hoa_don WHERE id = ?1", nativeQuery = true)
    Optional<HoaDon> findById(Long id);
}
