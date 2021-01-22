package salaryhistory;

import employees_list.EmployeeListController;

/**
 * Klasa slużąca do tworzenia historii wyplat
 * @see HistoryController
 */
public class HistoryRow {
    private String date;
    private Integer days;
    private Double brutto;
    private String premia;
    private String kara;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Double getBrutto() {
        return brutto;
    }

    public void setBrutto(Double brutto) {
        this.brutto = brutto;
    }

    public String getPremia() {
        return premia;
    }

    public void setPremia(String premia) {
        this.premia = premia;
    }

    public String getKara() {
        return kara;
    }

    public void setKara(String kara) {
        this.kara = kara;
    }

    public String getBon() {
        return bon;
    }

    public void setBon(String bon) {
        this.bon = bon;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    private String bon;
    private Double total;


    public HistoryRow(String date, Integer days, Double brutto, String premia, String kara, String bon, Double total) {
        this.date = date;
        this.days = days;
        this.brutto = brutto;
        this.premia = premia;
        this.kara = kara;
        this.bon = bon;
        this.total = total;
    }
}
