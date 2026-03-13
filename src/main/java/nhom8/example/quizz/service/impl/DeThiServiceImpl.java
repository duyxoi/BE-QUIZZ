package nhom8.example.quizz.service.impl;

import lombok.RequiredArgsConstructor;
import nhom8.example.quizz.entity.DeThi;
import nhom8.example.quizz.repository.DeThiRepository;
import nhom8.example.quizz.service.DeThiService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeThiServiceImpl implements DeThiService {

    // Đây là Repository thực thụ kế thừa JpaRepository
    private final DeThiRepository deThiRepository;

    @Override
    public List<DeThi> getAllDeThi() {
        return deThiRepository.findAll();
    }

    @Override
    public Optional<DeThi> getDeThiById(Integer id) {
        return deThiRepository.findById(id);
    }

    @Override
    @Transactional
    public DeThi createDeThi(DeThi deThi) {
        return deThiRepository.save(deThi);
    }

    @Override
    @Transactional
    public DeThi updateDeThi(Integer id, DeThi deThiDetails) {
        return deThiRepository.findById(id).map(deThi -> {
            deThi.setTenDe(deThiDetails.getTenDe());
            deThi.setThoiGianPhut(deThiDetails.getThoiGianPhut());
            deThi.setTrangThai(deThiDetails.getTrangThai());
            // Không nên update ngayTao và teacher_id thường xuyên
            return deThiRepository.save(deThi);
        }).orElseThrow(() -> new RuntimeException("Không tìm thấy đề thi với ID: " + id));
    }

    @Override
    @Transactional
    public void deleteDeThiById(Integer id) {
        deThiRepository.deleteById(id);
    }

    @Override
    public List<DeThi> getDeThiByTeacherId(Integer teacherId) {
        return deThiRepository.findByGiaoVien_TeacherId(teacherId);
    }
}