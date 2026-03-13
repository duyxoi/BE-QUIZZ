package nhom8.example.quizz.repository;

import nhom8.example.quizz.entity.DeThi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeThiRepository extends JpaRepository<DeThi, Integer> {
    List<DeThi> findByGiaoVien_TeacherId(Integer teacherId);
}