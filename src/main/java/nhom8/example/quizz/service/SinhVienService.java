package nhom8.example.quizz.service;

import nhom8.example.quizz.entity.SinhVien;
import java.util.List;
import java.util.Optional;

public interface SinhVienService {
    // Lấy danh sách tất cả sinh viên
    List<SinhVien> getAllSinhVien();

    // Tìm sinh viên theo ID (student_id)
    Optional<SinhVien> getSinhVienById(Integer id);

    // Tìm sinh viên theo User ID (Rất quan trọng khi sinh viên đăng nhập)
    Optional<SinhVien> getSinhVienByUserId(Integer userId);

    // Tạo mới hoặc cập nhật thông tin sinh viên
    SinhVien saveSinhVien(SinhVien sinhVien);

    // Xóa sinh viên
    void deleteSinhVien(Integer id);
}