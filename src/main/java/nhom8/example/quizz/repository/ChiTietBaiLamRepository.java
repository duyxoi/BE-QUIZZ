package nhom8.example.quizz.repository;


import nhom8.example.quizz.entity.ChiTietBaiLam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChiTietBaiLamRepository extends JpaRepository<ChiTietBaiLam, Integer> {
    List<ChiTietBaiLam> findByKetQuaThi_KetquaId(Integer ketquaId);
}