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
 * Created by DineshBabu on 10-04-2016.
 */
public class NewsdbAdapter extends ArrayAdapter {
    List list = new ArrayList();
    public NewsdbAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(Newsdb object) {
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
        View row;
        row = convertView;
        newsholder newsholder;
        if(row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.news_layout,parent,false);
            newsholder = new newsholder();
            newsholder.txbusno = (TextView) row.findViewById(R.id.txtnewsbusno);
            newsholder.txdate = (TextView) row.findViewById(R.id.txtnewsdate);
            newsholder.txinfo = (TextView) row.findViewById(R.id.txtnewsinfo);
            row.setTag(newsholder);
        }
        else
        {
            newsholder = (NewsdbAdapter.newsholder) row.getTag();
        }
        Newsdb newsdb = (Newsdb) this.getItem(position);
        newsholder.txbusno.setText(newsdb.getBusno());
        newsholder.txdate.setText(newsdb.getDate());
        newsholder.txinfo.setText(newsdb.getInfo());
        return row;
    }
    public static class newsholder
    {
        TextView txbusno,txdate,txinfo;
    }
}
