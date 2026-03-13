package nhom8.example.quizz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GiaoVien") // Khớp chính xác tên bảng trong SQL
public class GiaoVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id") // Ánh xạ đúng teacher_id
    private Integer teacherId;

    @OneToOne // Thông thường mỗi User chỉ đóng vai trò là 1 GiaoVien
    @JoinColumn(name = "user_id", nullable = false) // Khóa ngoại kết nối bảng Users
    private User user;

    // Sử dụng mappedBy = "giaoVien" để trỏ tới biến giaoVien trong class DeThi
    // Điều này giúp JPA hiểu đây là quan hệ 2 chiều
    @OneToMany(mappedBy = "giaoVien", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DeThi> deThiList;
}