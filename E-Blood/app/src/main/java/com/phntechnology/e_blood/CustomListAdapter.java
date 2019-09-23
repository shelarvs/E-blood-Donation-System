package com.phntechnology.e_blood;

/**
 * Created by PHN Technology on 10-08-2016.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;

    public CustomListAdapter(Activity context, String[] itemname) {
        super(context, R.layout.display_bloods, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.display_bloods, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.textView6);
        txtTitle.setText(itemname[position]);

        return rowView;

    };
}
