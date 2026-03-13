package nhom8.example.quizz.repository;

import nhom8.example.quizz.entity.DeThi;
import nhom8.example.quizz.entity.KetQuaThi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KetQuaThiRepository extends JpaRepository<KetQuaThi, Integer> {
    List<KetQuaThi> findBySinhVien_StudentId(Integer studentId);
    List<KetQuaThi> findByDeThi_DethiId(Integer dethiId);
}