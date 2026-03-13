package nhom8.example.quizz.repository;

import nhom8.example.quizz.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, Integer> {
    Optional<SinhVien> findByUser_UserId(Integer userId);
}