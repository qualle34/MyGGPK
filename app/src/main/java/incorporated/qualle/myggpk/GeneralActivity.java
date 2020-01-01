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
import android.support.design.widget.BottomNavigationView;
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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class GeneralActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    WebView webView;
    SwipeRefreshLayout swipe;

    String GroupURL;
    String URL;
    String ButtonURL;

    String ThemeInf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (NightModeListener()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        } else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        GroupURL = sharedPreferences.getString("general_screen_group", " ");
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

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_now:
//
//                    URL = GroupURL;
//
//                    return true;
//                case R.id.navigation_general:
//
//                    if (URL.equals("http://www.ggpk.by/Raspisanie/Files/TAR.html")) {
//
//                        URL = "http://www.ggpk.by/Raspisanie/Files/MRSO.html";
//                        return true;
//
//                    } else if (URL.equals("http://www.ggpk.by/Raspisanie/Files/P_KURS.html")) {
//
//                        URL = "http://www.ggpk.by/Raspisanie/Files/PGS.html";
//                        return true;
//
//                    } else if (URL.equals("http://www.ggpk.by/Raspisanie/Files/BDA.html") &&
//                               URL.equals("http://www.ggpk.by/Raspisanie/Files/PGB.html")) {
//
//                        URL = "http://www.ggpk.by/Raspisanie/Files/PGS.html";
//                        return true;
//
//                    } else if (URL.equals("http://www.ggpk.by/Raspisanie/Files/VMS.html") &&
//                               URL.equals("http://www.ggpk.by/Raspisanie/Files/PZT.html") &&
//                               URL.equals("http://www.ggpk.by/Raspisanie/Files/AEP.html") &&
//                               URL.equals("http://www.ggpk.by/Raspisanie/Files/BSB.html")) {
//
//                        URL = "http://www.ggpk.by/Raspisanie/Files/PGS.html";
//                        return true;
//
//                    } else if (URL.equals("http://www.ggpk.by/Raspisanie/Files/AGB.html")) {
//
//                        URL = "http://www.ggpk.by/Raspisanie/Files/PTOO.html";
//                        return true;
//
//                    }
//                    return true;
//            }
//            return false;
//        }
//    };

    public boolean NightModeListener() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        StringBuilder ThemeInfo = new StringBuilder();
        ThemeInfo.append(sharedPreferences.getString("general_screen_theme", "1"));
        ThemeInf = ThemeInfo.toString();

        if (ThemeInf.equals("1")) {
            return true;
        } else
            return false;
    }

    protected boolean isOnline() {

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

        if (!isOnline()) {

            webView.loadUrl("file:///android_asset/no_internet.html");
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    public void LoadWeb() {    // load url

        webView = findViewById(R.id.webView);

        if (isOnline()) {

            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        } else if (!isOnline()) {

            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        }

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(URL);

        swipe.setRefreshing(true);
        webView.setWebViewClient(new WebViewClient() {

            public void onReceivedError(WebView view, int errorCode, String description, String failingURL) {

                webView.loadUrl("file:///android_asset/no_internet.html");

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.general, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent Settings = new Intent(this, SettingsActivity.class);
            startActivity(Settings);
            return true;

        } else if (id == R.id.action_developer) {
            Intent Settings = new Intent(this, DeveloperActivity.class);
            startActivity(Settings);
            return true;

        } else if (id == R.id.WebSite) {
            Intent GgpkWebsite = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ggpk.by/index.php"));
            startActivity(GgpkWebsite);
            return true;

        } else if (id == R.id.action_refresh) {

            LoadWeb();
            webView.pageUp(true);

            if (isOnline()) {

                webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

            } else if (!isOnline()) {

                webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ONLY);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

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
