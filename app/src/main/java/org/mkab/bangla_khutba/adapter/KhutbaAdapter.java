package org.mkab.bangla_khutba.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import org.mkab.bangla_khutba.R;
import org.mkab.bangla_khutba.model.KhutbaModel;

import androidx.cardview.widget.CardView;

public class KhutbaAdapter extends ArrayAdapter<KhutbaModel> {

    List<KhutbaModel> modelList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public KhutbaAdapter(Context context, List<KhutbaModel> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        modelList = objects;
    }

    @Override
    public KhutbaModel getItem(int position) {
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

        KhutbaModel item = getItem(position);

        vh.textViewName.setText(item.getTitle());
        vh.textViewCountry.setText(item.getDate() + " " + item.getMonth() + " " + item.getYear());

        vh.imageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sIntent = new Intent(android.content.Intent.ACTION_SEND);
                sIntent.setType("text/plain");
                sIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, item.getDate() + " " + item.getMonth() + " " + item.getYear());
                sIntent.putExtra(android.content.Intent.EXTRA_TEXT, item.getKhutba_details());
                context.startActivity(Intent.createChooser(sIntent, "Share this khutba using..."));
            }
        });

        return vh.rootView;
    }

    private static class ViewHolder {
        public final CardView rootView;

        public final TextView textViewName;
        public final TextView textViewCountry;
        public final ImageView imageViewShare;

        private ViewHolder(CardView rootView, TextView textViewName, TextView textViewCountry, ImageView imageViewShare) {
            this.rootView = rootView;
            this.textViewName = textViewName;
            this.textViewCountry = textViewCountry;
            this.imageViewShare = imageViewShare;
        }

        public static ViewHolder create(CardView rootView) {
            TextView textViewName = rootView.findViewById(R.id.textViewName);
            TextView textViewCountry = rootView.findViewById(R.id.textViewCountry);
            ImageView imageViewShare = rootView.findViewById(R.id.ivShare);
            return new ViewHolder(rootView, textViewName, textViewCountry, imageViewShare);
        }
    }
}