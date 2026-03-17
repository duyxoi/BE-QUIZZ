package nhom8.example.quizz.repository;

import nhom8.example.quizz.entity.MonHoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonHocRepository extends JpaRepository<MonHoc, Integer> {
    // Tìm môn học theo tên nếu cần
    java.util.Optional<MonHoc> findByTenMon(String tenMon);
}