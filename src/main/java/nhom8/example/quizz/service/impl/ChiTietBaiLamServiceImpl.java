package nhom8.example.quizz.service.impl;

import lombok.RequiredArgsConstructor;
import nhom8.example.quizz.entity.ChiTietBaiLam;
import nhom8.example.quizz.repository.ChiTietBaiLamRepository;
import nhom8.example.quizz.service.ChiTietBaiLamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChiTietBaiLamServiceImpl implements ChiTietBaiLamService {

    private final ChiTietBaiLamRepository chiTietBaiLamRepository;

    @Override
    @Transactional
    public ChiTietBaiLam save(ChiTietBaiLam chiTietBaiLam) {
        // Bạn có thể thêm logic kiểm tra isCorrect tại đây trước khi save
        return chiTietBaiLamRepository.save(chiTietBaiLam);
    }

    @Override
    public Optional<ChiTietBaiLam> findById(Integer id) {
        return chiTietBaiLamRepository.findById(id);
    }

    @Override
    public List<ChiTietBaiLam> findAll() {
        return chiTietBaiLamRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        chiTietBaiLamRepository.deleteById(id);
    }

    @Override
    public List<ChiTietBaiLam> findByKetQuaId(Integer ketquaId) {
        // Phương thức này được định nghĩa trong Repository
        return chiTietBaiLamRepository.findByKetQuaThi_KetquaId(ketquaId);
    }
}