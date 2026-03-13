package nhom8.example.quizz.service;

import nhom8.example.quizz.entity.DeThi;
import java.util.List;
import java.util.Optional;

public interface DeThiService {
    List<DeThi> getAllDeThi();

    Optional<DeThi> getDeThiById(Integer id);

    DeThi createDeThi(DeThi deThi);

    DeThi updateDeThi(Integer id, DeThi deThi);

    void deleteDeThiById(Integer id);

    // Tìm đề thi theo giáo viên
    List<DeThi> getDeThiByTeacherId(Integer teacherId);
}