package nhom8.example.quizz.service.impl;

import lombok.RequiredArgsConstructor;
import nhom8.example.quizz.entity.MonHoc;
import nhom8.example.quizz.repository.MonHocRepository;
import nhom8.example.quizz.service.MonHocService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MonHocServiceImpl implements MonHocService {

    private final MonHocRepository monHocRepository;

    @Override
    public List<MonHoc> getAllMonHoc() {
        return monHocRepository.findAll();
    }

    @Override
    public Optional<MonHoc> getMonHocById(Integer id) {
        return monHocRepository.findById(id);
    }

    @Override
    @Transactional
    public MonHoc saveMonHoc(MonHoc monHoc) {
        return monHocRepository.save(monHoc);
    }

    @Override
    @Transactional
    public void deleteMonHoc(Integer id) {
        monHocRepository.deleteById(id);
    }
}