package www.dinesh21395.tk.sta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dineshbabu on 13-03-2016.
 */
public class RouteAdapter extends ArrayAdapter {
    List list =new ArrayList();
    public RouteAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        view = convertView;
        Routeholder routeholder;
        if(view==null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.route_layout,parent,false);
            routeholder= new Routeholder();
            routeholder.txbusno = (TextView) view.findViewById(R.id.txbusno);
            routeholder.txroute = (TextView) view.findViewById(R.id.txroute);
            view.setTag(routeholder);
        }
        else
        {
        routeholder = (Routeholder) view.getTag();
        }
        BusRoute busRoute = (BusRoute) this.getItem(position);
        routeholder.txbusno.setText(busRoute.getBusno());
        routeholder.txroute.setText(busRoute.getRoute());
        return view;
    }

    static class Routeholder
    {
        TextView txbusno,txroute;
    }
}
