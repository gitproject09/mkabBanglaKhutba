package org.mkab.bangla_khutba.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.mkab.bangla_khutba.R;
import org.mkab.bangla_khutba.activity.AddItem;
import org.mkab.bangla_khutba.helper.DeleteData;
import org.mkab.bangla_khutba.helper.InsertData;
import org.mkab.bangla_khutba.helper.ReadAllData;
import org.mkab.bangla_khutba.helper.ReadSingleData;
import org.mkab.bangla_khutba.helper.UpdateData;
import org.mkab.bangla_khutba.util.InternetConnection;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sopan Ahmed on 18-Feb-18.
 */

public class ServiceFragment extends Fragment {

    private Button read, readAll, insert, delete, update;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_service, container, false);

        read = rootView.findViewById(R.id.read_btn);
        readAll = rootView.findViewById(R.id.read_all_btn);
        insert = rootView.findViewById(R.id.insert_btn);
        update = rootView.findViewById(R.id.update_btn);
        delete = rootView.findViewById(R.id.delete_btn);


        readAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (InternetConnection.checkConnection(getActivity())) {
                    Intent intent = new Intent(getActivity(), ReadAllData.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_LONG).show();
                }


            }
        });


        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (InternetConnection.checkConnection(getActivity())) {
                    /*Intent intent = new Intent(getActivity(), InsertData.class);
                    startActivity(intent);*/

                    Intent intent = new Intent(getActivity(), AddItem.class);
                    startActivity(intent);


                } else {
                    Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_LONG).show();
                }


            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (InternetConnection.checkConnection(getActivity())) {
                    Intent intent = new Intent(getActivity(), UpdateData.class);
                    startActivity(intent);


                } else {
                    Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_LONG).show();
                }


            }
        });


        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (InternetConnection.checkConnection(getActivity())) {
                    Intent intent = new Intent(getActivity(), ReadSingleData.class);
                    startActivity(intent);


                } else {
                    Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_LONG).show();
                }


            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (InternetConnection.checkConnection(getActivity())) {
                    Intent intent = new Intent(getActivity(), DeleteData.class);
                    startActivity(intent);


                } else {
                    Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_LONG).show();
                }


            }
        });

        return rootView;
    }
}
