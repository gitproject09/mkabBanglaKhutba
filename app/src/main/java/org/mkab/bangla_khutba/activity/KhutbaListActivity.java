package org.mkab.bangla_khutba.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import org.mkab.bangla_khutba.R;
import org.mkab.bangla_khutba.adapter.KhutbaAdapter;
import org.mkab.bangla_khutba.model.KhutbaModel;
import org.mkab.bangla_khutba.parser.JSONParser;
import org.mkab.bangla_khutba.util.InternetConnection;
import org.mkab.bangla_khutba.util.Keys;
import org.mkab.bangla_khutba.util.SharePrefUtil;

public class KhutbaListActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<KhutbaModel> list;
    private KhutbaAdapter adapter;
    ProgressDialog dialog;
    int jIndex;
    int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.majlish_list);

        /**
         * Array List for Binding Data from JSON to this List
         */
        list = new ArrayList<>();
        /**
         * Binding that List to Adapter
         */
        adapter = new KhutbaAdapter(this, list);

        /**
         * Getting List and Setting List Adapter
         */
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent khutbaDetail = new Intent(getApplicationContext(), KhutbaDetailsActivity.class);
                khutbaDetail.putExtra(Keys.KEY_TITLE, list.get(position).getDate() + " " + list.get(position).getMonth() + ", " + list.get(position).getYear());
                khutbaDetail.putExtra(Keys.KEY_KHUTBA_DETAILS, list.get(position).getKhutba_details());
                startActivity(khutbaDetail);
            }
        });

        /**
         * Just to know onClick and Printing Hello Toast in Center.
         */
        /*Toast toast = Toast.makeText(getApplicationContext(), "Click on FloatingActionButton to Get Khutba", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();*/

        if (InternetConnection.checkConnection(getApplicationContext())) {
            new GetDataTask().execute();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabMajlish);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {

                /**
                 * Checking Internet Connection
                 */
                if (InternetConnection.checkConnection(getApplicationContext())) {
                  //  new GetDataTask().execute();
                    startActivity(new Intent(KhutbaListActivity.this, BottomViewActivity.class));
                } else {
                    getKhutbaFromJson();
                    Snackbar.make(view, "Internet Connection Not Available", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Creating Get Data Task for Getting Data From Web
     */
    class GetDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /**
             * Progress Dialog for User Interaction
             */

            x = list.size();

            if (x == 0)
                jIndex = 0;
            else
                jIndex = x;

            dialog = new ProgressDialog(KhutbaListActivity.this);
            dialog.setTitle("Loading...");
            dialog.setMessage("Getting Khutba");
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            /**
             * Getting JSON Object from Web Using okHttp
             */
            JSONObject jsonObject = JSONParser.getKhutbaDataFromWeb(getApplicationContext());

            try {
                if (jsonObject != null && jsonObject.length() > 0) {

                    JSONArray array = jsonObject.getJSONArray(Keys.KEY_KHUTBA);

                    SharePrefUtil.setSharePrefData(getApplicationContext(), "bangla_khutba", array.toString());

                    int lenArray = array.length();
                    if (lenArray > 0) {

                        for (; jIndex < lenArray; jIndex++) {

                            KhutbaModel model = new KhutbaModel();

                            JSONObject innerObject = array.getJSONObject(jIndex);


                            String year = innerObject.getString(Keys.KEY_YEAR);
                            String month = innerObject.getString(Keys.KEY_MONTH);
                            String date = innerObject.getString(Keys.KEY_DATE);
                            String title = innerObject.getString(Keys.KEY_TITLE);
                            String khutba_details = innerObject.getString(Keys.KEY_KHUTBA_DETAILS);

                            model.setYear(year);
                            model.setMonth(month);
                            model.setDate(date);
                            model.setTitle(title);
                            model.setKhutba_details(khutba_details);

                            list.add(model);
                        }
                    }
                } else {

                }
            } catch (JSONException je) {
                Log.i(JSONParser.TAG, "Ki hoise online json" + je.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            /**
             * Checking if List size if more than zero then
             * Update ListView
             */
            if (list.size() > 0) {
                adapter.notifyDataSetChanged();
            } else {
                Snackbar.make(findViewById(R.id.parentLayout), "No Data Found", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void getKhutbaFromJson() {

        dialog = new ProgressDialog(KhutbaListActivity.this);
        dialog.setTitle("Loading...");
        dialog.setMessage("Getting Khutba From Offline");
        dialog.show();

        JSONObject jsonObject = new JSONObject();
        if (SharePrefUtil.getStringValue(getApplicationContext(), "bangla_khutba") != null) {
            dialog.dismiss();

           /* boolean isFilePresent = MyJson.isFilePresent(getApplicationContext(), "storage.json");
            if (isFilePresent) {
                String jsonString = MyJson.read(getApplicationContext(), "storage.json");
                Log.d("dataJsonKhutba", jsonString);
                //do the json parsing here and do the rest of functionality of app
            }*/
            //Gson gson = new Gson();
            //SharePrefUtil.getStringValue(getApplicationContext(),"bangla_khutba")

           /* Log.d("BanglaKhutbaDetails", SharePrefUtil.getStringValue(getApplicationContext(),"bangla_khutba"));
            try {
                jsonObject.putOpt("bangla_khutba", SharePrefUtil.getStringValue(getApplicationContext(),"bangla_khutba"));
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            try {

                if (jsonObject != null) {
                    if (jsonObject.length() > 0) {
                        /**
                         * Getting Array named "contacts" From MAIN Json Object
                         */
                        //  SharePrefUtil.setSharePrefData("bangla_khutba", jsonObject.toString());

                        JSONArray array = jsonObject.getJSONArray(SharePrefUtil.getStringValue(getApplicationContext(), "bangla_khutba"));

                        /**
                         * Check Length of Array...
                         */


                        int lenArray = array.length();
                        int jIndex = 0;
                        if (lenArray > 0) {
                            for (; jIndex < lenArray; jIndex++) {

                                /**
                                 * Creating Every time New Object
                                 * and
                                 * Adding into List
                                 */
                                KhutbaModel model = new KhutbaModel();

                                /**
                                 * Getting Inner Object from contacts array...
                                 * and
                                 * From that We will get Name of that Contact
                                 *
                                 */
                                JSONObject innerObject = array.getJSONObject(jIndex);

                                String year = innerObject.getString(Keys.KEY_YEAR);
                                String month = innerObject.getString(Keys.KEY_MONTH);
                                String date = innerObject.getString(Keys.KEY_DATE);
                                String title = innerObject.getString(Keys.KEY_TITLE);
                                String khutba_details = innerObject.getString(Keys.KEY_KHUTBA_DETAILS);


//                                String image = innerObject.getString(Keys.KEY_IMAGE);
                                /**
                                 * Getting Object from Object "phone"
                                 */
                                //JSONObject phoneObject = innerObject.getJSONObject(Keys.KEY_PHONE);
                                //String phone = phoneObject.getString(Keys.KEY_MOBILE);

                                model.setYear(year);
                                model.setMonth(month);
                                model.setDate(date);
                                model.setTitle(title);
                                model.setKhutba_details(khutba_details);

                                /**
                                 * Adding name and phone concatenation in List...
                                 */
                                list.add(model);
                            }
                        }
                    }
                } else {

                }
            } catch (JSONException je) {
                Log.i(JSONParser.TAG, "Ki hoise ekhane" + je.getLocalizedMessage());
            }

        } else {
            dialog.dismiss();
        }

    }
}