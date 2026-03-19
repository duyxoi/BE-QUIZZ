package nhom8.example.quizz.service;


import nhom8.example.quizz.dto.SubmitResponseDTO;
import nhom8.example.quizz.entity.ChiTietBaiLam;
import nhom8.example.quizz.entity.KetQuaThi;
import java.util.List;
import java.util.Optional;

public interface KetQuaThiService {
    // Logic chính: Nộp bài và chấm điểm
    KetQuaThi nopBai(Integer studentId, Integer dethiId, List<ChiTietBaiLam> danhSachTraLoi);

    Optional<KetQuaThi> getKetQuaById(Integer id);

    void createKetQuaThi(KetQuaThi ketQuaThi);

    public SubmitResponseDTO chamDiemVaNopBai(Integer ketQuaId);
    // Lấy lịch sử thi của một sinh viên
    List<KetQuaThi> getHistoryByStudentId(Integer studentId);

    // Lấy danh sách kết quả của một đề thi (cho giáo viên xem)
    List<KetQuaThi> getResultsByDeThiId(Integer dethiId);

    void deleteKetQua(Integer id);
}