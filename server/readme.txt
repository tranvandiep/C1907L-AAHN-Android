Xây dựng 1 ứng FoodApp
- Ứng dụng mobile
	- Hiển thị danh đồ ăn
	- Thêm/sửa/xoá dữ liệu -> Server

- Server: API -> PHP Core
	URL: http://192.168.2.3/aptech/api/modules/food
		- Lấy được danh sách Food
			GET/POST -> action = list/Empty
			Response: Tra ve danh sach foods

		- Thêm
			POST
				action: add
				data foods
			Response

		- sửa
			POST
				action: update
				datafoods
			Response

		- xoá dữ liệu Food
			POST
				action: delete
				id: ???
			Response


create table foods (
	id int primary key auto_increment,
	title varchar(250) not null,
	thumbnail varchar(500),
	description longtext,
	unikey varchar(32), -> truong khoa duy nhat -> login -> id + time() -> md5 -> key duy nhat
	created_at datetime,
	updated_at datetime
)