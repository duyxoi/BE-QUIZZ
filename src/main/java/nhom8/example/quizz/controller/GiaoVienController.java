package nhom8.example.quizz.controller;

import nhom8.example.quizz.dto.AssignStudentRequest;
import nhom8.example.quizz.dto.CreateDeThiRequest;
import nhom8.example.quizz.dto.StudentResponseDTO;
import nhom8.example.quizz.dto.UserResponseDTO;
import nhom8.example.quizz.entity.DeThi;
import nhom8.example.quizz.service.DeThiService;
import nhom8.example.quizz.service.QuanLyGiaoVienHSService;
import nhom8.example.quizz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class GiaoVienController {

    @Autowired
    private DeThiService deThiService;

    @Autowired
    private UserService userService;

    @Autowired
    private  QuanLyGiaoVienHSService quanLyService;

    @PostMapping("/create/exam")
    public ResponseEntity<?> create(@RequestBody CreateDeThiRequest request) {
        try {
            DeThi result = deThiService.createFullDeThi(request);
            return ResponseEntity.ok("Tạo đề thi thành công! ID: " + result.getDethiId());
        } catch (Exception e) { 
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 1. Lấy danh sách học sinh của 1 giáo viên
    @GetMapping("/{teacherId}/students")
    public ResponseEntity<List<StudentResponseDTO>> getMyStudents(@PathVariable Integer teacherId) {
        return ResponseEntity.ok(quanLyService.getStudentsByTeacher(teacherId));
    }

    // 2. Thêm học sinh vào danh sách của giáo viên
    @PostMapping("/assign-student")
    public ResponseEntity<String> assignStudent(@RequestBody AssignStudentRequest request) {
        try {
            quanLyService.addStudentToTeacher(request);
            return ResponseEntity.ok("Thêm học sinh thành công!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
