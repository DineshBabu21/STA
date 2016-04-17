package www.dinesh21395.tk.sta;

/**
 * Created by dineshbabu on 07-03-2016.
 */
public class Contacts {

    private String name,department,rollno;

    public Contacts(String name,String rollno,String department)
    {
        this.setName(name);
        this.setRollno(rollno);
        this.setDepartment(department);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }



}
