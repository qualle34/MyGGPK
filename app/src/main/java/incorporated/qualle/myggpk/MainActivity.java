package incorporated.qualle.myggpk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private WebView webView;
    private SwipeRefreshLayout swipe;
    ImageView DarkImage;

    private String GroupURL;
    private String URL;
    static int TypeURL;
    private String MainURL;

    String ThemeInf;
    String LanguageInf;
    String DayTab;
    private String MainTab;

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_main);

        Boolean isFirstRun = getSharedPreferences("FIRST_RUN", MODE_PRIVATE).getBoolean("isfirstrun", true);

        if (isFirstRun) { // Первый запуск
            Toast.makeText(this, "Выбери свою группу в настройках", Toast.LENGTH_LONG).show();

            getSharedPreferences("FIRST_RUN", MODE_PRIVATE).edit().putBoolean("isfirstrun", false).commit();
        }

        NewTheme(); // Пасхалочка
        LanguageChanger();  // Смена языка

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabs);     // Добавление вкладок
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.general_home));
        tabLayout.addTab(tabLayout.newTab().setText(MainTab));
        TabsChanger();  // Смена вкладок

        DarkImage = findViewById(R.id.image_dark_mode);

        if (NightModeListener()) {    // Ночь
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            DarkImage.setVisibility(View.GONE);

        } else {                      // День
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

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

    public void NewTheme() {
        Boolean PasswordOne;
        Boolean PasswordTwo;
        Boolean PasswordThree;

        PasswordOne = getSharedPreferences("PASSWORD_ONE", MODE_PRIVATE).getBoolean("password_one", false);
        PasswordTwo = getSharedPreferences("PASSWORD_TWO", MODE_PRIVATE).getBoolean("password_two", false);
        PasswordThree = getSharedPreferences("PASSWORD_THREE", MODE_PRIVATE).getBoolean("password_three", false);

        if (PasswordOne) {
            Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
        }
        if (PasswordTwo) {
            Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
        }
        if (PasswordThree) {
            Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();
        }
    }

    public void TabsChanger() {

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tabLayout.getSelectedTabPosition() == 0) {

                    TypeURL = 0;
                    LoadWeb();

                } else if (tabLayout.getSelectedTabPosition() == 1) {

                    TypeURL = 1;
                    LoadWeb();
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

    public boolean LanguageListener() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        StringBuilder LanguageInfo = new StringBuilder();
        LanguageInfo.append(sharedPreferences.getString("pref_language_list", "1"));
        LanguageInf = LanguageInfo.toString();

        if (LanguageInf.equals("2")) {
            return false;
        } else {
            return true;
        }
    }

    public void LanguageChanger() {

        if (LanguageListener()) {

            Locale locale = new Locale("ru");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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
            DayTab = "День";
            MainTab = "Основное";

        } else {

            Locale locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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
            DayTab = "Day";
            MainTab = "Main";
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

        if (TypeURL == 0) {
            webView.loadUrl(URL);
        } else {

            ShitMethod();
            webView.loadUrl(MainURL);
        }

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

        getMenuInflater().inflate(R.menu.main, menu);
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

    public void ShitMethod() {

        switch (GroupURL) {
            case "http://www.ggpk.by/Raspisanie/Files/P_KURS.html":  // 1
                MainURL = "http://www.ggpk.by/Raspisanie/Files/P_KURS.html";

                break;
            case "http://www.ggpk.by/Raspisanie/Files/AGB.html":   // agb
                MainURL = "http://www.ggpk.by/Raspisanie/Files/PTOO.html";

                break;
            case "http://www.ggpk.by/Raspisanie/Files/TAR.html":   // tor
                MainURL = "http://www.ggpk.by/Raspisanie/Files/MRSO.html";

                break;
            case "http://www.ggpk.by/Raspisanie/Files/PGB.html":  // pgb
                MainURL = "http://www.ggpk.by/Raspisanie/Files/PGS.html";

                break;
            case "http://www.ggpk.by/Raspisanie/Files/BDA.html":  // bda
                MainURL = "http://www.ggpk.by/Raspisanie/Files/PGS.html";

                break;
            case "http://www.ggpk.by/Raspisanie/Files/VMS.html":   //vms
                MainURL = "http://www.ggpk.by/Raspisanie/Files/VMSO.html";

                break;
            case "http://www.ggpk.by/Raspisanie/Files/PZT.html":   //pzt
                MainURL = "http://www.ggpk.by/Raspisanie/Files/VMSO.html";

                break;
            case "http://www.ggpk.by/Raspisanie/Files/AEP.html":   //aep
                MainURL = "http://www.ggpk.by/Raspisanie/Files/VMSO.html";

                break;
            case "http://www.ggpk.by/Raspisanie/Files/BSB.html":   //bsk
                MainURL = "http://www.ggpk.by/Raspisanie/Files/VMSO.html";
                break;
        }
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