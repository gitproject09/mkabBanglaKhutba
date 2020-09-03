package org.mkab.bangla_khutba.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import org.mkab.bangla_khutba.R;
import org.mkab.bangla_khutba.model.MajlishModel;

import androidx.cardview.widget.CardView;

public class MajlishAdapter extends ArrayAdapter<MajlishModel> {

    List<MajlishModel> modelList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public MajlishAdapter(Context context, List<MajlishModel> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        modelList = objects;
    }

    @Override
    public MajlishModel getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout_row_view, parent, false);
            vh = ViewHolder.create((CardView) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        MajlishModel item = getItem(position);

        vh.textViewName.setText(item.getMajlishName());
        vh.textViewCountry.setText(item.getNameOfQuaid());

        return vh.rootView;
    }

    private static class ViewHolder {
        public final CardView rootView;

        public final TextView textViewName;
        public final TextView textViewCountry;

        private ViewHolder(CardView rootView, TextView textViewName, TextView textViewCountry) {
            this.rootView = rootView;
            this.textViewName = textViewName;
            this.textViewCountry = textViewCountry;
        }

        public static ViewHolder create(CardView rootView) {
            TextView textViewName = (TextView) rootView.findViewById(R.id.textViewName);
            TextView textViewCountry = (TextView) rootView.findViewById(R.id.textViewCountry);
            return new ViewHolder(rootView, textViewName, textViewCountry);
        }
    }
}