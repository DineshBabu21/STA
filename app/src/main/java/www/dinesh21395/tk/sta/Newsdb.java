package www.dinesh21395.tk.sta;

/**
 * Created by DineshBabu on 10-04-2016.
 */
public class Newsdb {
    private String busno;
    private String date;
    private String info;

    public Newsdb(String busno,String date,String info)
    {
        this.setBusno(busno);
        this.setDate(date);
        this.setInfo(info);
    }
    public String getBusno() {
        return busno;
    }

    public void setBusno(String busno) {
        this.busno = busno;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
