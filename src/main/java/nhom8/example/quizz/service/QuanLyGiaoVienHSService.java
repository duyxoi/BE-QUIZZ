package nhom8.example.quizz.service;


import nhom8.example.quizz.entity.QuanLyGiaoVienHS;
import nhom8.example.quizz.entity.SinhVien;
import nhom8.example.quizz.entity.GiaoVien;

import java.util.List;

public interface QuanLyGiaoVienHSService {
    // Thêm sinh viên vào danh sách quản lý của giáo viên
    QuanLyGiaoVienHS assignStudentToTeacher(Integer teacherId, Integer studentId);

    // Hủy quan hệ quản lý
    void removeStudentFromTeacher(Integer teacherId, Integer studentId);

    // Lấy danh sách tất cả sinh viên của một giáo viên
    List<SinhVien> getStudentsByTeacherId(Integer teacherId);

    // Lấy danh sách tất cả giáo viên của một sinh viên
    List<GiaoVien> getTeachersByStudentId(Integer studentId);

    // Kiểm tra xem sinh viên này có thuộc quyền quản lý của giáo viên này không
    boolean isLinked(Integer teacherId, Integer studentId);
}