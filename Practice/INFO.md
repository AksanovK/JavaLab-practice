#Базы данных


* Бизнес-логика (само приложение) <-> Данные


* СУБД = Приложение для администрирования + база данных



Хранение данных
Поиск
Безопасность
Целостность данных


SQL


Реляционные базы данных

Таблицы

Столбцы - название, тип, ограничения
Строки - каждая строка представляет собой единицу информации



student
id bigint PK| first_name char(20)| last_name char(20)| age integer| group_number integer|
-----------------------------------------------------------------------------------------
           1| Марсель            | Сидиков           | 26         | 09-915              |
subject
id bignint PK| title char(20) |
-------------------------------
          32 | Math           |
          77 | English        |
mark
student_id FK| subject_id FK | value
-------------------------------------
           1 |            32 |    50
           1 |            77 |    99
Primary Key - первичный ключ, идентификатор строки, уникальный для всей таблицы
Foreign Key - внешний ключ, ссылка на другой столбец
student_id -> student (id)
subject_id -> subject (id)

MySQL
SQL Server
SQLite
H2
HSQL
Oracle
PostgreSQL

create table student 
(
	id bigserial primary key,
	first_name char(20),
	last_name char(20),
	age integer,
	group_number integer
);

create table subject
(
    id    bigserial primary key,
    title char(20)
);

create table mark
(
    student_id bigint,
    subject_id bigint,
    value      integer,
    foreign key (student_id) references student (id),
    foreign key (subject_id) references subject (id)
);


insert into student (first_name, last_name, age, group_number)
values ('Марсель', 'Сидиков', 26, 915),
       ('Максим', 'Иванов', 19, 904),
       ('Леонид', 'Тыщенко', 19, 902);

insert into subject (title)
values ('Math'), ('English');

insert into mark (student_id, subject_id, value)
values (2, 1, 90),
       (2, 2, 95),
       (3, 1, 91),
       (3, 2, 96),
       (4, 1, 95),
       (4, 2, 94)

Получение всех записей

select *
from student;

Получение конкретных колонок

select first_name, last_name
from student;

Сортировка

select * from student order by age desc;

Условие выборки

select * from student where age > 25;
select * from student where age < 25 and group_number = 902;
select * from student where age > 25 or group_number = 902;
select *
from student s
where id in (select id from mark where value > 90 and student_id = s.id);

Обновление

update mark set value = 20 where student_id = 2;