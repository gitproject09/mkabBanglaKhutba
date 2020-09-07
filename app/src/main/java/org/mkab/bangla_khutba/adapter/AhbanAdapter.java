package org.mkab.bangla_khutba.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.mkab.bangla_khutba.R;
import org.mkab.bangla_khutba.model.AhbanModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import androidx.cardview.widget.CardView;

public class AhbanAdapter extends ArrayAdapter<AhbanModel> {

    List<AhbanModel> modelList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public AhbanAdapter(Context context, List<AhbanModel> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        modelList = objects;
    }

    @Override
    public AhbanModel getItem(int position) {
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

        AhbanModel item = getItem(position);

        vh.textViewName.setText(item.getName() + "\n" + item.getMajlishName() + ", " + item.getDistrict() + ", " + item.getRegion());
        //vh.textViewCountry.setText("Mobile No: "+item.getMobileNo() + "   Email: "+item.getEmail());


        if (!item.getExpireDate().isEmpty()) {

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

            String date = dateFormat.format(Calendar.getInstance().getTime());

            String[] englishDayMonthYear = date.split("T");
            String[] currentDate = englishDayMonthYear[0].split("-");

            String currentYear = currentDate[0];
            String currentMonth = currentDate[1];
            String currentDay = currentDate[2];

            Log.d("DateCheck", "Current Day in int : " + Integer.valueOf(currentDay));

            String[] exDate = item.getExpireDate().split("-");
            String exDay = exDate[0];
            String exMonth = exDate[1];
            String exYear = exDate[2];

            Log.d("DateCheck", "Expire Day in int : " + Integer.valueOf(exDay));


            if (Integer.valueOf(currentYear).equals(Integer.valueOf(exYear))) { // 2020 hole dhukbe
                Log.d("DateCheck", "Condition true Year : " + exYear);
                if (Integer.valueOf(currentMonth).equals(Integer.valueOf(exMonth))) {
                    Log.d("DateCheck", "Condition true Month : " + exMonth);
                    if (Integer.valueOf(currentDay).equals(Integer.valueOf(exDay))) {
                        vh.tvExpireDate.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
                        vh.tvExpireDate.setText("Already Expired on: " + item.getExpireDate());
                    } else if (Integer.valueOf(currentDay) < Integer.valueOf(exDay)){
                        Log.d("DateCheck", "Condition true Day : " + exMonth);
                        vh.tvExpireDate.setTextColor(context.getResources().getColor(android.R.color.holo_green_light));
                        vh.tvExpireDate.setText("Expire on: " + item.getExpireDate());
                    } else {
                        vh.tvExpireDate.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
                        vh.tvExpireDate.setText("Already Expired on: " + item.getExpireDate());
                    }
                } else if (Integer.valueOf(currentMonth) < Integer.valueOf(exMonth)) {
                    vh.tvExpireDate.setTextColor(context.getResources().getColor(android.R.color.holo_green_light));
                    vh.tvExpireDate.setText("Expire on: " + item.getExpireDate());
                }  else {
                    vh.tvExpireDate.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
                    vh.tvExpireDate.setText("Already Expired on: " + item.getExpireDate());
                }
            } else if(Integer.valueOf(currentYear) < Integer.valueOf(exYear)) {
                vh.tvExpireDate.setTextColor(context.getResources().getColor(android.R.color.holo_green_light));
                vh.tvExpireDate.setText("Expire on: " + item.getExpireDate());
            } else {
                vh.tvExpireDate.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
                vh.tvExpireDate.setText("Already Expired on: " + item.getExpireDate());
            }

        }

        return vh.rootView;
    }

    private static class ViewHolder {

        public final CardView rootView;

        public final TextView textViewName;
        public final TextView textViewCountry;
        public final TextView tvExpireDate;

        private ViewHolder(CardView rootView, TextView textViewName, TextView textViewCountry, TextView tvExpireDate) {
            this.rootView = rootView;
            this.textViewName = textViewName;
            this.textViewCountry = textViewCountry;
            this.tvExpireDate = tvExpireDate;
        }

        public static ViewHolder create(CardView rootView) {
            TextView textViewName = (TextView) rootView.findViewById(R.id.textViewName);
            TextView textViewCountry = (TextView) rootView.findViewById(R.id.textViewCountry);
            TextView tvExpireDate = (TextView) rootView.findViewById(R.id.tvExpireDate);
            return new ViewHolder(rootView, textViewName, textViewCountry, tvExpireDate);
        }
    }
}