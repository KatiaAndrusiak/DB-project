
-----------------------------------------------------------------------------------------------------------
CREATE VIEW lista_pracownikow
as
select p.id_pracownik, p.Imie, p.Nazwisko, p.miasto, p.email, S.Stanowisko, D.opis, E.etat
from pracownik p join prac_stan ps on p.id_pracownik = ps.id_pracownik
                join Stanowisko S on ps.id_stanowisko = S.id_stanowisko
                join Dzial D on ps.id_dzial = D.id_dzial
                join Etat E on ps.id_etat = E.id_etat;
				
-----------------------------------------------------------------------------------------------------------

create view historia_wyplat
 as
select p.id_pracownik, p.id_placa, D.miesiac, D.rok, D.ilosc_dni_robocz, p.brutto, p.wyplata
from placa p join Data D on p.id_data = D.id_data;

-----------------------------------------------------------------------------------------------------------

create view naliczone_premie
 as
select pl.id_placa, pp.kwota, pr.opis, pr.procenty*100 as procenty
from placa pl join pl_premia pp on pl.id_placa = pp.id_placa
    join premia pr on pp.id_premia = pr.id_premia;

-----------------------------------------------------------------------------------------------------------

create view naliczone_kary
 as
select pl.id_placa, pk.kwota, k.opis, k.procenty*100 as procenty
from placa pl join pl_kara pk on pl.id_placa = pk.id_placa
    join kara k on pk.id_kara= k.id_kara;
	
-----------------------------------------------------------------------------------------------------------

create view udzielone_bony
 as
select pl.id_placa, b.opis
from placa pl join pl_bon pb on pl.id_placa = pb.id_placa
    join bonus b on pb.id_bonus= b.id_bonus;

-----------------------------------------------------------------------------------------------------------