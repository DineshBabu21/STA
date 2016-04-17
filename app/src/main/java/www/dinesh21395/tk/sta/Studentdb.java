package www.dinesh21395.tk.sta;

/**
 * Created by DineshBabu on 08-04-2016.
 */
public class Studentdb {
    private String name;
    private String rollnumber;
    private String dept;
    private String busnumber;

    Studentdb(String name,String rollnumber,String dept,String busnumber)
    {
        this.setName(name);
        this.setRollnumber(rollnumber);
        this.setDept(dept);
        this.setBusnumber(busnumber);

    }
    public String getBusnumber() {
        return busnumber;
    }

    public void setBusnumber(String busnumber) {
        this.busnumber = busnumber;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getRollnumber() {
        return rollnumber;
    }

    public void setRollnumber(String rollnumber) {
        this.rollnumber = rollnumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
