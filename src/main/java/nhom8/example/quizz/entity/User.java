package nhom8.example.quizz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users") // Khớp chính xác tên bảng trong SQL
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") // Ánh xạ đúng user_id
    private Integer userId;

    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "full_name", columnDefinition = "NVARCHAR(100)")
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public enum Role {
        student,
        teacher
    }

    // Tự động gán role mặc định là student và gán thời gian tạo nếu cần
    @PrePersist
    protected void onCreate() {
        if (this.role == null) {
            this.role = Role.student;
        }
    }
}