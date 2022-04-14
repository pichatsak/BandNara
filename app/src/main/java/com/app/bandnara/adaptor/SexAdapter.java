package com.app.bandnara.adaptor;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.bandnara.R;

import java.util.List;

public class SexAdapter extends BaseAdapter {

    Context context;
    List<String> itemString;
    LayoutInflater inflter;

    public SexAdapter(Context applicationContext, List<String> itemString) {
        this.context = applicationContext;
        this.itemString = itemString;
        inflter = (LayoutInflater.from(applicationContext));
    }



    @Override
    public int getCount() {
        return itemString.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflter.inflate(R.layout.rowitem, null);
        TextView names = view.findViewById(R.id.showname);
        names.setText(itemString.get(i));
        if(i==0){
            names.setTextColor(Color.GRAY);
        }else{
            names.setTextColor(Color.BLACK);
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        View view = super.getDropDownView(position, convertView, parent);
        TextView tv = (TextView) view.findViewById(R.id.showname);

        tv.setPadding(30,0,0,0);

        return view;
    }
}
