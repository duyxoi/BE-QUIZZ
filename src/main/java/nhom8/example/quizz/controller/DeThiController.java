package nhom8.example.quizz.controller;


import nhom8.example.quizz.dto.*;
import nhom8.example.quizz.entity.ChiTietBaiLam;
import nhom8.example.quizz.entity.DeThi;
import nhom8.example.quizz.entity.KetQuaThi;
import nhom8.example.quizz.repository.ChiTietBaiLamRepository;
import nhom8.example.quizz.repository.KetQuaThiRepository;
import nhom8.example.quizz.service.CauHoiChiTietService;
import nhom8.example.quizz.service.DeThiService;
import nhom8.example.quizz.service.KetQuaThiService;
import nhom8.example.quizz.service.SinhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/exam")
public class DeThiController {

    @Autowired
    private DeThiService deThiService;

    @Autowired
    private SinhVienService  sinhVienService;

    @Autowired
    private ChiTietBaiLamRepository chiTietBaiLamRepository;

    @Autowired
    private KetQuaThiService ketQuaThiService;

    @Autowired
    private CauHoiChiTietService cauHoiChiTietService;

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getDeThiById(@PathVariable Integer id) {
            try{
                ExamResponseDTO responseDTO = deThiService.getExamById(id);
                return ResponseEntity.ok(responseDTO);
            }
            catch(Exception e){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
    }
    @PostMapping("/excute/{id}")
    public ResponseEntity<ExamWorkDTO> startExam(@PathVariable("id") Integer dethiId, @RequestParam Integer studentId) {
        DeThi deThi = deThiService.getDeThiById(dethiId).orElseThrow(()-> new RuntimeException("khong tim thay de thi"+dethiId));


        // Tạo mới một lượt thi (KetQuaThi)
        KetQuaThi ketQuaThi = new  KetQuaThi();
        ketQuaThi.setDeThi(deThi);
        ketQuaThi.setSinhVien(sinhVienService.getSinhVienById(studentId).orElseThrow(() -> new RuntimeException("khong tim thay sv"+studentId)));
        ketQuaThi.setThoiGianBatDau(LocalDateTime.now());
        ketQuaThi.setTrangThai(KetQuaThi.TrangThai.dang_lam);

        ketQuaThiService.createKetQuaThi(ketQuaThi);

        // Chuyển đổi sang DTO để trả về Client (giấu đáp án đúng)
        List<QuestionWorkDTO> questions = deThi.getListCauHoiChiTiet().stream().map(q ->
                QuestionWorkDTO.builder()
                        .cauHoiId(q.getCauhoiId())
                        .noiDung(q.getNoiDung())
                        .hinhAnhUrl(q.getHinhAnhUrl())
                        .phuongAnA(q.getPhuongAnA())
                        .phuongAnB(q.getPhuongAnB())
                        .phuongAnC(q.getPhuongAnC())
                        .phuongAnD(q.getPhuongAnD())
                        .loaiCauHoi(q.getLoaiCauHoi().toString())
                        .build()
        ).collect(Collectors.toList());


            return ResponseEntity.ok(ExamWorkDTO.builder()
                .ketQuaId(ketQuaThi.getKetquaId())
                .tenDe(deThi.getTenDe())
                .thoiGianPhut(deThi.getThoiGianPhut())
                .listCauHoi(questions)
                .build());

    }

    // BƯỚC 2: Lưu đáp án khi chọn từng câu
    @PostMapping("/save-answer")
    public ResponseEntity<?> saveAnswer(@RequestBody SubmitAnswerDTO dto){
        // Tìm xem câu này đã làm chưa để cập nhật, chưa thì tạo mới
        ChiTietBaiLam chiTietBaiLam = chiTietBaiLamRepository.findByKetQuaThi_KetquaIdAndCauHoi_CauhoiId(dto.getKetQuaId(),dto.getCauHoiId()).orElse(new ChiTietBaiLam());

        chiTietBaiLam.setKetQuaThi(ketQuaThiService.getKetQuaById(dto.getKetQuaId()).get());
        chiTietBaiLam.setCauHoi(cauHoiChiTietService.getCauHoiChiTietById(dto.getCauHoiId()).get());
        chiTietBaiLam.setDapAnChon(dto.getDapAnChon());

        chiTietBaiLamRepository.save(chiTietBaiLam);
        return ResponseEntity.ok("Da luu cau tra loi");
    }

    @PostMapping("/submit/{ketQuaId}")
    public ResponseEntity<SubmitResponseDTO> submitExam(@PathVariable Integer ketQuaId) {
        SubmitResponseDTO result = ketQuaThiService.chamDiemVaNopBai(ketQuaId);
        return ResponseEntity.ok(result);
    }
}
