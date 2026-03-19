package nhom8.example.quizz.service.impl;

import lombok.RequiredArgsConstructor;
import nhom8.example.quizz.dto.SubmitResponseDTO;
import nhom8.example.quizz.entity.*;
import nhom8.example.quizz.repository.*;
import nhom8.example.quizz.service.KetQuaThiService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KetQuaThiServiceImpl implements KetQuaThiService {

    private final KetQuaThiRepository ketQuaThiRepository;
    private final SinhVienRepository sinhVienRepository;
    private final DeThiRepository deThiRepository;
    private final ChiTietBaiLamRepository chiTietBaiLamRepository;

    @Override
    @Transactional
    public KetQuaThi nopBai(Integer studentId, Integer dethiId, List<ChiTietBaiLam> danhSachTraLoi) {
        // 1. Lấy thông tin Sinh viên và Đề thi
        SinhVien sv = sinhVienRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));
        DeThi deThi = deThiRepository.findById(dethiId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đề thi"));

        // 2. Khởi tạo đối tượng Kết quả
        KetQuaThi ketQua = new KetQuaThi();
        ketQua.setSinhVien(sv);
        ketQua.setDeThi(deThi);
        ketQua.setThoiGianNop(LocalDateTime.now());
        ketQua.setTrangThai(KetQuaThi.TrangThai.da_nop);

        // Lưu trước để lấy ID gán cho ChiTietBaiLam
        final KetQuaThi savedKq = ketQuaThiRepository.save(ketQua);

        // 3. Logic Chấm điểm tự động
        float tongDiem = 0;
        for (ChiTietBaiLam chiTiet : danhSachTraLoi) {
            CauHoiChiTiet cauHoi = chiTiet.getCauHoi();

            // Nếu là trắc nghiệm, tự động so sánh đáp án
            if (cauHoi.getLoaiCauHoi() == CauHoiChiTiet.LoaiCauHoi.trac_nghiem) {
                boolean isCorrect = cauHoi.getDapAnDung().equalsIgnoreCase(chiTiet.getDapAnChon());
                chiTiet.setIsCorrect(isCorrect);
                float diemdat = isCorrect ? cauHoi.getDiemCauHoi() : 0;
                chiTiet.setDiemDatDuoc(diemdat);
                tongDiem += diemdat;
            } else {
                // Nếu là tự luận, mặc định chưa có điểm (để giáo viên chấm sau)
                chiTiet.setIsCorrect(false);
                chiTiet.setDiemDatDuoc(0f);
            }

            chiTiet.setKetQuaThi(savedKq);
            chiTietBaiLamRepository.save(chiTiet);
        }

        // 4. Cập nhật tổng điểm cuối cùng
        savedKq.setTongDiem(tongDiem);
        return ketQuaThiRepository.save(savedKq);
    }

    @Override
    public Optional<KetQuaThi> getKetQuaById(Integer id) {
        return ketQuaThiRepository.findById(id);
    }

    @Override
    public void createKetQuaThi(KetQuaThi ketQuaThi) {
        ketQuaThiRepository.save(ketQuaThi);
    }

    @Override
    public List<KetQuaThi> getHistoryByStudentId(Integer studentId) {
        return ketQuaThiRepository.findBySinhVien_StudentId(studentId);
    }

    @Override
    public List<KetQuaThi> getResultsByDeThiId(Integer dethiId) {
        return ketQuaThiRepository.findByDeThi_DethiId(dethiId);
    }

    @Override
    @Transactional
    public void deleteKetQua(Integer id) {
        ketQuaThiRepository.deleteById(id);
    }


    @Override
    @Transactional
    public SubmitResponseDTO chamDiemVaNopBai(Integer ketQuaId) {
        // 1. Lấy thông tin lượt thi kèm danh sách chi tiết bài làm
        KetQuaThi ketQua = ketQuaThiRepository.findById(ketQuaId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lượt thi"));

        if (ketQua.getTrangThai() == KetQuaThi.TrangThai.da_nop) {
            throw new RuntimeException("Bài thi này đã được nộp trước đó!");
        }

        List<ChiTietBaiLam> listBaiLam = ketQua.getListChiTietBaiLam();
        int soCauDung = 0;
        int tongSoCau = ketQua.getDeThi().getListCauHoiChiTiet().size();

        // Giả sử mỗi câu hỏi có điểm riêng (diemCauHoi), nếu không có thì chia đều thang điểm 10
        float tongDiem = 0;

        // 2. Duyệt qua từng câu sinh viên đã làm
        for (ChiTietBaiLam chiTiet : listBaiLam) {
            CauHoiChiTiet cauHoi = chiTiet.getCauHoi();

            // So sánh đáp án (Chỉ tính cho trắc nghiệm)
            if (cauHoi.getLoaiCauHoi() == CauHoiChiTiet.LoaiCauHoi.trac_nghiem) {
                if (chiTiet.getDapAnChon() != null &&
                        chiTiet.getDapAnChon().equalsIgnoreCase(cauHoi.getDapAnDung())) {

                    chiTiet.setIsCorrect(true);
                    chiTiet.setDiemDatDuoc(cauHoi.getDiemCauHoi());
                    soCauDung++;
                } else {
                    chiTiet.setIsCorrect(false);
                    chiTiet.setDiemDatDuoc(0f);
                }
            }
            // Với tự luận: isCorrect và diemDatDuoc sẽ để GV chấm sau (mặc định false/0)
        }

        tongDiem = (float) ((soCauDung*1.0)/tongSoCau *10.0);
        // 3. Cập nhật trạng thái KetQuaThi
        ketQua.setTongDiem(tongDiem);
        ketQua.setTrangThai(KetQuaThi.TrangThai.da_nop);
        // Lưu ý: Đảm bảo field thoiGianNop trong Entity có thể ghi được (bỏ insertable=false)
        ketQua.setThoiGianNop(LocalDateTime.now());

        ketQuaThiRepository.save(ketQua);

        return SubmitResponseDTO.builder()
                .ketQuaId(ketQuaId)
                .tongDiem(tongDiem)
                .soCauDung(soCauDung)
                .tongSoCau(tongSoCau)
                .thoiGianNop(LocalDateTime.now().toString())
                .build();
    }
}