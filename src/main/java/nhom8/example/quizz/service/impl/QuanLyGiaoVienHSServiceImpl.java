package nhom8.example.quizz.service.impl;


import lombok.RequiredArgsConstructor;
import nhom8.example.quizz.dto.AssignStudentRequest;
import nhom8.example.quizz.dto.StudentResponseDTO;
import nhom8.example.quizz.entity.GiaoVien;
import nhom8.example.quizz.entity.QuanLyGiaoVienHS;
import nhom8.example.quizz.entity.SinhVien;
import nhom8.example.quizz.repository.GiaoVienRepository;
import nhom8.example.quizz.repository.QuanLyGiaoVienHSRepository;
import nhom8.example.quizz.repository.SinhVienRepository;
import nhom8.example.quizz.service.QuanLyGiaoVienHSService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuanLyGiaoVienHSServiceImpl implements QuanLyGiaoVienHSService {

    private final QuanLyGiaoVienHSRepository quanLyRepo;
    private final GiaoVienRepository giaoVienRepo;
    private final SinhVienRepository sinhVienRepo;

    @Override
    @Transactional
    public QuanLyGiaoVienHS assignStudentToTeacher(Integer teacherId, Integer studentId) {
        // 1. Kiểm tra tồn tại
        GiaoVien gv = giaoVienRepo.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Giáo viên không tồn tại"));
        SinhVien sv = sinhVienRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Sinh viên không tồn tại"));

        // 2. Kiểm tra xem đã kết nối chưa (tránh trùng lặp UNIQUE KEY)
        if (isLinked(teacherId, studentId)) {
            throw new RuntimeException("Sinh viên này đã được giáo viên quản lý rồi!");
        }

        // 3. Tạo mới quan hệ
        QuanLyGiaoVienHS quanLy = new QuanLyGiaoVienHS();
        quanLy.setGiaoVien(gv);
        quanLy.setSinhVien(sv);
        quanLy.setNgayBatDauHoc(LocalDate.now());

        return quanLyRepo.save(quanLy);
    }

    @Override
    @Transactional
    public void removeStudentFromTeacher(Integer teacherId, Integer studentId) {
        QuanLyGiaoVienHS link = quanLyRepo.findByGiaoVien_TeacherIdAndSinhVien_StudentId(teacherId, studentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mối quan hệ quản lý này"));
        quanLyRepo.delete(link);
    }

    @Override
    public List<SinhVien> getStudentsByTeacherId(Integer teacherId) {
        // Lấy danh sách trung gian rồi trích xuất đối tượng SinhVien
        return quanLyRepo.findByGiaoVien_TeacherId(teacherId)
                .stream()
                .map(QuanLyGiaoVienHS::getSinhVien)
                .collect(Collectors.toList());
    }

    @Override
    public List<GiaoVien> getTeachersByStudentId(Integer studentId) {
        // Lấy danh sách trung gian rồi trích xuất đối tượng GiaoVien
        return quanLyRepo.findBySinhVien_StudentId(studentId)
                .stream()
                .map(QuanLyGiaoVienHS::getGiaoVien)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isLinked(Integer teacherId, Integer studentId) {
        return quanLyRepo.findByGiaoVien_TeacherIdAndSinhVien_StudentId(teacherId, studentId).isPresent();
    }

    @Override
    public List<StudentResponseDTO> getStudentsByTeacher(Integer teacherId) {
        return quanLyRepo.findByGiaoVien_TeacherId(teacherId).stream()
                .map(item -> StudentResponseDTO.builder()
                        .studentId(item.getSinhVien().getStudentId())
                        .fullName(item.getSinhVien().getUser().getFullName())
                        .email(item.getSinhVien().getUser().getEmail())
                        .username(item.getSinhVien().getUser().getUsername())
                        .ngayBatDauHoc(item.getNgayBatDauHoc().toString())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addStudentToTeacher(AssignStudentRequest request) {
        // 1. Kiểm tra tồn tại
        GiaoVien gv = giaoVienRepo.findById(request.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Giáo viên không tồn tại"));
        SinhVien sv = sinhVienRepo.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Sinh viên không tồn tại"));

        // 2. Kiểm tra trùng lặp
        if (quanLyRepo.existsByGiaoVien_TeacherIdAndSinhVien_StudentId(request.getTeacherId(), request.getStudentId())) {
            throw new RuntimeException("Sinh viên này đã có trong danh sách quản lý!");
        }

        // 3. Lưu mối quan hệ
        QuanLyGiaoVienHS link = new QuanLyGiaoVienHS();
        link.setGiaoVien(gv);
        link.setSinhVien(sv);
        link.setNgayBatDauHoc(LocalDate.now()); // Lấy ngày hiện tại

        quanLyRepo.save(link);
    }

}