package org.mkab.bangla_khutba.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mkab.bangla_khutba.R;
import org.mkab.bangla_khutba.activity.MajlishListActivity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Sopan Ahmed on 18-Feb-18.
 */

public class CategoryFragment extends Fragment {

    @BindView(R.id.home_txtTitle)
    TextView mHomeTxtTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);
        mHomeTxtTitle.setText("Click to View Ahban");
        mHomeTxtTitle.setTextColor(getResources().getColor(R.color.collapse_color));
        mHomeTxtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent majlishIntent = new Intent(getContext(), MajlishListActivity.class);
                startActivity(majlishIntent);
            }
        });
        return rootView;
    }
}
