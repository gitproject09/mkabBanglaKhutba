package org.mkab.bangla_khutba.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.mkab.bangla_khutba.Post.PostData;
import org.mkab.bangla_khutba.R;

public class MainPageActivity extends AppCompatActivity {

    Button getData;
    Button sendData;
    Button viewMajlish;
    Button viewKhutba;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        sendData = findViewById(R.id.insertUser);
        getData = findViewById(R.id.viewUser);
        viewMajlish = findViewById(R.id.viewMajlish);
        viewKhutba = findViewById(R.id.viewKhutba);

        getData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
                startActivity(intent);
            }

        });

        viewMajlish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent majlishIntent = new Intent(getApplicationContext(), MajlishListActivity.class);
                startActivity(majlishIntent);
            }

        });

        viewKhutba.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent majlishIntent = new Intent(getApplicationContext(), KhutbaListActivity.class);
                startActivity(majlishIntent);

            }

        });

        sendData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), PostData.class);
                startActivity(intent);
            }

        });

    }

}
