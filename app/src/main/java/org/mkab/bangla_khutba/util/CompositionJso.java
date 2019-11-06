package org.mkab.bangla_khutba.util;

import org.json.JSONException;
import org.json.JSONObject;

public class CompositionJso extends JSONObject {

    public JSONObject makeJSONObject(String year, String month, String date, String title, String khutba_details) {

        JSONObject obj = new JSONObject();

        try {

            obj.put(Keys.KEY_YEAR, year);
            obj.put(Keys.KEY_MONTH, month);
            obj.put(Keys.KEY_DATE, date);
            obj.put(Keys.KEY_TITLE, title);
            obj.put(Keys.KEY_KHUTBA_DETAILS, khutba_details);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
    }
}
