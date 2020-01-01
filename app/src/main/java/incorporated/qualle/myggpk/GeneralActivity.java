package incorporated.qualle.myggpk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class GeneralActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    WebView webView;
    SwipeRefreshLayout swipe;
    ImageView DarkImage;

    String GroupURL;
    String URL;

    String ThemeInf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DarkImage = findViewById(R.id.image_dark_mode);

        if (NightModeListener()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            DarkImage.setVisibility(View.GONE);
        } else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        GroupURL = sharedPreferences.getString("pref_group_list", " ");
        URL = GroupURL;

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        swipe = findViewById(R.id.swipe);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                LoadWeb();

            }
        });

        LoadWeb();
    }

    public boolean NightModeListener() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        StringBuilder ThemeInfo = new StringBuilder();
        ThemeInfo.append(sharedPreferences.getString("pref_night_mode_list", "1"));
        ThemeInf = ThemeInfo.toString();

        if (ThemeInf.equals("1")) {
            return true;

        } else {
            return false;
        }
    }

    protected boolean CheckOnlineListener() {

        String SysService = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(SysService);

        if (cm.getActiveNetworkInfo() == null) {
            return false;

        } else {
            return true;
        }
    }

    public void NoInternet() {

        if (!CheckOnlineListener()) {

            webView.loadUrl("file:///android_assets/general_no_internet.html");
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    public void LoadWeb() {    // Загрузка страниц

        webView = findViewById(R.id.webView);

        if (CheckOnlineListener()) {

            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else if (!CheckOnlineListener()) {

            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(URL);


        swipe.setRefreshing(true);
        webView.setWebViewClient(new WebViewClient() {

            public void onReceivedError(WebView view, int errorCode, String description, String failingURL) {

                webView.loadUrl("file:///android_asset/general_no_internet.html");
            }

            public void onPageFinished(WebView view, String url) {

                swipe.setRefreshing(false);
            }

        });

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
    public boolean onCreateOptionsMenu(Menu menu) {  // Добавление элементов в панель действий

        getMenuInflater().inflate(R.menu.general, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Нажмите здесь пункт «Действия».
        // Панель действий будетавтоматически обрабатывать клики на кнопке Home / Up,
        // так долго как вы указываете родительскую активность в AndroidManifest.xml.

        int id = item.getItemId();
        // Слушатель для кнопок в панели действий

        if (id == R.id.action_settings) {

            Intent settings = new Intent(this, SettingsActivity.class);
            startActivity(settings);

            return true;

        } else if (id == R.id.action_developer) {

            Intent developer = new Intent(this, DeveloperActivity.class);
            startActivity(developer);

            return true;

        } else if (id == R.id.action_website) {

            Intent Website = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ggpk.by/index.php"));
            startActivity(Website);
            return true;

        } else if (id == R.id.action_refresh) {

            LoadWeb();
            webView.pageUp(true);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) { // Слушатель для кнопок в боковом меню

        int id = item.getItemId();

        if (id == R.id.first_k) {

            URL = "http://www.ggpk.by/Raspisanie/Files/P_KURS.html";
            LoadWeb();
            NoInternet();

        } else if (id == R.id.AGB) {

            URL = "http://www.ggpk.by/Raspisanie/Files/AGB.html";
            LoadWeb();
            NoInternet();

        } else if (id == R.id.TAR) {

            URL = "http://www.ggpk.by/Raspisanie/Files/TAR.html";
            LoadWeb();
            NoInternet();

        } else if (id == R.id.PGB) {

            URL = "http://www.ggpk.by/Raspisanie/Files/PGB.html";
            LoadWeb();
            NoInternet();

        } else if (id == R.id.BDA) {

            URL = "http://www.ggpk.by/Raspisanie/Files/BDA.html";
            LoadWeb();
            NoInternet();

        } else if (id == R.id.VMS) {

            URL = "http://www.ggpk.by/Raspisanie/Files/VMS.html";
            LoadWeb();
            NoInternet();

        } else if (id == R.id.PZT) {

            URL = "http://www.ggpk.by/Raspisanie/Files/PZT.html";
            LoadWeb();
            NoInternet();

        } else if (id == R.id.AEP) {

            URL = "http://www.ggpk.by/Raspisanie/Files/AEP.html";
            LoadWeb();
            NoInternet();

        } else if (id == R.id.BSK) {

            URL = "http://www.ggpk.by/Raspisanie/Files/BSB.html";
            LoadWeb();
            NoInternet();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
