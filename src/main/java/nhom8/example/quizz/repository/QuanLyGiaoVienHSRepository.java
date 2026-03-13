package nhom8.example.quizz.repository;

import nhom8.example.quizz.entity.QuanLyGiaoVienHS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuanLyGiaoVienHSRepository extends JpaRepository<QuanLyGiaoVienHS, Integer> {
    List<QuanLyGiaoVienHS> findByGiaoVien_TeacherId(Integer teacherId);
    List<QuanLyGiaoVienHS> findBySinhVien_StudentId(Integer studentId);
    Optional<QuanLyGiaoVienHS> findByGiaoVien_TeacherIdAndSinhVien_StudentId(Integer teacherId, Integer studentId);
}