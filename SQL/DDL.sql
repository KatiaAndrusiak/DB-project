
CREATE TABLE public.Admin (
                id_admin INTEGER NOT NULL,
                login VARCHAR(30) NOT NULL,
                haslo VARCHAR(30) NOT NULL,
                CONSTRAINT admin_pk PRIMARY KEY (id_admin)
);


CREATE TABLE public.Dzial (
                id_dzial INTEGER NOT NULL,
                opis VARCHAR(30) NOT NULL,
                CONSTRAINT dzial_pk PRIMARY KEY (id_dzial)
);


CREATE SEQUENCE public.data_id_data_seq;

CREATE TABLE public.Data (
                id_data INTEGER NOT NULL DEFAULT nextval('public.data_id_data_seq'),
                miesiac INTEGER NOT NULL,
                rok INTEGER NOT NULL,
                ilosc_dni_robocz INTEGER NOT NULL,
                idPracownik INTEGER NOT NULL,
                CONSTRAINT data_pk PRIMARY KEY (id_data)
);


ALTER SEQUENCE public.data_id_data_seq OWNED BY public.Data.id_data;

CREATE SEQUENCE public.bonus_id_bonus_seq;

CREATE TABLE public.Bonus (
                id_bonus INTEGER NOT NULL DEFAULT nextval('public.bonus_id_bonus_seq'),
                opis VARCHAR(40) NOT NULL,
                CONSTRAINT bonus_pk PRIMARY KEY (id_bonus)
);


ALTER SEQUENCE public.bonus_id_bonus_seq OWNED BY public.Bonus.id_bonus;

CREATE SEQUENCE public.premia_id_premia_seq;

CREATE TABLE public.Premia (
                id_premia INTEGER NOT NULL DEFAULT nextval('public.premia_id_premia_seq'),
                procenty DOUBLE PRECISION NOT NULL,
                opis VARCHAR(30) NOT NULL,
                CONSTRAINT premia_pk PRIMARY KEY (id_premia)
);


ALTER SEQUENCE public.premia_id_premia_seq OWNED BY public.Premia.id_premia;

CREATE SEQUENCE public.kara_id_kara_seq;

CREATE TABLE public.Kara (
                id_kara INTEGER NOT NULL DEFAULT nextval('public.kara_id_kara_seq'),
                procenty DOUBLE PRECISION NOT NULL,
                opis VARCHAR(40) NOT NULL,
                CONSTRAINT kara_pk PRIMARY KEY (id_kara)
);


ALTER SEQUENCE public.kara_id_kara_seq OWNED BY public.Kara.id_kara;

CREATE SEQUENCE public.etat_id_etat_seq_1;

CREATE TABLE public.Etat (
                id_etat INTEGER NOT NULL DEFAULT nextval('public.etat_id_etat_seq_1'),
                etat VARCHAR(30) NOT NULL,
                godzin_dziennie DOUBLE PRECISION NOT NULL,
                CONSTRAINT etat_pk PRIMARY KEY (id_etat)
);


ALTER SEQUENCE public.etat_id_etat_seq_1 OWNED BY public.Etat.id_etat;

CREATE SEQUENCE public.stanowisko_id_stanowisko_seq;

CREATE TABLE public.Stanowisko (
                id_stanowisko INTEGER NOT NULL DEFAULT nextval('public.stanowisko_id_stanowisko_seq'),
                id_dzial INTEGER NOT NULL,
                Stanowisko VARCHAR(30) NOT NULL,
                Stawka_godz DOUBLE PRECISION NOT NULL,
                CONSTRAINT stanowisko_pk PRIMARY KEY (id_stanowisko, id_dzial)
);


ALTER SEQUENCE public.stanowisko_id_stanowisko_seq OWNED BY public.Stanowisko.id_stanowisko;

CREATE SEQUENCE public.pracownik_id_pracownik_seq;

CREATE TABLE public.Pracownik (
                id_pracownik INTEGER NOT NULL DEFAULT nextval('public.pracownik_id_pracownik_seq'),
                Imie VARCHAR(30) NOT NULL,
                Nazwisko VARCHAR(30) NOT NULL,
                Miasto VARCHAR(30) NOT NULL,
                email VARCHAR(50) NOT NULL,
                CONSTRAINT pracownik_pk PRIMARY KEY (id_pracownik)
);


ALTER SEQUENCE public.pracownik_id_pracownik_seq OWNED BY public.Pracownik.id_pracownik;

CREATE TABLE public.prac_login (
                id_pracownik INTEGER NOT NULL,
                login VARCHAR(30) NOT NULL,
                haslo VARCHAR(30) NOT NULL,
                CONSTRAINT prac_login_pk PRIMARY KEY (id_pracownik)
);


CREATE SEQUENCE public.placa_id_placa_seq;

CREATE TABLE public.Placa (
                id_placa INTEGER NOT NULL DEFAULT nextval('public.placa_id_placa_seq'),
                id_pracownik INTEGER NOT NULL,
                id_data INTEGER NOT NULL,
                wyplata DOUBLE PRECISION NOT NULL,
                brutto DOUBLE PRECISION NOT NULL,
                CONSTRAINT placa_pk PRIMARY KEY (id_placa)
);


ALTER SEQUENCE public.placa_id_placa_seq OWNED BY public.Placa.id_placa;

CREATE TABLE public.pl_premia (
                id_placa INTEGER NOT NULL,
                id_premia INTEGER NOT NULL,
                kwota DOUBLE PRECISION NOT NULL,
                CONSTRAINT pl_premia_pk PRIMARY KEY (id_placa, id_premia)
);


CREATE TABLE public.pl_bon (
                id_placa INTEGER NOT NULL,
                id_bonus INTEGER NOT NULL,
                CONSTRAINT pl_bon_pk PRIMARY KEY (id_placa, id_bonus)
);


CREATE TABLE public.pl_kara (
                id_placa INTEGER NOT NULL,
                id_kara INTEGER NOT NULL,
                kwota DOUBLE PRECISION NOT NULL,
                CONSTRAINT pl_kara_pk PRIMARY KEY (id_placa, id_kara)
);


CREATE TABLE public.prac_stan (
                id_pracownik INTEGER NOT NULL,
                id_etat INTEGER NOT NULL,
                id_stanowisko INTEGER NOT NULL,
                id_dzial INTEGER NOT NULL,
                CONSTRAINT prac_stan_pk PRIMARY KEY (id_pracownik, id_etat, id_stanowisko, id_dzial)
);


ALTER TABLE public.Stanowisko ADD CONSTRAINT dzial_stanowisko_fk
FOREIGN KEY (id_dzial)
REFERENCES public.Dzial (id_dzial)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Placa ADD CONSTRAINT miesiac_placa_fk
FOREIGN KEY (id_data)
REFERENCES public.Data (id_data)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.pl_bon ADD CONSTRAINT bonus_bon_placa_fk
FOREIGN KEY (id_bonus)
REFERENCES public.Bonus (id_bonus)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.pl_premia ADD CONSTRAINT premia_pl_premia_fk
FOREIGN KEY (id_premia)
REFERENCES public.Premia (id_premia)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.pl_kara ADD CONSTRAINT kara_pl_kara_fk
FOREIGN KEY (id_kara)
REFERENCES public.Kara (id_kara)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.prac_stan ADD CONSTRAINT etat_prac_stan_fk
FOREIGN KEY (id_etat)
REFERENCES public.Etat (id_etat)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.prac_stan ADD CONSTRAINT stanowisko_prac_stan_fk
FOREIGN KEY (id_stanowisko, id_dzial)
REFERENCES public.Stanowisko (id_stanowisko, id_dzial)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.prac_stan ADD CONSTRAINT pracownik_prac_stan_fk
FOREIGN KEY (id_pracownik)
REFERENCES public.Pracownik (id_pracownik)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Placa ADD CONSTRAINT pracownik_placa_fk
FOREIGN KEY (id_pracownik)
REFERENCES public.Pracownik (id_pracownik)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.prac_login ADD CONSTRAINT pracownik_prac_login_fk
FOREIGN KEY (id_pracownik)
REFERENCES public.Pracownik (id_pracownik)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.pl_kara ADD CONSTRAINT placa_pl_kara_fk
FOREIGN KEY (id_placa)
REFERENCES public.Placa (id_placa)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.pl_bon ADD CONSTRAINT placa_bon_prac_fk
FOREIGN KEY (id_placa)
REFERENCES public.Placa (id_placa)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.pl_premia ADD CONSTRAINT placa_pl_premia_fk
FOREIGN KEY (id_placa)
REFERENCES public.Placa (id_placa)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;