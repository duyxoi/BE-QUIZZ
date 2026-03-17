package nhom8.example.quizz.service;

import nhom8.example.quizz.entity.MonHoc;
import java.util.List;
import java.util.Optional;

public interface MonHocService {
    List<MonHoc> getAllMonHoc();
    Optional<MonHoc> getMonHocById(Integer id);
    MonHoc saveMonHoc(MonHoc monHoc);
    void deleteMonHoc(Integer id);
}