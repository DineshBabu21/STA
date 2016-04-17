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
 * Created by DineshBabu on 08-04-2016.
 */
public class StudentdbAdapter extends ArrayAdapter {
    List list = new ArrayList();
    public StudentdbAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(Studentdb object) {
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
        studentdbholder studentdbholder;
        if(row==null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row =layoutInflater.inflate(R.layout.studentdblayout,parent,false);
            studentdbholder = new studentdbholder();
            studentdbholder.txbusno = (TextView) row.findViewById(R.id.txtbusno);
            studentdbholder.txdept= (TextView) row.findViewById(R.id.txtdept);
            studentdbholder.txrollno = (TextView) row.findViewById(R.id.txtrollno);
            studentdbholder.txname = (TextView) row.findViewById(R.id.txtname);
            row.setTag(studentdbholder);
        }
        else
        {
            studentdbholder = (StudentdbAdapter.studentdbholder) row.getTag();
        }
         Studentdb studentdb = (Studentdb) this.getItem(position);
        studentdbholder.txname.setText(studentdb.getName());
        studentdbholder.txdept.setText(studentdb.getDept());
        studentdbholder.txbusno.setText(studentdb.getBusnumber());
        studentdbholder.txrollno.setText(studentdb.getRollnumber());
        return row;
    }
    public static class studentdbholder
    {
     TextView txname,txbusno,txrollno,txdept;
    }
}
