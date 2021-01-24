
-----------------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION prac_insert() RETURNS TRIGGER AS $$
    BEGIN
        IF EXISTS(SELECT 1 FROM pracownik p WHERE p.email = New.email ) THEN
            -- rekord nie zostanie dodany ani zaktualizowany
            RAISE EXCEPTION 'Taki pracownik już istnieje! Sprawdź poprawność danych';
        ELSE
            RETURN NEW;
        END IF;
    END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER pracownik_test_insert
    BEFORE INSERT OR UPDATE ON pracownik
    FOR EACH ROW EXECUTE PROCEDURE prac_insert();

---------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION stan_insert() RETURNS TRIGGER AS $$
    BEGIN
        IF EXISTS(SELECT 1 FROM stanowisko s WHERE s.stanowisko = New.stanowisko ) THEN
            -- rekord nie zostanie dodany ani zaktualizowany
            RAISE EXCEPTION 'Takie stanowisko już istnieje! Sprawdź poprawność danych';
            RETURN NULL;
        ELSE
            RETURN NEW;
        END IF;
    END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER stanowisko_test_insert
    BEFORE INSERT OR UPDATE ON stanowisko
    FOR EACH ROW EXECUTE PROCEDURE stan_insert();
	
-------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION placa_insert() RETURNS TRIGGER AS $$
    BEGIN
        IF EXISTS(SELECT 1 FROM placa p WHERE p.id_pracownik=New.id_pracownik and p.id_data = New.id_data ) THEN
            -- rekord nie zostanie dodany ani zaktualizowany
            RAISE EXCEPTION 'Taki zapis już istnieje! Sprawdź poprawność danych';
        ELSE
            RETURN NEW;
        END IF;
    END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER placa_test_insert
    BEFORE INSERT ON placa
    FOR EACH ROW EXECUTE PROCEDURE placa_insert();

--------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION premia_insert() RETURNS TRIGGER AS $$
    BEGIN
        IF EXISTS(SELECT 1 FROM premia p WHERE p.procenty=New.procenty and p.opis=New.opis ) THEN
            -- rekord nie zostanie dodany ani zaktualizowany
            RAISE EXCEPTION 'Taki zapis już istnieje! Sprawdź poprawność danych';
            RETURN NULL;
        ELSE
            RETURN NEW;
        END IF;
    END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER premia_test_insert
    BEFORE INSERT OR UPDATE ON premia
    FOR EACH ROW EXECUTE PROCEDURE premia_insert();
	
--------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION kara_insert() RETURNS TRIGGER AS $$
    BEGIN
        IF EXISTS(SELECT 1 FROM kara p WHERE p.procenty=New.procenty and p.opis=New.opis ) THEN
            -- rekord nie zostanie dodany ani zaktualizowany
            RAISE EXCEPTION 'Taki zapis już istnieje! Sprawdź poprawność danych';
            RETURN NULL;
        ELSE
            RETURN NEW;
        END IF;
    END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER kara_test_insert
    BEFORE INSERT OR UPDATE ON kara
    FOR EACH ROW EXECUTE PROCEDURE kara_insert();
	
--------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION bonus_insert() RETURNS TRIGGER AS $$
    BEGIN
        IF EXISTS(SELECT 1 FROM bonus b WHERE b.opis=New.opis ) THEN
            -- rekord nie zostanie dodany ani zaktualizowany
            RAISE EXCEPTION 'Taki zapis już istnieje! Sprawdź poprawność danych';
            RETURN NULL;
        ELSE
            RETURN NEW;
        END IF;
    END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER bonus_test_insert
    BEFORE INSERT OR UPDATE ON bonus
    FOR EACH ROW EXECUTE PROCEDURE bonus_insert();
	
-----------------------------------------------------------------------------------------------------------