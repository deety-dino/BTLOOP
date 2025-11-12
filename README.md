# 🎮BÀI TẬP LỚN: ARKANOID

## 👥 Nhóm phát triển

**Nhóm 4 – Lập trình Game JavaFX**

| Họ và tên           | Vai trò     | Nhiệm vụ chính                                        |
|---------------------|-------------|-------------------------------------------------------|
| Nguyễn Đức Anh      | Trưởng nhóm | Thiết kế giao diện người dùng, xử lý va chạm, sự kiện |
| Nguyễn Phạm Sơn Hải | Thành viên  | Thiết kế đối tượng, ...                               |
| Phạm Minh Đức       | Thành viên  | Xử lý đồ họa, âm thanh, hiệu ứng đặc biệt, ...        |
| Đỗ Tuấn Anh         | Thành viên   | Xử lý powerup, ...                                    |

---

## 🕹️ Giới thiệu

**Arkanoid** là một trò chơi **điều khiển 2D** nơi người chơi điều khiển **ván** (paddle) để điều khiển **những quả bóng** phá những viên gạch.

Game được phát triển bằng **JavaFX** với mục tiêu rèn luyện kỹ năng **lập trình hướng đối tượng, xử lý đồ họa, và quản
lý sự kiện người dùng**.

---

## ✨ Tính năng nổi bật

- 🌆 3 màn chơi với độ khó tăng dần
- 💣 Thu thập vật phẩm để tăng điểm hoặc mở khóa các tính năng hỗ trợ
- 🔊 Hiệu ứng âm thanh và hoạt ảnh mượt mà
- 💾 Lưu tiến độ người chơi bằng tệp JSON

---

## 🧱 Cấu trúc thư mục

```bash
BTLOOP/
 ├── main/
 │   ├── java/
 │   │   ├── game/
 │   │   │   ├── Main.java
 │   │   │   ├── controller/
 │   │   │   ├── model/
 │   │   │   └── view/
 │   └── resources/
 │       ├── images/
 │       ├── sounds/
 │       └── levels/
 └── test/
 ```
## 🧩 Công nghệ sử dụng
- **Java 17**
- **JavaFX 21** (thiết kế giao diện)
- **JSON** (đọc/ghi thông tin)

---

## Nguồn cho asset:
⦁	Tất cả asset ảnh với BGM đều được lấy từ game Blue Archive với font được generate từ web: https://symbolon.pages.dev/  
⦁	Asset cho bóng được lấy từ game Geometry Dash
⦁	SFX trong game cũng lấy từ Blue Archive
