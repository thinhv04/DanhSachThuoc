	-- Tạo database
CREATE DATABASE QuanLyDuAn;
USE QuanLyDuAn;
go
-- drop database QuanLyDuAn

-- unique là không cho các giá trị trong cột trùng lặp
-- decimal là giới hạn số trước dấu phẩy và sau dấu phẩy ví dụ decimal(10,2) thì nhập số 1000,2345 vô nó sẽ thành 1000,23

-- Tạo bảng vai trò
CREATE TABLE VAITRO (
    ma_vai_tro VARCHAR(20) NOT NULL PRIMARY KEY,
    ten_vai_tro NVARCHAR(255) NOT NULL
);

CREATE TABLE QUANLY
(
	ma_quan_ly varchar(5) primary key,
	ho_ten nvarchar(225) not null,
	mat_khau varchar(255) not null,
	email nvarchar(225) not null,
	SDT VARCHAR(15) NOT NULL UNIQUE,
    cccd VARCHAR(20) NOT NULL UNIQUE,
	vai_tro varchar(20) not null,
	hinh nvarchar(100),
	dia_chi NVARCHAR(255) NOT NULL,
	foreign key (vai_tro) REFERENCES VAITRO(ma_vai_tro)
)
select * from CHUYENKHOA
-- Tạo bảng chuyên khoa
CREATE TABLE CHUYENKHOA (
    ma_chuyen_khoa VARCHAR(20) NOT NULL PRIMARY KEY,
    ten_chuyen_khoa NVARCHAR(255) NOT NULL,
	hinh NVARCHAR(255),
	so_luong INT NOT NULL DEFAULT 0
);
ALTER TABLE CHUYENKHOA  
ADD ghi_chu NVARCHAR(MAX);

ALTER TABLE CHUYENKHOA
ALTER COLUMN so_luong INT NULL;


select * from BACSI

CREATE TABLE BACSI (
    ma_bac_si VARCHAR(20) NOT NULL PRIMARY KEY,
    ho_ten NVARCHAR(255) NOT NULL,
	mat_khau varchar(255) NOT NULL,
    gioi_tinh VARCHAR(10) NOT NULL,
    dia_chi NVARCHAR(255) NOT NULL,
    SDT VARCHAR(15) NOT NULL ,
    cccd VARCHAR(20) NOT NULL ,
    email VARCHAR(255) NOT NULL ,
    hinh NVARCHAR(255) NOT NULL,
    vai_tro VARCHAR(20) NOT NULL,
	chuyen_khoa varchar(20) null,
	ghi_chu nvarchar(250) not null,
	FOREIGN KEY (chuyen_khoa) REFERENCES CHUYENKHOA(ma_chuyen_khoa),
    FOREIGN KEY (vai_tro) REFERENCES VAITRO(ma_vai_tro),
);
CREATE TABLE NHANVIEN (
    ma_nhan_vien VARCHAR(20) NOT NULL PRIMARY KEY,
    ho_ten NVARCHAR(255) NOT NULL,
	mat_khau varchar(255) NOT NULL,
    gioi_tinh VARCHAR(10) NOT NULL,
    dia_chi NVARCHAR(255) NOT NULL,
    SDT VARCHAR(15) NOT NULL ,
    cccd VARCHAR(20) NOT NULL ,
    email VARCHAR(255) NOT NULL ,
    hinh NVARCHAR(255) NOT NULL,
    vai_tro VARCHAR(20) NOT NULL,
	ghi_chu nvarchar(250) not null,
    FOREIGN KEY (vai_tro) REFERENCES VAITRO(ma_vai_tro),
);
-- Tạo bảng bệnh nhân
CREATE TABLE BENHNHAN (
    ma_benh_nhan int identity(1,1) PRIMARY KEY,
    ho_ten NVARCHAR(255) NOT NULL,
    nam_sinh DATE ,
    gioi_tinh VARCHAR(10) ,
    sdt VARCHAR(15) ,
    email VARCHAR(255) NOT NULL ,
    mat_khau NVARCHAR(255) ,
    hinh NVARCHAR(255) ,
    tinh_tp NVARCHAR(100) ,
    quan_huyen NVARCHAR(100),
    duong NVARCHAR(255)
);

-- Tạo bảng dịch vụ
CREATE TABLE DICHVU (
    ma_dich_vu VARCHAR(20) NOT NULL PRIMARY KEY,
    ten_dich_vu NVARCHAR(255) NOT NULL,
    mo_ta NVARCHAR(255) NOT NULL,
    gia DECIMAL(10,2) NOT NULL
);

select * from LICHKHAM
-- Tạo bảng lịch khám
CREATE TABLE LICHKHAM (
    ma_lich_kham int identity(1,1) PRIMARY KEY,
    ma_benh_nhan int NOT NULL,
    ma_nhan_vien VARCHAR(20) NULL,
    ma_chuyen_khoa VARCHAR(20) NOT NULL,
	ma_bac_si varchar(20) NULL,
    ngay_kham DATE NOT NULL,
    gio_kham TIME  NOT NULL,
    trang_thai NVARCHAR(50) NOT NULL,
    ghi_chu NVARCHAR(255) NOT NULL,
    FOREIGN KEY (ma_benh_nhan) REFERENCES BENHNHAN(ma_benh_nhan),
    FOREIGN KEY (ma_nhan_vien) REFERENCES NHANVIEN(ma_nhan_vien),
    FOREIGN KEY (ma_chuyen_khoa) REFERENCES CHUYENKHOA(ma_chuyen_khoa),
	FOREIGN KEY (ma_bac_si) REFERENCES BACSI(ma_bac_si),
);

select * from LICHKHAM

SELECT COLUMN_NAME 
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'BENHAN';


ALTER TABLE BENHAN
ADD ma_lich_kham INT NOT NULL;

-- Sau đó thêm ràng buộc khóa ngoại:
ALTER TABLE BENHAN
ADD CONSTRAINT FK_BENHAN_LICHKHAM
FOREIGN KEY (ma_lich_kham) REFERENCES LICHKHAM(ma_lich_kham);

select * from BENHAN
delete from BENHAN
-- Tạo bảng bệnh án
CREATE TABLE BENHAN (
    ma_benh_an int identity(1,1) PRIMARY KEY,
    ten_benh_an NVARCHAR(255) NOT NULL,
    ma_lich_kham int NOT NULL,
    ma_bac_si VARCHAR(20) NOT NULL,
    ngay_kham DATE NOT NULL,
    trieu_chung NVARCHAR(255) NOT NULL,
    dieu_tri NVARCHAR(255) NOT NULL,
    ghi_chu NVARCHAR(255) NOT NULL,
    FOREIGN KEY (ma_lich_kham) REFERENCES LICHKHAM(ma_lich_kham),
    FOREIGN KEY (ma_bac_si) REFERENCES BACSI(ma_bac_si)
);
-- Tạo bảng chi tiết dịch vụ
CREATE TABLE CHITIETDICHVU (
    ma_chi_tiet_dich_vu int identity(1,1) PRIMARY KEY,
    ma_lich_kham int NOT NULL,
    ma_dich_vu VARCHAR(20) NOT NULL,
    soluong INT NOT NULL,
    FOREIGN KEY (ma_lich_kham) REFERENCES LICHKHAM(ma_lich_kham),
    FOREIGN KEY (ma_dich_vu) REFERENCES DICHVU(ma_dich_vu)
);

-- Tạo bảng hóa đơn 
CREATE TABLE HOADON (
    ma_hoa_don INT IDENTITY(1,1) PRIMARY KEY,
    ma_lich_kham INT NOT NULL ,
    ngay_thanh_toan DATE NOT NULL check (ngay_thanh_toan >= CAST(GETDATE() AS DATE)),
    tong_tien DECIMAL(10,2) NOT NULL,
    hinh_thuc NVARCHAR(100) NOT NULL,
    trang_thai NVARCHAR(50) NOT NULL DEFAULT 'Chưa thanh toán',
    FOREIGN KEY (ma_lich_kham) REFERENCES LICHKHAM(ma_lich_kham),
); 
-- Bảng THUOC (Danh sách thuốc)
CREATE TABLE THUOC (
    ma_thuoc VARCHAR(20) NOT NULL PRIMARY KEY,
    ten_thuoc NVARCHAR(255) NOT NULL,
    mo_ta NVARCHAR(255) NOT NULL,
    don_vi NVARCHAR(50) NOT NULL,
    gia_thuoc DECIMAL(10,2) NOT NULL CHECK (gia_thuoc >= 0),
    han_su_dung DATE NOT NULL CHECK (han_su_dung > GETDATE())
); 

-- Bảng DONTHUOC (Đơn thuốc)
CREATE TABLE DONTHUOC (
    ma_don_thuoc INT IDENTITY(1,1) PRIMARY KEY,
    ma_benh_an INT NOT NULL,
    ma_bac_si VARCHAR(20) NOT NULL,
    ngay_lap DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (ma_benh_an) REFERENCES BENHAN(ma_benh_an) ON DELETE CASCADE,
    FOREIGN KEY (ma_bac_si) REFERENCES BACSI(ma_bac_si) ON DELETE CASCADE
);

-- Bảng KHO_THUOC (Số lượng thuốc trong kho)
CREATE TABLE KHO_THUOC (
    ma_thuoc VARCHAR(20) PRIMARY KEY,
    so_luong_hien_co INT NOT NULL DEFAULT 0 CHECK (so_luong_hien_co >= 0),
    FOREIGN KEY (ma_thuoc) REFERENCES THUOC(ma_thuoc) ON DELETE CASCADE
);

-- Bảng NHAPTHUOC (Nhập thuốc vào kho)
CREATE TABLE NHAPTHUOC (
    ma_nhap_thuoc INT IDENTITY(1,1) PRIMARY KEY,
    ma_nhan_vien VARCHAR(20) NOT NULL,
    ma_thuoc VARCHAR(20) NOT NULL,
    so_luong_nhap INT NOT NULL CHECK (so_luong_nhap > 0),
    ngay_nhap DATE NOT NULL DEFAULT GETDATE(),
    nha_cung_cap NVARCHAR(255) NOT NULL,
    ghi_chu NVARCHAR(255),
    FOREIGN KEY (ma_nhan_vien) REFERENCES NHANVIEN(ma_nhan_vien) ON DELETE CASCADE,
    FOREIGN KEY (ma_thuoc) REFERENCES THUOC(ma_thuoc) ON DELETE CASCADE
);

-- Bảng CHITIETDONTHUOC (Chi tiết đơn thuốc)
CREATE TABLE CHITIETDONTHUOC (
    ma_chi_tiet_dt INT IDENTITY(1,1) PRIMARY KEY,
    ma_don_thuoc INT NOT NULL,
    ma_thuoc VARCHAR(20) NOT NULL,
    so_luong INT NOT NULL CHECK (so_luong > 0),
    lieu_luong NVARCHAR(100) NOT NULL,
    FOREIGN KEY (ma_don_thuoc) REFERENCES DONTHUOC(ma_don_thuoc) ON DELETE CASCADE,
    FOREIGN KEY (ma_thuoc) REFERENCES THUOC(ma_thuoc) ON DELETE CASCADE
);

-- Bảng HÓA ĐƠN
CREATE TABLE HOADON_DONTHUOC (
    ma_hoa_don INT IDENTITY(1,1) PRIMARY KEY,
	ma_benh_nhan INT NOT NULL,
    ngay_thanh_toan DATE NOT NULL DEFAULT GETDATE(),
    hinh_thuc NVARCHAR(100) NOT NULL,
    trang_thai NVARCHAR(50) NOT NULL,
	FOREIGN KEY (ma_benh_nhan) REFERENCES BENHNHAN(ma_benh_nhan) ON DELETE CASCADE
);

-- Bảng CHI TIẾT HÓA ĐƠN ĐƠN THUỐC (Quan hệ nhiều - nhiều giữa HÓA ĐƠN và ĐƠN THUỐC)
CREATE TABLE HOADON_CHITIET_DONTHUOC (
    ma_chi_tiet_hd INT IDENTITY(1,1) PRIMARY KEY,
    ma_hoa_don INT NOT NULL,
    ma_don_thuoc INT NOT NULL,
    tong_tien DECIMAL(10,2) NOT NULL CHECK (tong_tien >= 0),
    FOREIGN KEY (ma_hoa_don) REFERENCES HOADON_DONTHUOC(ma_hoa_don) ON DELETE CASCADE,
    FOREIGN KEY (ma_don_thuoc) REFERENCES DONTHUOC(ma_don_thuoc) ON DELETE CASCADE
);
CREATE TABLE LICHSUTHANHTOAN (
    ma_lich_su INT IDENTITY(1,1) PRIMARY KEY,
	ma_benh_nhan int NOT NULL,
    ma_hoa_don INT NOT NULL,
	ma_hoa_don_donthuoc int NULL,
    ma_dich_vu VARCHAR(20) NOT NULL,
    ngay_su_dung DATETIME NOT NULL DEFAULT GETDATE(),
    so_luong INT NOT NULL CHECK (so_luong > 0),
    thanh_tien DECIMAL(10,2) NOT NULL,
    ghi_chu NVARCHAR(255) NULL,
    FOREIGN KEY (ma_hoa_don) REFERENCES HOADON(ma_hoa_don),
	FOREIGN KEY (ma_hoa_don_donthuoc) REFERENCES HOADON(ma_hoa_don),
	FOREIGN KEY (ma_benh_nhan) REFERENCES BENHNHAN(ma_benh_nhan),
    FOREIGN KEY (ma_dich_vu) REFERENCES DICHVU(ma_dich_vu)
);

-------------------------------------------------------------insert vô bảng -------------------------------------------------------------------------
-- insert từng cái 1 đừng kéo 1 lần insert hết là lỗi
-- Bảng VAITRO
INSERT INTO VAITRO (ma_vai_tro, ten_vai_tro) VALUES
('VT01', N'Bác sĩ'),
('VT02', N'Y tá'),
('VT00', N'Quản lý'),
('VT03', N'Nhân viên tiếp tân');

-- Bảng QUANLY
INSERT INTO QUANLY (ma_quan_ly, ho_ten, mat_khau, email, SDT, cccd, vai_tro, hinh, dia_chi)  
VALUES ('QL001', N'Võ Nguyễn Duy Tân', '123', 'a@gmail.com', '0987654321', '123456789012', 'VT00', 'avatar.jpg', N'123 Đường ABC, TP. HCM');
UPDATE QUANLY SET mat_khau = '$2a$10$RsgeqGNABvDJH6c1cCYHqetCYyat6y.cK3eMTPQBjRlLRj/NTJhRO' WHERE email = 'a@gmail.com';
select * from quanly
-- Bảng CHUYENKHOA
INSERT INTO CHUYENKHOA (ma_chuyen_khoa, ten_chuyen_khoa, hinh, so_luong) VALUES
('CK01', N'Nội Tổng Quát', 'noi_tong_quat.jpg', 0),
('CK02', N'Ngoại Tổng Quát', 'ngoai_tong_quat.jpg', 0),
('CK03', N'Nhi Khoa', 'nhi_khoa.jpg', 0),
('CK04', N'Sản Phụ Khoa', 'san_phu_khoa.jpg', 0),
('CK05', N'Răng Hàm Mặt', 'rang_ham_mat.jpg', 0),
('CK06', N'Tai Mũi Họng', 'tai_mui_hong.jpg', 0),
('CK07', N'Mắt', 'mat.jpg', 0),
('CK08', N'Da Liễu', 'da_lieu.jpg', 0),
('CK09', N'Thần Kinh', 'than_kinh.jpg', 0),
('CK10', N'Tim Mạch', 'tim_mach.jpg', 0),
('CK11', N'Nội Tiết', 'noi_tiet.jpg', 0),
('CK12', N'Xương Khớp', 'xuong_khop.jpg', 0),
('CK13', N'Hô Hấp', 'ho_hap.jpg', 0),
('CK14', N'Tiêu Hóa', 'tieu_hoa.jpg', 0),
('CK15', N'Y Học Cổ Truyền', 'y_hoc_co_truyen.jpg', 0);

INSERT INTO BACSI (ma_bac_si, ho_ten, mat_khau, gioi_tinh, dia_chi, SDT, cccd, email, hinh, vai_tro, chuyen_khoa, ghi_chu) 
VALUES 
('BS001', N'Nguyễn Văn A', '123456', 'Nam', N'123 Lê Lợi, Hà Nội', '0987654321', '012345678901', 'nguyenvana@example.com', N'bs001.jpg', 'VT01', 'CK01', N'Bác sĩ chuyên khoa nội'),
('BS002', N'Trần Thị B', 'abcdef', 'Nữ', N'45 Trần Hưng Đạo, TP.HCM', '0912345678', '023456789012', 'tranthib@example.com', N'bs002.jpg', 'VT02', 'CK02', N'Bác sĩ chuyên khoa ngoại'),
('BS003', N'Lê Công C', 'pass789', 'Nam', N'78 Nguyễn Huệ, Đà Nẵng', '0933456789', '034567890123', 'lecongc@example.com', N'bs003.jpg', 'VT01', 'CK03', N'Bác sĩ chuyên khoa nhi'),
('BS004', N'Phạm Hải D', 'qwerty', 'Nam', N'90 Lý Thường Kiệt, Hải Phòng', '0967890123', '045678901234', 'phamhayd@example.com', N'bs004.jpg', 'VT03', 'CK01', N'Bác sĩ chuyên khoa răng hàm mặt'),
('BS005', N'Hoàng Minh E', 'xyz123', 'Nữ', N'12 Hoàng Diệu, Cần Thơ', '0978901234', '056789012345', 'hoangminhe@example.com', N'bs005.jpg', 'VT02', 'CK02', N'Bác sĩ chuyên khoa mắt');


-- Bảng DICHVU
INSERT INTO DICHVU (ma_dich_vu, ten_dich_vu, mo_ta, gia) VALUES
('DV01', N'Khám tổng quát', N'Kiểm tra sức khỏe tổng quát', 500000),
('DV02', N'Xét nghiệm máu', N'Xét nghiệm máu tổng quát', 200000),
('DV03', N'Chụp X-quang', N'Chụp X-quang phổi', 300000);
select * from BENHNHAN

-- Bảng NHANVIEN (tham chiếu VAITRO và CHUYENKHOA)
INSERT INTO NHANVIEN (ma_nhan_vien, ho_ten, gioi_tinh, dia_chi, SDT, cccd, email, hinh, vai_tro, chuyen_khoa) VALUES
('NV01', N'Nguyễn Văn A', 'Nam', N'Hà Nội', '0123456789', '123456789012', 'vana@example.com', 'a.jpg', 'VT01', 'CK01'),
('NV02', N'Trần Thị B', 'Nữ', N'Hồ Chí Minh', '0987654321', '987654321098', 'thib@example.com', 'b.jpg', 'VT02', 'CK02'),
('NV03', N'Lê Văn C', 'Nam', N'Đà Nẵng', '0912345678', '192837465012', 'vanc@example.com', 'c.jpg', 'VT03', 'CK03');

-- Bảng BENHNHAN
INSERT INTO BENHNHAN ( ho_ten, nam_sinh, gioi_tinh, sdt, email, mat_khau, hinh, bao_hiem, tinh_tp, quan_huyen, duong) VALUES
( N'Phạm Thị X', '1990-05-15', 'Nữ', '0901122334', 'phamx@example.com', '123456', 'x.jpg', 'BHYT123', 'Hà Nội', 'Cầu Giấy', 'Trần Duy Hưng'),
( N'Hoàng Văn Y', '1985-10-20', 'Nam', '0978123456', 'hoangy@example.com', 'abcdef', 'y.jpg', 'BHYT456', 'Hồ Chí Minh', 'Quận 1', 'Lê Lợi'),
( N'Đinh Thị Z', '2000-08-25', 'Nữ', '0965566778', 'dinhtz@example.com', 'qwerty', 'z.jpg', 'BHYT789', 'Đà Nẵng', 'Hải Châu', 'Nguyễn Văn Linh');


-- Bảng BENHAN (phụ thuộc NHANVIEN và BENHNHAN)
INSERT INTO BENHAN ( ten_benh_an, ma_benh_nhan, ma_bac_si, ngay_kham, trieu_chung, dieu_tri, ghi_chu) VALUES
(N'Cảm cúm', 1, 'NV01', '2024-02-01', N'Sốt, ho, mệt mỏi', N'Uống thuốc hạ sốt', N'Tái khám sau 1 tuần'),
(N'Viêm phổi', 2, 'NV01', '2024-02-02', N'Khó thở, ho có đờm', N'Dùng kháng sinh, nghỉ ngơi', N'Tái khám sau 2 tuần'),
(N'Đau dạ dày', 3, 'NV01', '2024-02-03', N'Đau bụng, buồn nôn', N'Ăn uống khoa học, thuốc dạ dày', N'Không uống rượu bia');

-- Bảng LICHKHAM (phụ thuộc BENHNHAN, NHANVIEN, DICHVU, CHUYENKHOA)
INSERT INTO LICHKHAM ( ma_benh_nhan, ma_nhan_vien, ma_dich_vu, ma_chuyen_khoa, ngay_kham, gio_kham, trang_thai, ghi_chu) VALUES
(1, 'NV01', 'DV01', 'CK01', '2024-02-10', '08:30:00', N'Chờ khám', N''),
(2, 'NV02', 'DV02', 'CK02', '2024-02-11', '09:00:00', N'Đã khám', N''),
(3, 'NV03', 'DV03', 'CK03', '2024-02-12', '10:00:00', N'Chờ khám', N'');
select * from donthuoc
-- Bảng DONTHUOC (phụ thuộc BENHAN và NHANVIEN)
INSERT INTO DONTHUOC ( ma_benh_an, nhan_vien, ngay_lap) VALUES
( 1, 'NV01', '2024-02-10'),
( 2, 'NV01', '2024-02-11'),
( 3, 'NV01', '2024-02-12');

-- Bảng THUOC
INSERT INTO THUOC (ma_thuoc, ten_thuoc, mo_ta, gia_thuoc, don_vi, han_su_dung, nhan_vien, so_luong_hien_co) VALUES
('T01', N'Paracetamol', N'Giảm đau hạ sốt', 5000, N'Viên', '2025-12-31', 'NV01', 1000),
('T02', N'Amoxicillin', N'Kháng sinh', 10000, N'Viên', '2025-11-30', 'NV01', 1000),
('T03', N'Omeprazol', N'Điều trị dạ dày', 15000, N'Viên', '2025-10-31', 'NV01', 1000);

-- Bảng CHITIETDONTHUOC
INSERT INTO CHITIETDONTHUOC (ma_don_thuoc, ma_thuoc, soluong, lieu_luong) VALUES
(1, 'T01', 10, N'1 viên/lần, 3 lần/ngày'),
(2, 'T02', 14, N'1 viên/lần, 2 lần/ngày'),
(3, 'T03', 20, N'1 viên/lần, 1 lần/ngày');

-- Bảng THANHTOAN
INSERT INTO THANHTOAN ( ma_lich_kham, ngay_thanh_toan, bao_hiem_ho_tro, so_tien_phai_tra, tong_tien, hinh_thuc, trang_thai) VALUES
(1 ,'2024-02-10', 100000, 400000, 500000, N'Tiền mặt', N'Đã thanh toán'),
(2, '2024-02-11', 50000, 150000, 200000, N'Thanh toán online', N'Đã thanh toán'),
(3 ,'2024-02-12', 100000, 200000, 300000, N'Tiền mặt', N'Chưa thanh toán');
select * from donthuoc
-- Bảng CHITIETDICHVU
INSERT INTO CHITIETDICHVU ( ma_lich_kham, ma_dich_vu, soluong, tong_tien) VALUES
(1, 'DV01', 1, 500000),
( 2, 'DV02', 1, 200000),
(3, 'DV03', 1, 300000);

-- Bảng THANHTOANDONTHUOC
INSERT INTO THANHTOAN_DONTHUOC ( ma_don_thuoc, ngay_thanh_toan, bao_hiem_ho_tro, so_tien_phai_tra, tong_tien, hinh_thuc, trang_thai) VALUES
(1, '2025-02-18', 50000, 450000, 500000, 'Tiền mặt', 'Đã thanh toán'),
(2, '2025-02-18', 30000, 170000, 200000, 'Chuyển khoản', 'Đã thanh toán'),
(3,'2025-02-18', 40000, 260000, 300000, 'Tiền mặt', 'Đã thanh toán');


-- trigger 

CREATE TRIGGER trg_update_thuoc
ON CHITIETDONTHUOC
AFTER INSERT
AS
BEGIN
    -- Trừ số lượng thuốc hiện có khi thêm chi tiết đơn thuốc mới
    UPDATE THUOC
    SET so_luong_hien_co = so_luong_hien_co - i.soluong
    FROM THUOC t
    INNER JOIN inserted i ON t.ma_thuoc = i.ma_thuoc;
END;
GO


-- quy trình thêm 1 bệnh nhân cho tới lúc thanh toán thêm từng cái 1 đừng kéo 1 lần hết sẽ lỗi
-- thêm bệnh nhân
INSERT INTO BENHNHAN (ma_benh_nhan, ho_ten, nam_sinh, gioi_tinh, sdt, email, mat_khau, hinh, bao_hiem, tinh_tp, quan_huyen, duong) VALUES
('BN04', N'Nguyễn Văn D', '1995-07-15', 'Nam', '0911223344', 'nguyenvd@example.com', '123456', 'd.jpg', 'BHYT999', 'Hà Nội', 'Hoàn Kiếm', 'Phố Huế');
-- tạo bệnh án
INSERT INTO BENHAN (ma_benh_an, ten_benh_an, ma_benh_nhan, ma_bac_si, ngay_kham, trieu_chung, dieu_tri, ghi_chu) VALUES
('BA04', N'Đau đầu', 'BN04', 'NV01', '2025-02-18', N'Nhức đầu, chóng mặt', N'Uống thuốc giảm đau, nghỉ ngơi', N'Tái khám nếu không đỡ');
-- tạo lịch khám
INSERT INTO LICHKHAM (ma_lich_kham, ma_benh_nhan, ma_nhan_vien, ma_dich_vu, ma_chuyen_khoa, ngay_kham, gio_kham, trang_thai, ghi_chu) VALUES
('LK04', 'BN04', 'NV01', 'DV01', 'CK01', '2025-02-18', '08:00:00', N'Chờ khám', N'');
-- tạo đơn thuốc khi khám xong
INSERT INTO DONTHUOC (ma_don_thuoc, ma_benh_an, nhan_vien, ngay_lap) VALUES
('DT04', 'BA04', 'NV01', '2025-02-18');
-- tạo thông tin cho đơn thuốc
INSERT INTO CHITIETDONTHUOC (ma_don_thuoc, ma_thuoc, soluong, lieu_luong) VALUES
('DT04', 'T01', 10, N'1 viên/lần, 3 lần/ngày');
-- thanh toán tiền khám 
INSERT INTO THANHTOAN (ma_thanh_toan, ma_lich_kham, ngay_thanh_toan, bao_hiem_ho_tro, so_tien_phai_tra, tong_tien, hinh_thuc, trang_thai) VALUES
('TT04', 'LK04', '2025-02-18', 100000, 400000, 500000, N'Tiền mặt', N'Đã thanh toán');
-- thanh toán tiền thuốc
INSERT INTO THANHTOAN_DONTHUOC (ma_thanh_toan_dt, ma_don_thuoc, ngay_thanh_toan, bao_hiem_ho_tro, so_tien_phai_tra, tong_tien, hinh_thuc, trang_thai) VALUES
('TTDT04', 'DT04', '2025-02-18', 50000, 450000, 500000, 'Tiền mặt', 'Đã thanh toán');


select * from THUOC;
