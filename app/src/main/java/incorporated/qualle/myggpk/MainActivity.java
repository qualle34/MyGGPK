package incorporated.qualle.myggpk;

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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import com.qualle.myggpk.R;

import incorporated.qualle.myggpk.group.Group;
import incorporated.qualle.myggpk.group.GroupFabric;
import incorporated.qualle.myggpk.settings.AppSettings;
import incorporated.qualle.myggpk.style.ExternalStyle;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private WebView webView;
    private SwipeRefreshLayout swipe;

    private int groupId;

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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        groupId = AppSettings.getGroupId(getApplicationContext());

        swipe = findViewById(R.id.swipe);
        swipe.setProgressViewOffset(false, 70, 200);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                load(groupId);
            }
        });
        load(groupId);
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
                Intent website = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ggpk.by/index.php"));
                startActivity(website);
                break;

            case R.id.action_main:
                Intent main = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ggpk.by/"));
                startActivity(main);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.first:
                groupId = 1;
                load(groupId);
                break;

            case R.id.agb:
                groupId = 2;
                load(groupId);
                break;

            case R.id.tor:
                groupId = 3;
                load(groupId);
                break;

            case R.id.pgb:
                groupId = 4;
                load(groupId);
                break;

            case R.id.bda:
                groupId = 5;
                load(groupId);
                break;

            case R.id.vms:
                groupId = 6;
                load(groupId);
                break;

            case R.id.pzt:
                groupId = 7;
                load(groupId);
                break;

            case R.id.aep:
                groupId = 8;
                load(groupId);
                break;

            case R.id.bsk:
                groupId = 9;
                load(groupId);
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

    private void secret() {
        if (AppSettings.isVip(getApplicationContext())) {
            Toast.makeText(this, "Vip", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeLanguage() {

        if (AppSettings.getLanguageSettings(getApplicationContext()) == 1) {

            setLocale("ru");

            NavigationView navigationView = findViewById(R.id.nav_view);
            Menu menu = navigationView.getMenu();

            menu.findItem(R.id.first).setTitle("1 Курс");
            menu.findItem(R.id.agb).setTitle("АГБ");
            menu.findItem(R.id.tor).setTitle("ТОР");
            menu.findItem(R.id.pgb).setTitle("ПГБ");
            menu.findItem(R.id.bda).setTitle("БДА");
            menu.findItem(R.id.vms).setTitle("ВМС");
            menu.findItem(R.id.pzt).setTitle("ПЗТ");
            menu.findItem(R.id.aep).setTitle("АЭП");
            menu.findItem(R.id.bsk).setTitle("БСК");
            navigationView.setNavigationItemSelectedListener(this);

        } else {

            setLocale("en");

            NavigationView navigationView = findViewById(R.id.nav_view);
            Menu menu = navigationView.getMenu();

            menu.findItem(R.id.first).setTitle("First year");
            menu.findItem(R.id.agb).setTitle("AGB");
            menu.findItem(R.id.tor).setTitle("TOR");
            menu.findItem(R.id.pgb).setTitle("PGB");
            menu.findItem(R.id.bda).setTitle("BDA");
            menu.findItem(R.id.vms).setTitle("VMS");
            menu.findItem(R.id.pzt).setTitle("PZT");
            menu.findItem(R.id.aep).setTitle("AEP");
            menu.findItem(R.id.bsk).setTitle("BSK");
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

        if (AppSettings.getNightModeSettings(getApplicationContext())) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }

    private void load(String url) throws ExecutionException, InterruptedException {
        String css = getResources().getString(R.string.css);
        webView = findViewById(R.id.webView);
        webView.getSettings().setDomStorageEnabled(true);

        if (isOnline()) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

            if (AppSettings.getStyleSettings(getApplicationContext())) {
                webView.loadData(ExternalStyle.getWithStyle(getApplicationContext(),url, css, true), "text/html; charset=UTF-8", null);

            } else {
                webView.loadUrl(url);
            }

        } else {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ONLY);
            if (AppSettings.getStyleSettings(getApplicationContext())) {
                webView.loadData(ExternalStyle.getWithStyle(getApplicationContext(),url, css, false), "text/html; charset=UTF-8", null);
            } else {
                webView.loadUrl(url);
            }
        }

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

    private void load(int id) {
        Group group = GroupFabric.getGroup(id);
        try {
            load(group.getUrl());
        } catch (ExecutionException | InterruptedException ignore) {
        }
    }
}
