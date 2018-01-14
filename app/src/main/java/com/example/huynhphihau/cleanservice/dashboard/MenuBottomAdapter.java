package com.example.huynhphihau.cleanservice.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.model.MenuBottom;

import java.util.List;

/**
 * Created by phihau on 4/24/2017.
 */

public class MenuBottomAdapter extends ArrayAdapter<MenuBottom> {

    public MenuBottomAdapter(Context context, int resource, List<MenuBottom> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.line_menu, null);
        }
        MenuBottom p = getItem(position);
        if (p != null) {
            /*Mapping*/
            TextView txt_title = (TextView) view.findViewById(R.id.txt_code_of_conduct_title);
            TextView txt_sub_title = (TextView) view.findViewById(R.id.txt_code_of_conduct_sub_title);
            /*Set value*/
            txt_title.setText(p.getTitle());
            txt_sub_title.setText(p.getSub_title());
        }
        return view;
    }
}
