package nhom8.example.quizz.service.impl;

import lombok.RequiredArgsConstructor;
import nhom8.example.quizz.entity.SinhVien;
import nhom8.example.quizz.repository.SinhVienRepository;
import nhom8.example.quizz.service.SinhVienService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SinhVienServiceImpl implements SinhVienService {

    private final SinhVienRepository sinhVienRepository;

    @Override
    public List<SinhVien> getAllSinhVien() {
        return sinhVienRepository.findAll();
    }

    @Override
    public Optional<SinhVien> getSinhVienById(Integer id) {
        return sinhVienRepository.findById(id);
    }

    @Override
    public Optional<SinhVien> getSinhVienByUserId(Integer userId) {
        // Phương thức này cần được khai báo trong SinhVienRepository
        return sinhVienRepository.findByUser_UserId(userId);
    }

    @Override
    @Transactional
    public SinhVien saveSinhVien(SinhVien sinhVien) {
        return sinhVienRepository.save(sinhVien);
    }

    @Override
    @Transactional
    public void deleteSinhVien(Integer id) {
        if (sinhVienRepository.existsById(id)) {
            sinhVienRepository.deleteById(id);
        } else {
            throw new RuntimeException("Không tìm thấy Sinh viên với ID: " + id);
        }
    }
}