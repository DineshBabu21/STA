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
 * Created by dineshbabu on 07-03-2016.
 */
public class ContactAdapter extends ArrayAdapter {
    List list = new ArrayList();
    public ContactAdapter(Context context, int resource) {
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
        View row;
        row=convertView;
        ContactHolder contactHolder;
        if (row==null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row =layoutInflater.inflate(R.layout.rowlayout,parent,false);
            contactHolder =new ContactHolder();
            contactHolder.txname = (TextView) row.findViewById(R.id.tx_name);
            contactHolder.txroll = (TextView) row.findViewById(R.id.tx_rollno);
            contactHolder.txdept = (TextView) row.findViewById(R.id.tx_dept);
            row.setTag(contactHolder);
        }
        else
        {
            contactHolder = (ContactHolder) row.getTag();
        }
        Contacts contacts = (Contacts) this.getItem(position);
        contactHolder.txname.setText(contacts.getName());
        contactHolder.txroll.setText(contacts.getRollno());
        contactHolder.txdept.setText(contacts.getDepartment());

        return row;
    }
    static class ContactHolder
    {
        TextView txname,txroll,txdept;
    }
}
