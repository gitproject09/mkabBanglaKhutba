package org.mkab.bangla_khutba.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import org.mkab.bangla_khutba.R;
import org.mkab.bangla_khutba.util.Keys;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class KhutbaDetailsActivity extends AppCompatActivity {

    private TextView tvKhutbaDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_khutba_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        tvKhutbaDetails = findViewById(R.id.fullscreen_content);

        if (getIntent().getExtras() != null) {

            // tvKhutbaDetails.setText(getIntent().getStringExtra(Keys.KEY_KHUTBA_DETAILS));
            getSupportActionBar().setTitle("Khutba : " + getIntent().getStringExtra(Keys.KEY_TITLE));
            tvKhutbaDetails.setText(Html.fromHtml(getIntent().getStringExtra(Keys.KEY_KHUTBA_DETAILS)));
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
