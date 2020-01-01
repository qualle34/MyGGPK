package incorporated.qualle.myggpk;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DeveloperActivity extends AppCompatActivity {

    TextView me;
    String MyMail = "qualle.inc@gmail.com";
    String MyURL = "https://play.google.com/store/apps/details?id=incorporated.qualle.myggpk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
        setupActionBar();
    }

    public void OnMailClick(View view) {

        String Emails = MyMail;
        String[] Email = Emails.split(",");

        String subject = "Отзыв о My GGPK";

        Intent sendMail = new Intent(Intent.ACTION_SEND);
        sendMail.putExtra(Intent.EXTRA_EMAIL, Email);
        sendMail.putExtra(Intent.EXTRA_SUBJECT, subject);


        sendMail.setType("message/rfc822");
        startActivity(Intent.createChooser(sendMail, "Выберите почтовый клиент"));
    }

    public void OnRateClick(View view) {

        try {

            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + getPackageName())));

        } catch (ActivityNotFoundException e) {

            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }

    }

    public void OnShareClick(View view) {

        Intent Share = new Intent(Intent.ACTION_SEND);
        Share.setType("text/plain");
        String Text = "My GGPK: " + MyURL;

        Share.putExtra(Intent.EXTRA_TEXT, Text);

        startActivity(Intent.createChooser(Share, "Поделиться"));
    }

    public void OnAboutClick(View view) {
    }

    private void setupActionBar() {      // Отображение экшенбара
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            // Меняет цвет и добавляет кнопку
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    // Кнопка назад на главном экране настроек
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
