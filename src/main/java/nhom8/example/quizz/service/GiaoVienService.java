package nhom8.example.quizz.service;

import nhom8.example.quizz.entity.GiaoVien;
import java.util.List;
import java.util.Optional;

public interface GiaoVienService {
    List<GiaoVien> getAllGiaoVien();

    Optional<GiaoVien> getGiaoVienById(Integer id);

    // Tìm giáo viên dựa trên tài khoản User (rất hay dùng khi Login xong)
    Optional<GiaoVien> getGiaoVienByUserId(Integer userId);

    GiaoVien createGiaoVien(GiaoVien giaoVien);

    GiaoVien updateGiaoVien(Integer id, GiaoVien giaoVien);

    void deleteGiaoVien(Integer id);
}