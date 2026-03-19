package nhom8.example.quizz.service.impl;

import lombok.RequiredArgsConstructor;
import nhom8.example.quizz.dto.CreateDeThiRequest;
import nhom8.example.quizz.dto.ExamResponseDTO;
import nhom8.example.quizz.dto.SubjectDTO;
import nhom8.example.quizz.entity.CauHoiChiTiet;
import nhom8.example.quizz.entity.DeThi;
import nhom8.example.quizz.entity.GiaoVien;
import nhom8.example.quizz.entity.MonHoc;
import nhom8.example.quizz.repository.CauHoiChiTietRepository;
import nhom8.example.quizz.repository.DeThiRepository;
import nhom8.example.quizz.repository.GiaoVienRepository;
import nhom8.example.quizz.repository.MonHocRepository;
import nhom8.example.quizz.service.DeThiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeThiServiceImpl implements DeThiService {

    // Đây là Repository thực thụ kế thừa JpaRepository


    private final DeThiRepository deThiRepository;
    private final MonHocRepository monHocRepository;
    private final GiaoVienRepository giaoVienRepository;
    private final CauHoiChiTietRepository cauHoiChiTietRepository;


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

    @Override
    @Transactional
    public DeThi createFullDeThi(CreateDeThiRequest request) {
        // 1. Tìm GiaoVien và MonHoc
        GiaoVien gv = giaoVienRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giáo viên"));
        MonHoc mh = monHocRepository.findById(request.getMonhocId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy môn học"));

        // 2. Lưu Đề thi trước
        DeThi deThi = new DeThi();
        deThi.setTenDe(request.getTenDe());
        deThi.setThoiGianPhut(request.getThoiGianPhut());
        deThi.setGiaoVien(gv);
        deThi.setMonHoc(mh);
        deThi.setTrangThai(DeThi.TrangThai.active);
        deThi.setNgayTao(LocalDateTime.now());

        DeThi savedDeThi = deThiRepository.save(deThi);

        // 3. Duyệt danh sách câu hỏi và lưu
        if (request.getQuestions() != null) {
            for (CreateDeThiRequest.CauHoiDTO qDto : request.getQuestions()) {
                CauHoiChiTiet cauHoi = new CauHoiChiTiet();
                cauHoi.setNoiDung(qDto.getNoi_dung());
                cauHoi.setPhuongAnA(qDto.getPhuong_an_a());
                cauHoi.setPhuongAnB(qDto.getPhuong_an_b());
                cauHoi.setPhuongAnC(qDto.getPhuong_an_c());
                cauHoi.setPhuongAnD(qDto.getPhuong_an_d());
                cauHoi.setDapAnDung(qDto.getDap_an_dung());
                cauHoi.setDiemCauHoi(qDto.getDiem_cau_hoi());
                cauHoi.setLoaiCauHoi(CauHoiChiTiet.LoaiCauHoi.valueOf(qDto.getLoai_cau_hoi()));

                cauHoi.setDeThi(savedDeThi); // Gắn vào đề thi vừa tạo
                cauHoiChiTietRepository.save(cauHoi);
            }
        }

        return savedDeThi;
    }

    @Override
    public ExamResponseDTO getExamById(Integer id) {
        DeThi deThi = deThiRepository.findById(id).orElseThrow(() -> new RuntimeException("Khong tim thay De thi "+id));
        return ExamResponseDTO.builder()
                .id(deThi.getDethiId())
                .title(deThi.getTenDe())
                .description("Chua co gi")
                .subject(SubjectDTO.builder()
                        .id(deThi.getMonHoc().getMonhocId())
                        .name(deThi.getMonHoc().getTenMon())
                        .build())
                .questionCount(deThi.getListCauHoiChiTiet().size())
                .durationMinutes(deThi.getThoiGianPhut())
                .build();
    }


}