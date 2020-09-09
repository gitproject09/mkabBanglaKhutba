package org.mkab.bangla_khutba.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mkab.bangla_khutba.R;
import org.mkab.bangla_khutba.activity.PostDetailActivity;
import org.mkab.bangla_khutba.adapter.AhbanAdapter;
import org.mkab.bangla_khutba.adapter.MajlishAdapter;
import org.mkab.bangla_khutba.model.AhbanModel;
import org.mkab.bangla_khutba.model.MajlishModel;
import org.mkab.bangla_khutba.parser.JSONParser;
import org.mkab.bangla_khutba.util.InternetConnection;
import org.mkab.bangla_khutba.util.Keys;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;


/**
 * Created by Sopan Ahmed on 18-Feb-18.
 */

public class CategoryFragment extends Fragment {

    private ListView listView;
    private TextView tvSearchCount;
    private ArrayList<AhbanModel> list;
    //private AhbanAdapter adapter;
    private MyAppAdapter myAppAdapter;

    public List<AhbanModel> parkingList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.ahban_fragment, container, false);

        setHasOptionsMenu(true);

        /**
         * Array List for Binding Data from JSON to this List
         */
        list = new ArrayList<>();
        parkingList = new ArrayList<>();
        listView = rootView.findViewById(R.id.listView);
        tvSearchCount = rootView.findViewById(R.id.tvSearchCount);
        /**
         * Binding that List to Adapter
         */
        //adapter = new AhbanAdapter(getActivity(), list);

        myAppAdapter = new MyAppAdapter(list, getActivity());
        listView.setAdapter(myAppAdapter);

        /**
         * Getting List and Setting List Adapter
         */

       /* listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               *//* Snackbar.make(rootView.findViewById(R.id.parentLayout), list.get(position).getRegion()
                        + " => " + list.get(position).getDistrict() + " => " + list.get(position).getMobileNo(), Snackbar.LENGTH_LONG).show();

                Toast toast = Toast.makeText(getActivity(), list.get(position).getRegion()
                        + " => " + list.get(position).getDistrict() + " => " + list.get(position).getMajlishName()
                        + " => " + list.get(position).getName() + " => " + list.get(position).getAddress(),
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();*//*

                Intent intent = new Intent(getActivity(), PostDetailActivity.class);

                intent.putExtra(Keys.KEY_REGION, list.get(position).getRegion());
                intent.putExtra(Keys.KEY_DISTRICT, list.get(position).getDistrict());
                intent.putExtra(Keys.KEY_MAJLISH_NAME, list.get(position).getMajlishName());

                intent.putExtra(Keys.KEY_A_NAME, list.get(position).getName());
                intent.putExtra(Keys.KEY_A_ADDRESS, list.get(position).getAddress());
                intent.putExtra(Keys.KEY_A_EMAIL, list.get(position).getEmail());
                intent.putExtra(Keys.KEY_A_MOBILE_NO, list.get(position).getMobileNo());

                intent.putExtra(Keys.KEY_A_CHANDA, list.get(position).getChanda());
                intent.putExtra(Keys.KEY_A_BOOK_NO, list.get(position).getBookNo());
                intent.putExtra(Keys.KEY_A_ROSHID_NO, list.get(position).getRoshidNo());

                intent.putExtra(Keys.KEY_A_ADD_DATE, list.get(position).getAddDate());
                intent.putExtra(Keys.KEY_A_EXPIRE_DATE, list.get(position).getExpireDate());
                intent.putExtra(Keys.KEY_A_COMMENT, list.get(position).getComment());
                startActivity(intent);
            }
        });*/

        if (InternetConnection.checkConnection(getActivity())) {
            new GetDataTask().execute();
        } else {
            Toast toast = Toast.makeText(getActivity(), "Internet Connection Not Available", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //*** setOnQueryTextFocusChangeListener ***
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchQuery) {
                Log.e("qaidIndex", "onQueryTextChange : " + searchQuery);
                //if(!searchQuery.equals("")){
                myAppAdapter.filter(searchQuery.trim());
                listView.invalidate();
                //}

                return true;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }/* else if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getActivity(), SignInActivity.class));
            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            getActivity().finish();
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }


    public class MyAppAdapter extends BaseAdapter {

        public class ViewHolder {
            TextView txtTitle, tvExpireDate;
            ImageView imgAuthor;
            ImageView rlEdit;
        }

        public List<AhbanModel> parkingList;

        public Context context;
        ArrayList<AhbanModel> arraylist;

        private MyAppAdapter(List<AhbanModel> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
            arraylist = new ArrayList<AhbanModel>();
            arraylist.addAll(parkingList);

        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;
            ViewHolder viewHolder;

            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.layout_row_view, null);
                //rowView = inflater.inflate(R.layout.layout_row_view, null, false);
               // View view = inflater.inflate(R.layout.layout_row_view, parent, false);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.txtTitle = (TextView) rowView.findViewById(R.id.textViewName);
                viewHolder.tvExpireDate = (TextView) rowView.findViewById(R.id.textViewCountry);
                viewHolder.rlEdit = (ImageView) rowView.findViewById(R.id.ivShare);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.txtTitle.setText(parkingList.get(position).getName() + "\n" + parkingList.get(position).getMajlishName()
                    + ", " + parkingList.get(position).getDistrict() + ", " + parkingList.get(position).getRegion());


            if (!parkingList.get(position).getExpireDate().isEmpty()) {

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
                dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

                String date = dateFormat.format(Calendar.getInstance().getTime());

                String[] englishDayMonthYear = date.split("T");
                String[] currentDate = englishDayMonthYear[0].split("-");

                String currentYear = currentDate[0];
                String currentMonth = currentDate[1];
                String currentDay = currentDate[2];

                Log.d("DateCheck", "Current Day in int : " + Integer.valueOf(currentDay));

                String[] exDate = parkingList.get(position).getExpireDate().split("-");
                String exDay = exDate[0];
                String exMonth = exDate[1];
                String exYear = exDate[2];

                Log.d("DateCheck", "Expire Day in int : " + Integer.valueOf(exDay));


                if (Integer.valueOf(currentYear).equals(Integer.valueOf(exYear))) { // 2020 hole dhukbe
                    Log.d("DateCheck", "Condition true Year : " + exYear);
                    if (Integer.valueOf(currentMonth).equals(Integer.valueOf(exMonth))) {
                        Log.d("DateCheck", "Condition true Month : " + exMonth);
                        if (Integer.valueOf(currentDay).equals(Integer.valueOf(exDay))) {
                            viewHolder.tvExpireDate.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
                            viewHolder.tvExpireDate.setText("Already Expired on: " + parkingList.get(position).getExpireDate());
                        } else if (Integer.valueOf(currentDay) < Integer.valueOf(exDay)){
                            Log.d("DateCheck", "Condition true Day : " + exMonth);
                            viewHolder.tvExpireDate.setTextColor(context.getResources().getColor(android.R.color.holo_green_light));
                            viewHolder.tvExpireDate.setText("Expire on: " + parkingList.get(position).getExpireDate());
                        } else {
                            viewHolder.tvExpireDate.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
                            viewHolder.tvExpireDate.setText("Already Expired on: " + parkingList.get(position).getExpireDate());
                        }
                    } else if (Integer.valueOf(currentMonth) < Integer.valueOf(exMonth)) {
                        viewHolder.tvExpireDate.setTextColor(context.getResources().getColor(android.R.color.holo_green_light));
                        viewHolder.tvExpireDate.setText("Expire on: " + parkingList.get(position).getExpireDate());
                    }  else {
                        viewHolder.tvExpireDate.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
                        viewHolder.tvExpireDate.setText("Already Expired on: " + parkingList.get(position).getExpireDate());
                    }
                } else if(Integer.valueOf(currentYear) < Integer.valueOf(exYear)) {
                    viewHolder.tvExpireDate.setTextColor(context.getResources().getColor(android.R.color.holo_green_light));
                    viewHolder.tvExpireDate.setText("Expire on: " + parkingList.get(position).getExpireDate());
                } else {
                    viewHolder.tvExpireDate.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
                    viewHolder.tvExpireDate.setText("Already Expired on: " + parkingList.get(position).getExpireDate());
                }

            }


            /*String titleText = "";

            if (parkingList.get(position).getName().trim().contains(" ")) {
                String splitValue[] = parkingList.get(position).getName().trim().split(" ");

                String firstLetter = splitValue[0].toUpperCase();
                String secondLetter = splitValue[1].toUpperCase();
                titleText = firstLetter.charAt(0) + "" + secondLetter.charAt(0);
            } else {
                titleText = String.valueOf(parkingList.get(position).getName().toUpperCase().charAt(0));
            }

            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT

            int color = generator.getRandomColor();

            TextDrawable textDrawable = TextDrawable.builder()
                    .buildRound(titleText, color); // radius in px

            Picasso.get()
                    .load(BASE_IMG_URL + parkingList.get(position).author)
                    .transform(new CropCircleTransformation())
                    //.networkPolicy(NetworkPolicy.OFFLINE)
                    .resize(100, 100)
                    .centerCrop()
                    .networkPolicy(Utils.isNetworkAvailable(mActivity) ? NetworkPolicy.NO_CACHE : NetworkPolicy.OFFLINE)
                    .placeholder(R.drawable.ic_default_profile)
                    .error(textDrawable)
                    .into(viewHolder.imgAuthor);*/

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), PostDetailActivity.class);

                    intent.putExtra(Keys.KEY_REGION, parkingList.get(position).getRegion());
                    intent.putExtra(Keys.KEY_DISTRICT, parkingList.get(position).getDistrict());
                    intent.putExtra(Keys.KEY_MAJLISH_NAME, parkingList.get(position).getMajlishName());

                    intent.putExtra(Keys.KEY_A_NAME, parkingList.get(position).getName());
                    intent.putExtra(Keys.KEY_A_ADDRESS, parkingList.get(position).getAddress());
                    intent.putExtra(Keys.KEY_A_EMAIL, parkingList.get(position).getEmail());
                    intent.putExtra(Keys.KEY_A_MOBILE_NO, parkingList.get(position).getMobileNo());

                    intent.putExtra(Keys.KEY_A_CHANDA, parkingList.get(position).getChanda());
                    intent.putExtra(Keys.KEY_A_BOOK_NO, parkingList.get(position).getBookNo());
                    intent.putExtra(Keys.KEY_A_ROSHID_NO, parkingList.get(position).getRoshidNo());

                    intent.putExtra(Keys.KEY_A_ADD_DATE, parkingList.get(position).getAddDate());
                    intent.putExtra(Keys.KEY_A_EXPIRE_DATE, parkingList.get(position).getExpireDate());
                    intent.putExtra(Keys.KEY_A_COMMENT, parkingList.get(position).getComment());
                    startActivity(intent);
                }
            });


            return rowView;

        }

        public void filter(String charText) {

            charText = charText.toLowerCase(Locale.getDefault());

            parkingList.clear();
            if (charText.length() == 0) {
                parkingList.addAll(arraylist);
                tvSearchCount.setText("Total : " + parkingList.size());

            } else {
                for (AhbanModel postDetail : arraylist) {
                    if (charText.length() != 0 && postDetail.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                        parkingList.add(postDetail);
                    } else if (charText.length() != 0 && postDetail.getMobileNo().toLowerCase(Locale.getDefault()).contains(charText)) {
                        parkingList.add(postDetail);
                    } else if (charText.length() != 0 && postDetail.getMajlishName().toLowerCase(Locale.getDefault()).contains(charText)) {
                        parkingList.add(postDetail);
                    }
                }
                tvSearchCount.setText("Total : " + parkingList.size());
            }
            notifyDataSetChanged();
        }
    }


    /**
     * Creating Get Data Task for Getting Data From Web
     */
    class GetDataTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;

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

            dialog = new ProgressDialog(getActivity());
            dialog.setTitle("Please wait...");
            dialog.setMessage("Getting Ahban Users");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {

            /**
             * Getting JSON Object from Web Using okHttp
             */
            JSONObject jsonObject = JSONParser.getAhbanDataFromWeb();

            try {
                /**
                 * Check Whether Its NULL???
                 */
                if (jsonObject != null) {
                    /**
                     * Check Length...
                     */
                    if (jsonObject.length() > 0) {
                        /**
                         * Getting Array named "contacts" From MAIN Json Object
                         */
                        JSONArray array = jsonObject.getJSONArray(Keys.KEY_MAIN_AHBAN);

                        /**
                         * Check Length of Array...
                         */


                        int lenArray = array.length();
                        if (lenArray > 0) {
                            for (; jIndex < lenArray; jIndex++) {

                                /**
                                 * Creating Every time New Object
                                 * and
                                 * Adding into List
                                 */
                                AhbanModel model = new AhbanModel();

                                /**
                                 * Getting Inner Object from contacts array...
                                 * and
                                 * From that We will get Name of that Contact
                                 *
                                 */
                                JSONObject innerObject = array.getJSONObject(jIndex);

                                String region = innerObject.getString(Keys.KEY_REGION);
                                String district = innerObject.getString(Keys.KEY_DISTRICT);
                                String majlishName = innerObject.getString(Keys.KEY_MAJLISH_NAME);
                                String name = innerObject.getString(Keys.KEY_A_NAME);
                                String address = innerObject.getString(Keys.KEY_A_ADDRESS);
                                String email = innerObject.getString(Keys.KEY_A_EMAIL);
                                String mobileNo = innerObject.getString(Keys.KEY_A_MOBILE_NO);
                                String chanda = innerObject.getString(Keys.KEY_A_CHANDA);
                                String bookNo = innerObject.getString(Keys.KEY_A_BOOK_NO);
                                String roshidNo = innerObject.getString(Keys.KEY_A_ROSHID_NO);
                                String addDate = innerObject.getString(Keys.KEY_A_ADD_DATE);
                                String expireDate = innerObject.getString(Keys.KEY_A_EXPIRE_DATE);
                                String comment = innerObject.getString(Keys.KEY_A_COMMENT);


//                                String image = innerObject.getString(Keys.KEY_IMAGE);
                                /**
                                 * Getting Object from Object "phone"
                                 */
                                //JSONObject phoneObject = innerObject.getJSONObject(Keys.KEY_PHONE);
                                //String phone = phoneObject.getString(Keys.KEY_MOBILE);

                                model.setRegion(region);
                                model.setDistrict(district);
                                model.setMajlishName(majlishName);
                                model.setName(name);
                                model.setAddress(address);
                                model.setEmail(email);
                                model.setMobileNo(mobileNo);
                                model.setChanda(chanda);
                                model.setBookNo(bookNo);
                                model.setRoshidNo(roshidNo);
                                model.setAddDate(addDate);
                                model.setExpireDate(expireDate);
                                model.setComment(comment);

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

                myAppAdapter = new MyAppAdapter(list, getActivity());
                listView.setAdapter(myAppAdapter);
                tvSearchCount.setText("Total : " + list.size());
            } else {
                Snackbar.make(getView().findViewById(R.id.parentLayout), "No Data Found", Snackbar.LENGTH_LONG).show();
            }
        }

    }
}
