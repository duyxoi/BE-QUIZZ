package nhom8.example.quizz.service.impl;

import lombok.RequiredArgsConstructor;
import nhom8.example.quizz.entity.GiaoVien;
import nhom8.example.quizz.repository.GiaoVienRepository;
import nhom8.example.quizz.service.GiaoVienService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GiaoVienServiceImpl implements GiaoVienService {

    private final GiaoVienRepository giaoVienRepository;

    @Override
    public List<GiaoVien> getAllGiaoVien() {
        return giaoVienRepository.findAll();
    }

    @Override
    public Optional<GiaoVien> getGiaoVienById(Integer id) {
        return giaoVienRepository.findById(id);
    }

    @Override
    public Optional<GiaoVien> getGiaoVienByUserId(Integer userId) {
        // Cần khai báo findByUser_UserId trong GiaoVienRepository
        return giaoVienRepository.findByUser_UserId(userId);
    }

    @Override
    @Transactional
    public GiaoVien createGiaoVien(GiaoVien giaoVien) {
        return giaoVienRepository.save(giaoVien);
    }

    @Override
    @Transactional
    public GiaoVien updateGiaoVien(Integer id, GiaoVien details) {
        return giaoVienRepository.findById(id).map(gv -> {
            // Cập nhật thông tin User liên quan nếu cần, hoặc chỉ cập nhật quan hệ
            gv.setUser(details.getUser());
            return giaoVienRepository.save(gv);
        }).orElseThrow(() -> new RuntimeException("Không tìm thấy Giáo viên với ID: " + id));
    }

    @Override
    @Transactional
    public void deleteGiaoVien(Integer id) {
        giaoVienRepository.deleteById(id);
    }
}