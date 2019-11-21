package org.mkab.bangla_khutba.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.mkab.bangla_khutba.R;
import org.mkab.bangla_khutba.util.Keys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ScrollingActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbar;
    private TextView tvKhutbaDetails;
    private TextView tvKhutbaTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collapse_khutba_details);

        tvKhutbaDetails = findViewById(R.id.fullscreen_content);
        tvKhutbaTitle = findViewById(R.id.tvKhutbaTitle);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar = findViewById(R.id.collapsing_toolbar);

        if (getIntent().getExtras() != null) {

            // tvKhutbaDetails.setText(getIntent().getStringExtra(Keys.KEY_KHUTBA_DETAILS));
            tvKhutbaDetails.setText(Html.fromHtml(getIntent().getStringExtra(Keys.KEY_KHUTBA_DETAILS)));
            collapsingToolbar.setTitle(getIntent().getStringExtra(Keys.KEY_TITLE));
            collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.off_white));
            collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.collapse_color));
            tvKhutbaTitle.setText("Friday Sermon : "+getIntent().getStringExtra(Keys.KEY_TITLE));
        }

       // loadBackdrop();

        findViewById(R.id.fabShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sIntent = new Intent(android.content.Intent.ACTION_SEND);
                sIntent.setType("text/plain");
                sIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Friday Sermon : "+getIntent().getStringExtra(Keys.KEY_TITLE));
                sIntent.putExtra(android.content.Intent.EXTRA_TEXT, getIntent().getStringExtra(Keys.KEY_KHUTBA_DETAILS));
                startActivity(Intent.createChooser(sIntent, "Share Khutba using..."));
            }
        });
    }

    private void loadBackdrop() {
        final ImageView imageView = findViewById(R.id.backdrop);
        Glide.with(this).load(R.drawable.mkab_transparent).apply(RequestOptions.centerCropTransform()).into(imageView);
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
