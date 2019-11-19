package org.mkab.bangla_khutba.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.mkab.bangla_khutba.R;
import org.mkab.bangla_khutba.fragment.AccountFragment;
import org.mkab.bangla_khutba.fragment.CategoryFragment;
import org.mkab.bangla_khutba.fragment.HomeFragment;
import org.mkab.bangla_khutba.fragment.ServiceFragment;

import java.lang.reflect.Field;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sopan Ahmed on 18-Feb-18.
 */

public class BottomViewActivity extends AppCompatActivity {

    @BindView(R.id.bottom_view)
    BottomNavigationView mBottomNavigationView;
   /* @BindView(R.id.toolbar)
    Toolbar mToolbar;*/

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_view);
        ButterKnife.bind(this);

        //Set Toolbar
       // getSupportActionBar().setTitle(getString(R.string.menu_home));
       // getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        /**
         * Disable shift method require for to prevent shifting icon.
         * When you select any icon then remain all icon shift
         * @param view
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            disableShiftMode(mBottomNavigationView);
        }

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragCategory = null;
                // init corresponding fragment
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        fragCategory = new HomeFragment();
                        break;
                    case R.id.menu_categories:
                        fragCategory = new CategoryFragment();
                        break;
                    case R.id.menu_services:
                        fragCategory = new ServiceFragment();
                        break;
                    case R.id.menu_account:
                        fragCategory = new AccountFragment();
                        break;
                }
                //Set bottom menu selected item text in toolbar
                /*ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle(item.getTitle());
                }*/
               // getSupportActionBar().setTitle(item.getTitle());
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragCategory);
                transaction.commit();
                return true;
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new HomeFragment());
        transaction.commit();
    }


    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShifting(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
        }
    }
}
