package nhom8.example.quizz.repository;

import nhom8.example.quizz.entity.GiaoVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GiaoVienRepository extends JpaRepository<GiaoVien, Integer> {
    Optional<GiaoVien> findByUser_UserId(Integer userId);
}
