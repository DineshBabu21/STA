package www.dinesh21395.tk.sta;

import android.content.Context;

/**
 * Created by dineshbabu on 13-03-2016.
 */
public class BusRoute {
    private String busno,route;
    BusRoute(String busno,String route)
    {
        this.busno=busno;
        this.route=route;
    }


    public String getBusno() {
        return busno;
    }

    public void setBusno(String busno) {
        this.busno = busno;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}
