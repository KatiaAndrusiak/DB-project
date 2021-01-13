package admin_first;

public class Employee {
    private int id;
    private String name;
    private String surname;
    private String city;
    private String email;
    private String position;
    private String department;

    public Employee(int id, String name, String surname, String city, String email, String position, String department) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.email = email;
        this.position = position;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }

    public String getPosition() {
        return position;
    }

    public String getDepartment() {
        return department;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}
