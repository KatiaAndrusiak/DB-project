


CREATE OR REPLACE FUNCTION insert_data(mies int, r int, ilosc_dni int, idPrac int)
RETURNS integer AS
    $$
  DECLARE
    st int;
  BEGIN
       IF EXISTS(SELECT 1 FROM data WHERE miesiac=mies and rok=r and idPracownik = idPrac ) THEN
             RAISE EXCEPTION 'Taki zapis już istnieje! Sprawdź poprawność danych';
        ELSE
            insert into data (miesiac, rok, ilosc_dni_robocz, idPracownik) values (mies, r, ilosc_dni, idPrac);
            Select id_data into st FROM data WHERE miesiac=mies and rok=r and ilosc_dni_robocz =ilosc_dni;
        END IF;
    return st;
  END;
$$
LANGUAGE plpgsql;

-------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION stawka_dzien(idPrac int)
RETURNS integer AS
    $$
  DECLARE
    st int;
  BEGIN
      select E.godzin_dziennie*S.Stawka_godz into st
        from pracownik p join prac_stan ps on p.id_pracownik = ps.id_pracownik
                join Stanowisko S on ps.id_stanowisko = S.id_stanowisko
                join Etat E on ps.id_etat = E.id_etat
      where p.id_pracownik=idPrac;
    return st;
  END;
$$
LANGUAGE plpgsql;
-------------------------------------------------------------------------------------------



CREATE OR REPLACE FUNCTION insert_placa(mies int, r int, ilosc_dni int, id_prac int)
RETURNS integer AS
    $$
  DECLARE
    idData int;
    idPlaca int;
  BEGIN
       SELECT * into idData  from insert_data(mies,r,ilosc_dni, id_prac);
       Insert Into Placa (id_pracownik, id_data, wyplata, brutto) values (id_prac,idData,0,0);
       select max(id_placa) into idPlaca from Placa;
    return idPlaca;
  END;
$$
LANGUAGE plpgsql;

--------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION wyplata_mies(id_plac int, id_prac int)
RETURNS integer AS
    $$
  DECLARE
      st int;
      dni int;
      plac float;
  BEGIN
      select * into st from stawka_dzien(id_prac);
      select ilosc_dni_robocz into dni from data d, placa p where p.id_placa =id_plac and p.id_data = d.id_data;
      plac = st*dni;
    return plac;
  END
$$
LANGUAGE plpgsql;

--------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION update_pracownik( id_prac int, nimie varchar, nnazwisko varchar,
nmiasto varchar, nemail varchar, ndzial varchar, nstanowisko varchar, netat varchar)
RETURNS integer AS
    $$
  DECLARE
      idStan int;
      idDzial int;
      idEtat int;
  BEGIN
     Update pracownik
      set imie = nimie, nazwisko = nnazwisko, miasto = nmiasto, email = nemail
      where id_pracownik = id_prac;

     select s.id_stanowisko into idStan from Stanowisko s where s.Stanowisko = nstanowisko;
     select d.id_dzial into idDzial from Dzial d where d.opis =ndzial;
     select e.id_etat into idEtat from  Etat e where e.etat = netat;

     Update prac_stan
      set id_etat = idEtat, id_stanowisko = idStan, id_dzial = idDzial
      where id_pracownik = id_prac;
     return 1;
  END
$$
LANGUAGE plpgsql;

-------------------------------------------------------------------------------------------





CREATE OR REPLACE FUNCTION delete_pracownik(id_prac int)
 RETURNS boolean AS $$
  DECLARE
       var_r RECORD;
  BEGIN
        IF EXISTS(SELECT 1 FROM pracownik p WHERE p.id_pracownik=id_prac) THEN

           delete from prac_stan where id_pracownik = id_prac;

           FOR var_r IN (SELECT * FROM placa WHERE id_pracownik=id_prac)
           LOOP
                delete from pl_premia where id_placa = var_r.id_placa;
                delete from pl_bon where id_placa = var_r.id_placa;
                delete from pl_kara where id_placa = var_r.id_placa;
           END LOOP;

           delete from placa where id_pracownik = id_prac;
           delete from data where idPracownik = id_prac;
           delete from prac_login where id_pracownik = id_prac;
           delete from pracownik where id_pracownik =id_prac;
            return true;
        ELSE
            return false;
        END IF;
    END;
$$ LANGUAGE 'plpgsql';



-----------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION zmien_haslo(idPrac int, oldpass varchar, newpass varchar)
RETURNS integer AS
    $$
  DECLARE

  BEGIN
      IF EXISTS(SELECT 1 FROM prac_login p WHERE p.id_pracownik=idPrac and p.haslo=oldpass) THEN
          update prac_login
          set haslo = newpass
          where id_pracownik=idPrac;
          return 1;
        else
          return 0;
      end if;
  END
$$
LANGUAGE plpgsql;

