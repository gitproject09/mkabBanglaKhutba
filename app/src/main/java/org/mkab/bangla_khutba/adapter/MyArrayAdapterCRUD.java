package org.mkab.bangla_khutba.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.mkab.bangla_khutba.R;
import org.mkab.bangla_khutba.model.MyDataModelCRUD;

import java.util.List;

public class MyArrayAdapterCRUD extends ArrayAdapter<MyDataModelCRUD> {

    List<MyDataModelCRUD> modelList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public MyArrayAdapterCRUD(Context context, List<MyDataModelCRUD> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        modelList = objects;
    }

    @Override
    public MyDataModelCRUD getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout_row_view_crud, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        MyDataModelCRUD item = getItem(position);

        vh.textViewId.setText(item.getId());
        vh.textViewName.setText(item.getName());


        return vh.rootView;
    }


    private static class ViewHolder {
        public final RelativeLayout rootView;

        public final TextView textViewId;
        public final TextView textViewName;


        private ViewHolder(RelativeLayout rootView, TextView textViewName, TextView textViewId) {
            this.rootView = rootView;
            this.textViewId = textViewId;
            this.textViewName = textViewName;

        }

        public static ViewHolder create(RelativeLayout rootView) {
            TextView textViewId = (TextView) rootView.findViewById(R.id.textViewId);
            TextView textViewName = (TextView) rootView.findViewById(R.id.textViewName);

            return new ViewHolder(rootView, textViewName, textViewId);
        }
    }
}
