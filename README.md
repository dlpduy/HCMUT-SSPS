# HCMUT Student Smart Printing Service (HCMUT_SSPS)

## Mô Tả
HCMUT Student Smart Printing Service (HCMUT_SSPS) là hệ thống dịch vụ in thông minh dành cho sinh viên tại trường Đại học Bách khoa ĐHQG-HCM.

## Các Tính Năng Chính
- **In tài liệu**: Sinh viên có thể tải lên tài liệu, chọn máy in và cấu hình các tùy chọn in.
- **Lịch sử in ấn**: Hệ thống ghi lại lịch sử in của sinh viên, bao gồm ID sinh viên, ID máy in, tên tệp, thời gian bắt đầu và kết thúc, số trang, và kích thước giấy.
- **Quản lý máy in**: SPSO có thể thêm, kích hoạt, và vô hiệu hóa các máy in.
- **Quản lý tài khoản sinh viên**: Sinh viên có thể theo dõi số lượng trang in còn lại và mua thêm trang in qua hệ thống thanh toán.
- **Cấu hình hệ thống**: SPSO có thể cấu hình các loại tệp tin được phép tải lên và thiết lập số lượng trang in mặc định cho sinh viên.
- **Báo cáo**: Hệ thống tự động tạo báo cáo sử dụng hàng tháng và hàng năm, có thể được xem bởi SPSO.
- **Xác thực người dùng**: Tất cả người dùng phải đăng nhập qua hệ thống xác thực HCMUT_SSO.

## Công nghệ sử dụng
- **Frontend**: ReactJS (Ứng dụng web).
- **Backend**: Spring Boot (Java).
- **Database**: MySQL.



## Cấu trúc thư mục

```text
/
|-- backend/                 # Mã nguồn backend sử dụng Spring Boot
    |-- database/            # Các script cơ sở dữ liệu MySQL
|-- frontend/                # Mã nguồn frontend sử dụng ReactJS
|-- documents/               # Chi tiết các Yêu cầu, Mô hình hệ thống, Kiến trúc thiết kế của ứng dụng
|-- README.md                # File này
```
