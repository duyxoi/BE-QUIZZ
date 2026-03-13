package nhom8.example.quizz.service;


import nhom8.example.quizz.entity.ChiTietBaiLam;
import java.util.List;
import java.util.Optional;

public interface ChiTietBaiLamService {
    // Tạo mới hoặc cập nhật chi tiết bài làm
    ChiTietBaiLam save(ChiTietBaiLam chiTietBaiLam);

    // Tìm một bản ghi theo ID
    Optional<ChiTietBaiLam> findById(Integer id);

    // Lấy toàn bộ danh sách (nếu cần)
    List<ChiTietBaiLam> findAll();

    // Xóa một bản ghi theo ID
    void deleteById(Integer id);

    // Tìm các câu trả lời theo ID của kết quả thi (Rất quan trọng cho logic xem lại bài)
    List<ChiTietBaiLam> findByKetQuaId(Integer ketquaId);
}