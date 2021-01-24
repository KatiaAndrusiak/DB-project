INSERT INTO Admin values
(1, 'admin', 'admin');


INSERT INTO Dzial values
(1, 'Business Development'),
(2, 'Marketing'),
(3, 'Administracja i HR'),
(4, 'IT'),
(5, 'Obsługa Klienta'),
(6, 'Biuro Rachunkowe');

INSERT INTO Stanowisko (id_dzial,stanowisko, stawka_godz)  values
(1,'Head of Business Development', 150),
(1,'Specjalista ds. sprzedaży B2B', 90),

(2,'Head of Marketing',150),
(2,'Video Manager',100),
(2,'Performance Marketing Manager',100),
(2,'Specjalista ds. Podatków',90),

(3,'CFO',150),
(3,'Główny Księgowy',110),
(3,'HR Manager',100),
(3,'Koordynator Biura',90),
(3,'Młodszy Księgowy',80),

(4,'CTO',150),
(4,'Graphic Designer',120),
(4,'Ruby Developer',110),
(4,'DevOps',130),
(4,'Tester Aplikacji',100),
(4,'Front-end Developer',110),

(5,'Manager Działu Obsługi Klienta',150),
(5,'Specjalista ds. Księgowości',120),
(5,'Koordynator Zespołu Zdalnego',130),

(6,'Manager Biznesowy OBR',150);


INSERT INTO etat (etat, godzin_dziennie)  values
('Pełen etat', 8.0),
('1/4 etatu', 2.0),
('1/2 etatu', 4.0),
('3/4 etatu', 6.0);


insert into Bonus (opis)
values ('Karnet na siłownię')
,('Ubezpieczenie na życie')
,('Służbowa komórka');


insert into Kara (procenty, opis)
values (0.2, 'Opuszczenie pracy bez usprawiedliwienia')
,(0.3, 'Spożywania alkoholu w czasie pracy');


insert into premia (procenty, opis) values
(0.4,'Regulaminowa'),
(0.2, 'Uznaniowa');