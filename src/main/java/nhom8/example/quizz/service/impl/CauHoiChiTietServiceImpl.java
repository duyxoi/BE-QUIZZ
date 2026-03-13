package nhom8.example.quizz.service.impl;

import lombok.RequiredArgsConstructor;
import nhom8.example.quizz.entity.CauHoiChiTiet;
import nhom8.example.quizz.entity.DeThi;
import nhom8.example.quizz.repository.CauHoiChiTietRepository;
import nhom8.example.quizz.service.CauHoiChiTietService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CauHoiChiTietServiceImpl implements CauHoiChiTietService {

    private final CauHoiChiTietRepository repository;

    @Override
    public List<CauHoiChiTiet> getAllCauHoiChiTietByDeThi(DeThi deThi) {
        // Tìm danh sách câu hỏi theo đối tượng DeThi
        return repository.findByDeThi(deThi);
    }

    @Override
    @Transactional
    public void deleteCauHoiChiTietById(CauHoiChiTiet cauHoiChiTiet) {
        // Xóa dựa trên thực thể (entity) truyền vào
        repository.delete(cauHoiChiTiet);
    }

    @Override
    @Transactional
    public void createCauHoiChiTiet(CauHoiChiTiet cauHoiChiTiet) {
        // Lưu câu hỏi mới
        repository.save(cauHoiChiTiet);
    }

    @Override
    @Transactional
    public void updateCauHoiChiTiet(CauHoiChiTiet cauHoiChiTiet) {
        // JPA tự động hiểu là Update nếu thực thể đã có ID tồn tại
        repository.save(cauHoiChiTiet);
    }
}