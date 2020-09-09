package org.mkab.bangla_khutba.activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.mkab.bangla_khutba.R;
import org.mkab.bangla_khutba.helper.Controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddItem extends AppCompatActivity implements View.OnClickListener {

    EditText editTextItemName,editTextBrand;
    Button buttonAddItem;
    String name;
    String brand;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_item);

        editTextItemName = (EditText)findViewById(R.id.et_item_name);
        editTextBrand = (EditText)findViewById(R.id.et_brand);

        buttonAddItem = (Button)findViewById(R.id.btn_add_item);
        buttonAddItem.setOnClickListener(this);

    }

    //This is the part where data is transafeered from Your Android phone to Sheet by using HTTP Rest API calls

    private void addItemToSheet() {

        name = editTextItemName.getText().toString().trim();
        brand = editTextBrand.getText().toString().trim();

        new AddItem.InsertDataActivity().execute();

    }

    class InsertDataActivity extends AsyncTask< Void, Void, Void > {

        ProgressDialog dialog;
        int jIndex;
        int x;

        String result = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(AddItem.this);
            dialog.setTitle("Hey Wait Please...");
            dialog.setMessage("Inserting your values..");
            dialog.show();

        }

        @Nullable
        @Override
        protected Void doInBackground(Void...params) {

            JSONObject jsonObject = Controller.addItem(name, brand);
            Log.i(Controller.TAG, "Json obj ");

            try {
                /**
                 * Check Whether Its NULL???
                 */
                if (jsonObject != null) {

                    result = jsonObject.getString("result");
                    //Toast.makeText(getApplicationContext(), "Insertion success", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException je) {
                Log.i(Controller.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            finish();
        }
    }


    @Override
    public void onClick(View v) {

        if(v==buttonAddItem){
            addItemToSheet();
            //Define what to do when button is clicked
        }
    }
}