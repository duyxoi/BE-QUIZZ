package nhom8.example.quizz.repository;


import nhom8.example.quizz.entity.ChiTietBaiLam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChiTietBaiLamRepository extends JpaRepository<ChiTietBaiLam, Integer> {
    List<ChiTietBaiLam> findByKetQuaThi_KetquaId(Integer ketquaId);
    Optional<ChiTietBaiLam> findByKetQuaThi_KetquaIdAndCauHoi_CauhoiId(Integer ketquaId, Integer cauhoiId);
}