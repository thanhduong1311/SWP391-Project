drop table employee
drop table customer
drop table [request]
drop table [rating]
drop table [report]
drop table [service]
drop table salary_history
drop table [notification]
drop table major
drop table[address]
drop table[job]
drop table account



create table [service] (
	id int primary key not null identity,
	[name] nvarchar(40),
	img varchar(200),
	[unitPrice] decimal(10,2),
	discount int,
	created_at datetime,
	update_at datetime
)

create table [job] (
	id int primary key not null identity,
	[status] int, -- 0: in progress, 1: completed, 2: canceled
	[description] nvarchar(250),
	service_id int,
	[from] datetime,
	[to] datetime,
	[address] nvarchar(250),
	customer_id int,
	employee_id int,
	payment_type int,
	created_at datetime,
	update_at datetime
)

create table [report] (
	id int primary key not null identity,
	job_id int,
	reason nvarchar(250),
	type int,
	created_at datetime,
	update_at datetime
)

create table [request] (
	id int primary key not null identity,
	job_id int,
	reason nvarchar(250),
	status int,
	denied_at datetime,
	approve_at datetime,
	created_at datetime,
	update_at datetime
)

create table [rating] (
	id int primary key not null identity,
	job_id int,
	point int,
	rating_description nvarchar(300),
	created_at datetime,
	update_at datetime
)


create table salary_history (
	id int primary key not null identity,
	employee_id int,
	job_id int,
	amount decimal(10,2),
	note nvarchar(250),
	created_at datetime,
	update_at datetime
)



create table account (
	[user_id] int primary key not null identity,
	username varchar(20),
	[password] varchar(20),
	[status] int,
	full_name nvarchar(50),
	email varchar(100),
	phone_number nvarchar(10),
	address_id int,
	gender nvarchar(10),
	balance decimal(10,2),
	created_at datetime,
	update_at datetime
)



create table employee(
	employee_id int primary key not null identity,
	[user_id] int,
	id_card_number varchar(20),
)



create table customer (
	customer_id int primary key not null identity,
	[user_id] int,
	total_spend decimal(10,2)
)


create table [notification] (
	id int primary key not null identity,
	[user_id] int,
	title nvarchar(200),
	content nvarchar(500),
	read_at datetime,
	created_at datetime,
	update_at datetime
)



create table major(
	service_id int,
	employee_id int
)


create table [address] (
	id int primary key not null identity,
	city nvarchar(100),
	district nvarchar(100),
	commune nvarchar(100),
	lo nvarchar(100)
)

ALTER TABLE [job]
ADD FOREIGN KEY (service_id) REFERENCES [service] (id);
ALTER TABLE [job]
ADD FOREIGN KEY (customer_id) REFERENCES customer(customer_id);
ALTER TABLE [job]
ADD FOREIGN KEY (employee_id) REFERENCES employee(employee_id);
ALTER TABLE [request]
ADD FOREIGN KEY (job_id) REFERENCES [job] (id);

ALTER TABLE [report]
ADD FOREIGN KEY (job_id) REFERENCES [job] (id);

ALTER TABLE [rating]
ADD FOREIGN KEY (job_id) REFERENCES [job] (id);

ALTER TABLE salary_history
ADD FOREIGN KEY (job_id) REFERENCES [job] (id);
ALTER TABLE salary_history
ADD FOREIGN KEY (employee_id) REFERENCES employee (employee_id);

ALTER TABLE account
ADD FOREIGN KEY (address_id) REFERENCES [address] (id);

ALTER TABLE employee
ADD FOREIGN KEY ([user_id]) REFERENCES account ([user_id]);

ALTER TABLE customer
ADD FOREIGN KEY ([user_id]) REFERENCES account ([user_id]);

ALTER TABLE [notification]
ADD FOREIGN KEY ([user_id]) REFERENCES account ([user_id]);

ALTER TABLE major
ADD FOREIGN KEY (employee_id) REFERENCES employee (employee_id);
ALTER TABLE major
ADD FOREIGN KEY (service_id) REFERENCES [service] (id);

insert into [service] (name,img,unitPrice,discount,created_at,update_at) 
values
('House cleaning service',null,15,0,GETDATE(),GETDATE()),
('Air conditioner cleaning',null,15,0,GETDATE(),GETDATE()),
('Deep cleaning service',null,20,0,GETDATE(),GETDATE()),
('Laundry service',null,15,0,GETDATE(),GETDATE()),
('Child care',null,15,0,GETDATE(),GETDATE());


INSERT INTO [address](city,district,commune,lo)
VALUES
('Can Tho','Ninh Kieu','Hung Loi','NVL'),
('Kien Giang','Hon Dat','My Phuoc','Phuoc Hao'),
('Can Tho','Ninh Kieu','Cai Rang','30/4'),
('Tra Vinh','Cau Ngang','Vinh Kim','NVL'),
('Can Thap','Lai Vung','Hoa Thanh','11/2'),
('Can Tho','Ninh Kieu','Hung Khanh','NVL'),
('Can Tho','Ninh Kieu','Long Hoa','NVL'),
('Can Tho','Ninh Kieu','My Tho','NVL'),
('Can Tho','Ninh Kieu','Ninh Kieu','NVL');

insert into account (username,[password],[status],full_name,email,phone_number,address_id,gender,balance,created_at,update_at)
values
('thanhduong','123',1,'Nguyen Thanh Duong','thanhduong@gmail.com','0811448651',1,'male',700,GETDATE(),GETDATE()),
('giahuy','444',1,'Tran Ha Gia Huy','huygia@gmail.com','0873969351',1,'male',850,GETDATE(),GETDATE()),
('anhtuan','1@23',1,'Le Anh Tuan','tuana@gmail.com','0848418651',1,'male',700,GETDATE(),GETDATE()),
('minhhieu','1234',1,'Nguyen Minh Hieu','hieum@gmail.com','0863969351',1,'male',900,GETDATE(),GETDATE()),
('thuytrang','5555',1,'Le Thu Trang','trangl@gmail.com','0858418651',1,'female',800,GETDATE(),GETDATE()),
('vanhan','6666',1,'Tran Van Han','hanv@gmail.com','0873969351',1,'male',750,GETDATE(),GETDATE()),
('ngochuy','7777',1,'Nguyen Thi Ngoc Huy','huyn@gmail.com','0888418651',1,'female',650,GETDATE(),GETDATE()),
('minhduy','8888',1,'Le Minh Duy','duym@gmail.com','0898418651',1,'male',550,GETDATE(),GETDATE()),
('thuyduong','9999',1,'Tran Thuy Duong','duongt@gmail.com','0810418651',1,'female',450,GETDATE(),GETDATE());


insert into employee([user_id],id_card_number) 
values
(5,'0912786452371'),
(6,'0912786452372'),
(7,'0912786452373'),
(8,'0912786452374');

insert into customer([user_id],total_spend) 
values
(1,100),
(2,200),
(3,230),
(5,110);

insert into major(employee_id, service_id)
values
(1,3),
(2,2),
(3,4);

insert into job([status],[description],service_id,[from],[to],[address],customer_id,employee_id,payment_type,created_at,update_at)
values
(0,N'vô nhà anh đi nhẹ nhẹ, nhà có chó',1,'2023-09-23 08:00:00','2023-09-23 10:00:00','160/36 Hung Loi, Ninh Kieu ,Can Tho', 1,null,1,'2023-09-23 08:00:00','2023-09-23 08:00:00'),
(1,N'tới cửa bấm chuông',4,'2023-09-23 08:00:00','2023-09-23 10:00:00','50 Hung Loi, Ninh Kieu ,Can Tho', 1,1,1,'2023-09-23 08:00:00','2023-09-23 08:00:00'),
(2,N'nhà có trẻ em lau nhà nhớ cẩn thận',3,'2023-09-23 08:00:00','2023-09-23 10:00:00','38 Hung Loi, Ninh Kieu ,Can Tho', 1,2,1,'2023-09-23 08:00:00','2023-09-23 08:00:00'),
(3,N'máy lạnh đời cũ nên hơi khó vệ sinh',2,'2023-09-23 08:00:00','2023-09-23 10:00:00','51 Hung Loi, Ninh Kieu ,Can Tho', 1,3,1,'2023-09-23 08:00:00','2023-09-23 08:00:00');

insert into [rating] (job_id,point,rating_description,created_at,update_at)
values
(2,5,N'Làm việc rất ok',getdate(), GETDATE())

select * from [service]
select * from account
select * from employee
select * from customer
select * from major
select * from rating
select * from job where [status] = 1
select * from salary_history



select e.employee_id, c.[user_id], c.full_name, c.username from  
employee e join account c on e.[user_id] = c.[user_id]





