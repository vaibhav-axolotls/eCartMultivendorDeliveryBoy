package com.axolotls.prachetadboy.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import com.axolotls.prachetadboy.R;
import com.axolotls.prachetadboy.helper.Constant;
import com.axolotls.prachetadboy.helper.Session;

public class DrawerActivity extends AppCompatActivity {
    public static TextView tvName, tvWallet;
    public NavigationView navigationView;
    public DrawerLayout drawer;
    public ActionBarDrawerToggle drawerToggle;
    public TextView tvMobile;
    protected FrameLayout frameLayout;
    Session session;
    LinearLayout lytProfile;
    LinearLayout lytWallet;
    Activity activity;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ApiConfig.transparentStatusAndNavigation(DrawerActivity.this);
        setContentView(R.layout.activity_drawer);

        frameLayout = findViewById(R.id.content_frame);
        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);
        View header = navigationView.getHeaderView(0);
        lytWallet = header.findViewById(R.id.lytWallet);
        tvWallet = header.findViewById(R.id.tvWallet);
        tvName = header.findViewById(R.id.header_name);
        tvMobile = header.findViewById(R.id.tvMobile);
        lytProfile = header.findViewById(R.id.lytProfile);

        activity = DrawerActivity.this;

        session = new Session(activity);

        if (session.isUserLoggedIn()) {
            tvName.setText(session.getData(Constant.NAME));
            tvMobile.setText(session.getData(Constant.MOBILE));
            lytWallet.setVisibility(View.VISIBLE);
            tvWallet.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wallet_white, 0, 0, 0);
            tvWallet.setText(getString(R.string.wallet_balance) + "\t:\t" + new Session(activity).getData(Constant.CURRENCY) + Constant.formatter.format(Double.parseDouble(session.getData(Constant.BALANCE))));

        } else {
            lytWallet.setVisibility(View.GONE);
            tvName.setText(getResources().getString(R.string.is_login));
        }

        lytProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });
        setupNavigationDrawer();
    }


    private void setupNavigationDrawer() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawer.closeDrawers();

                switch (menuItem.getItemId()) {
                    case R.id.menu_home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        break;
                    case R.id.menu_notifications:
                        startActivity(new Intent(getApplicationContext(), NotificationListActivity.class));
                        break;
                    case R.id.menu_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        break;
                    case R.id.menu_wallet_history:
                        startActivity(new Intent(getApplicationContext(), WalletHistoryActivity.class));
                        break;
                    case R.id.menu_policy:
                        Intent policy = new Intent(DrawerActivity.this, WebViewActivity.class);
                        policy.putExtra("title", getString(R.string.privacy_policy));
                        policy.putExtra("link", Constant.DELIVERY_BOY_POLICY);
                        startActivity(policy);
                        break;
                    case R.id.menu_terms:
                        Intent terms = new Intent(DrawerActivity.this, WebViewActivity.class);
                        terms.putExtra("title", getString(R.string.terms_conditions));
                        terms.putExtra("link", Constant.DELIVERY_BOY_TERMS);
                        startActivity(terms);
                        break;
                    case R.id.menu_logout:
                        session.logoutUserConfirmation(activity);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }
}
