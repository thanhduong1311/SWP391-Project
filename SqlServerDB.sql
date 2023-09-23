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
	id int primary key not null,
	[name] nvarchar(40),
	img varchar(200),
	[unitPrice] decimal(10,2),
	discount int,
	created_at datetime,
	update_at datetime
)

create table [job] (
	id int primary key not null,
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
	id int primary key not null,
	job_id int,
	reason nvarchar(250),
	type int,
	created_at datetime,
	update_at datetime
)

create table [request] (
	id int primary key not null,
	job_id int,
	reason nvarchar(250),
	status int,
	denied_at datetime,
	approve_at datetime,
	created_at datetime,
	update_at datetime
)

create table [rating] (
	id int primary key not null,
	job_id int,
	point int,
	rating_description nvarchar(300),
	created_at datetime,
	update_at datetime
)


create table salary_history (
	id int primary key not null,
	employee_id int,
	job_id int,
	amount decimal(10,2),
	note nvarchar(250),
	created_at datetime,
	update_at datetime
)



create table account (
	[user_id] int primary key not null,
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
	employee_id int primary key not null,
	[user_id] int,
	id_card_number varchar(20),
)



create table customer (
	customer_id int primary key not null,
	[user_id] int,
	total_spend decimal(10,2)
)


create table [notification] (
	id int primary key not null,
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
	id int primary key not null,
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