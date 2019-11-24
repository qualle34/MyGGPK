package com.qualle.myggpk;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.qualle.myggpk.group.Group;
import com.qualle.myggpk.group.GroupFabric;
import com.qualle.myggpk.settings.AppSettings;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private WebView webView;
    private SwipeRefreshLayout swipe;

    private int groupId;
    private boolean isMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firstRun();
        secret();
        changeLanguage();
        changeTheme();
        createTabLayout();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        groupId = AppSettings.getGroupId(getApplicationContext());

        swipe = findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                load(groupId, isMain);
            }
        });
        load(groupId, isMain);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_settings:
                Intent settings = new Intent(this, SettingsActivity.class);
                startActivity(settings);
                break;

            case R.id.action_developer:
                Intent developer = new Intent(this, DeveloperActivity.class);
                startActivity(developer);
                break;

            case R.id.action_website:
                Intent Website = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ggpk.by/index.php"));
                startActivity(Website);
                break;

            case R.id.action_refresh:
                load(groupId, isMain);
                webView.pageUp(true);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.first_k:
                groupId = 1;
                load(groupId, false);
                break;

            case R.id.AGB:
                groupId = 2;
                load(groupId, false);
                break;

            case R.id.TAR:
                groupId = 3;
                load(groupId, false);
                break;

            case R.id.PGB:
                groupId = 4;
                load(groupId, false);
                break;

            case R.id.BDA:
                groupId = 5;
                load(groupId, false);
                break;

            case R.id.VMS:
                groupId = 6;
                load(groupId, false);
                break;

            case R.id.PZT:
                groupId = 7;
                load(groupId, false);
                break;

            case R.id.AEP:
                groupId = 8;
                load(groupId, false);
                break;

            case R.id.BSK:
                groupId = 9;
                load(groupId, false);
                break;

            default:
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void firstRun() {
        boolean isFirstRun = getSharedPreferences("FIRST_RUN", MODE_PRIVATE).getBoolean("is_first_run", true);

        if (isFirstRun) {
            Toast.makeText(this, "Выбери свою группу в настройках", Toast.LENGTH_LONG).show();
            getSharedPreferences("FIRST_RUN", MODE_PRIVATE).edit().putBoolean("is_first_run", false).apply();
        }
    }

    private void secret() { // test
        if (AppSettings.isVip(getApplicationContext())) {
            Toast.makeText(this, "Vip", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeLanguage() {

        if (AppSettings.getLanguageSettings(getApplicationContext()) == 1) {

            setLocale("ru");

            NavigationView navigationView = findViewById(R.id.nav_view);
            Menu menu = navigationView.getMenu();

            menu.findItem(R.id.side_title_schedule).setTitle("Рассписание");
            menu.findItem(R.id.first_k).setTitle("1 Курс");
            menu.findItem(R.id.AGB).setTitle("АГБ");
            menu.findItem(R.id.TAR).setTitle("ТОР");
            menu.findItem(R.id.PGB).setTitle("ПГБ");
            menu.findItem(R.id.BDA).setTitle("БДА");
            menu.findItem(R.id.VMS).setTitle("ВМС");
            menu.findItem(R.id.PZT).setTitle("ПЗТ");
            menu.findItem(R.id.AEP).setTitle("АЭП");
            menu.findItem(R.id.BSK).setTitle("БСК");
            navigationView.setNavigationItemSelectedListener(this);

        } else {

            setLocale("en");

            NavigationView navigationView = findViewById(R.id.nav_view);
            Menu menu = navigationView.getMenu();

            menu.findItem(R.id.side_title_schedule).setTitle("Schedule");
            menu.findItem(R.id.first_k).setTitle("First year");
            menu.findItem(R.id.AGB).setTitle("AGB");
            menu.findItem(R.id.TAR).setTitle("TOR");
            menu.findItem(R.id.PGB).setTitle("PGB");
            menu.findItem(R.id.BDA).setTitle("BDA");
            menu.findItem(R.id.VMS).setTitle("VMS");
            menu.findItem(R.id.PZT).setTitle("PZT");
            menu.findItem(R.id.AEP).setTitle("AEP");
            menu.findItem(R.id.BSK).setTitle("BSK");
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    private void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    private void changeTheme() {
        ImageView darkImage = findViewById(R.id.image_dark_mode);

        if (AppSettings.getNightModeSettings(getApplicationContext())) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            darkImage.setVisibility(View.GONE);
        }
    }

    private void createTabLayout() {
        final TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.home));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.main_tab_title)));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tabLayout.getSelectedTabPosition() == 0) {
                    isMain = false;
                    load(groupId, false);

                } else if (tabLayout.getSelectedTabPosition() == 1) {
                    isMain = true;
                    load(groupId, true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }

    private void load(String url) {
        webView = findViewById(R.id.webView);

        if (isOnline()) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        } else {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }

        webView.getSettings().setDomStorageEnabled(true);

        webView.loadUrl(url);

        swipe.setRefreshing(true);
        webView.setWebViewClient(new WebViewClient() {

            public void onReceivedError(WebView view, int errorCode, String description, String failingURL) {
                webView.loadUrl("file:///android_asset/offline.html");
            }

            public void onPageFinished(WebView view, String url) {
                swipe.setRefreshing(false);
            }
        });
    }

    private void load(int id, boolean isMain) {
        Group group = GroupFabric.getGroup(id);

        if (isMain) {
            load(group.getMainUrl());
        } else {
            load(group.getUrl());
        }
    }
}
