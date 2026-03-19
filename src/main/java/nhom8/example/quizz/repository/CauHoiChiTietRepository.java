package nhom8.example.quizz.repository;

import nhom8.example.quizz.entity.CauHoiChiTiet;
import nhom8.example.quizz.entity.DeThi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CauHoiChiTietRepository extends JpaRepository<CauHoiChiTiet, Integer> {
    List<CauHoiChiTiet> findByDeThi_DethiId(Integer dethiId);
    List<CauHoiChiTiet> findByDeThi(DeThi deThi);
    Optional<CauHoiChiTiet> findByCauhoiId(Integer cauhoiId);
}