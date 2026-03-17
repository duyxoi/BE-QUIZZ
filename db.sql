-- 1. Bảng Người dùng (Chỉ chứa thông tin cá nhân và vai trò)

create database quizz

use quizz



CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name NVARCHAR(100),
    role ENUM('student', 'teacher') DEFAULT 'student',
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create table SinhVien(
	student_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id int not null,
    foreign key (user_id) references Users(user_id)
);

create table GiaoVien(
	teacher_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id int not null,
    foreign key (user_id) references Users(user_id)
);


-- 2. Bảng trung gian Quan hệ Giáo viên - Sinh viên (Nhiều - Nhiều)
CREATE TABLE QuanLyGiaoVienHS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    teacher_id INT NOT NULL,
    student_id INT NOT NULL,
    ngay_bat_dau_hoc DATE,
    CONSTRAINT fk_gv FOREIGN KEY (teacher_id) REFERENCES GiaoVien(teacher_id) ON DELETE CASCADE,
    CONSTRAINT fk_hs FOREIGN KEY (student_id) REFERENCES SinhVien(student_id) ON DELETE CASCADE,
    -- Đảm bảo không bị trùng lặp cặp GV-HS
    UNIQUE KEY unique_relationship (teacher_id, student_id)
);

-- 3. Bảng Đề thi
CREATE TABLE DeThi (
    dethi_id INT AUTO_INCREMENT PRIMARY KEY,
    ten_de NVARCHAR(200) NOT NULL,
    thoi_gian_phut INT NOT NULL,
    ngay_tao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    teacher_id INT, -- ID của Giáo viên tạo đề
    trang_thai ENUM('active', 'hidden') DEFAULT 'active',
    CONSTRAINT fk_dethi_user FOREIGN KEY (teacher_id) 
        REFERENCES GiaoVien(teacher_id) ON DELETE SET NULL
);

-- 4. Bảng Câu hỏi chi tiết
CREATE TABLE CauHoiChiTiet (
    cauhoi_id INT AUTO_INCREMENT PRIMARY KEY,
    dethi_id INT NOT NULL,
    noi_dung TEXT NOT NULL,
    hinh_anh_url VARCHAR(255),
    loai_cau_hoi ENUM('trac_nghiem', 'tu_luan') DEFAULT 'trac_nghiem',
    phuong_an_a TEXT,
    phuong_an_b TEXT,
    phuong_an_c TEXT,
    phuong_an_d TEXT,
    dap_an_dung CHAR(1),
    diem_cau_hoi FLOAT DEFAULT 1.0,
    CONSTRAINT fk_cauhoi_dethi FOREIGN KEY (dethi_id) 
        REFERENCES DeThi(dethi_id) ON DELETE CASCADE
);

-- 5. Bảng Kết quả thi
CREATE TABLE KetQuaThi (
    ketqua_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL, -- ID của Sinh viên làm bài
    dethi_id INT NOT NULL,
    tong_diem FLOAT DEFAULT 0,
    thoi_gian_bat_dau DATETIME,
    thoi_gian_nop DATETIME DEFAULT CURRENT_TIMESTAMP,
    trang_thai ENUM('dang_lam', 'da_nop', 'da_cham') DEFAULT 'da_nop',
    nhan_xet_chung TEXT,
    CONSTRAINT fk_kq_user FOREIGN KEY (student_id) 
        REFERENCES SinhVien(student_id) ON DELETE CASCADE,
    CONSTRAINT fk_kq_dethi FOREIGN KEY (dethi_id) 
        REFERENCES DeThi(dethi_id) ON DELETE CASCADE
);

-- 6. Bảng Chi tiết bài làm
CREATE TABLE ChiTietBaiLam (
    chitiet_id INT AUTO_INCREMENT PRIMARY KEY,
    ketqua_id INT NOT NULL,
    cauhoi_id INT NOT NULL,
    dap_an_chon CHAR(1),
    noi_dung_tra_loi TEXT,
    is_correct BOOLEAN DEFAULT FALSE,
    diem_dat_duoc FLOAT DEFAULT 0,
    CONSTRAINT fk_ct_ketqua FOREIGN KEY (ketqua_id) 
        REFERENCES KetQuaThi(ketqua_id) ON DELETE CASCADE,
    CONSTRAINT fk_ct_cauhoi FOREIGN KEY (cauhoi_id) 
        REFERENCES CauHoiChiTiet(cauhoi_id) ON DELETE CASCADE
);


-- ALTER TABLE QuanLyGiaoVienHS 
-- DROP FOREIGN KEY fk_hs;

-- ALTER TABLE QuanLyGiaoVienHS
-- ADD CONSTRAINT fk_hs 
-- FOREIGN KEY (student_id) REFERENCES SinhVien(student_id) 
-- ON DELETE CASCADE 
-- ON UPDATE CASCADE;

INSERT INTO Users (username, password, full_name, role, email) VALUES
('teacher1', '123456', 'Nguyen Van A', 'teacher', 'teacher1@gmail.com'),
('teacher2', '123456', 'Tran Thi B', 'teacher', 'teacher2@gmail.com'),
('student1', '123456', 'Le Van C', 'student', 'student1@gmail.com'),
('student2', '123456', 'Pham Thi D', 'student', 'student2@gmail.com'),
('student3', '123456', 'Hoang Van E', 'student', 'student3@gmail.com');

INSERT INTO GiaoVien (user_id) VALUES
(1),
(2);

INSERT INTO SinhVien (user_id) VALUES
(3),
(4),
(5);

INSERT INTO QuanLyGiaoVienHS (teacher_id, student_id, ngay_bat_dau_hoc) VALUES
(1,1,'2025-09-01'),
(1,2,'2025-09-01'),
(2,3,'2025-09-01');

INSERT INTO DeThi (ten_de, thoi_gian_phut, teacher_id, trang_thai) VALUES
('De thi Java co ban', 30, 1, 'active'),
('De thi C++ nang cao', 45, 2, 'active');

INSERT INTO CauHoiChiTiet 
(dethi_id, noi_dung, loai_cau_hoi, phuong_an_a, phuong_an_b, phuong_an_c, phuong_an_d, dap_an_dung, diem_cau_hoi)
VALUES
(1,'Java la ngon ngu gi?','trac_nghiem',
'OOP','Functional','Procedural','None','A',1),

(1,'Tu khoa dung de ke thua trong Java la gi?','trac_nghiem',
'implement','extends','inherit','super','B',1),

(2,'Toan tu new trong C++ dung de lam gi?','trac_nghiem',
'Cap phat bo nho','Giai phong bo nho','Khai bao bien','Khong gi ca','A',1),

(2,'Ham main trong C++ tra ve kieu gi?','trac_nghiem',
'void','int','float','string','B',1);

INSERT INTO KetQuaThi 
(student_id, dethi_id, tong_diem, thoi_gian_bat_dau, trang_thai)
VALUES
(1,1,2,'2026-03-10 09:00:00','da_nop'),
(2,1,1,'2026-03-10 09:10:00','da_nop'),
(3,2,2,'2026-03-10 09:20:00','da_nop');

INSERT INTO ChiTietBaiLam 
(ketqua_id, cauhoi_id, dap_an_chon, is_correct, diem_dat_duoc)
VALUES
(1,1,'A',true,1),
(1,2,'B',true,1),

(2,1,'A',true,1),
(2,2,'A',false,0),

(3,3,'A',true,1),
(3,4,'B',true,1);

CREATE TABLE MonHoc (
    monhoc_id INT AUTO_INCREMENT PRIMARY KEY,
    ten_mon NVARCHAR(100) NOT NULL,
    mo_ta TEXT
);

-- 2. Thêm cột monhoc_id vào bảng DeThi và tạo liên kết
ALTER TABLE DeThi ADD COLUMN monhoc_id INT;
ALTER TABLE DeThi ADD CONSTRAINT fk_dethi_monhoc 
    FOREIGN KEY (monhoc_id) REFERENCES MonHoc(monhoc_id) ON DELETE SET NULL;
