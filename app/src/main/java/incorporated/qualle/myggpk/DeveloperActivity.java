package incorporated.qualle.myggpk;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

    private void sendMail() {

        String Emails = MyMail;
        String[] Email = Emails.split(",");

        String subject = "Отзыв о My GGPK";

        Intent sendMail = new Intent(Intent.ACTION_SEND);
        sendMail.putExtra(Intent.EXTRA_EMAIL, Email);
        sendMail.putExtra(Intent.EXTRA_SUBJECT, subject);


        sendMail.setType("message/rfc822");
        startActivity(Intent.createChooser(sendMail, "Выберите почтовый клиент"));
    }

    public void Mail(View view) {

        sendMail();

    }

    public void Rate(View view) {

        try {

            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + getPackageName())));

        } catch (ActivityNotFoundException e) {

            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }


    public void Share(View view) {

        Intent Share = new Intent(Intent.ACTION_SEND);
        Share.setType("text/plain");
        String Text = "My GGPK: " + MyURL;

        Share.putExtra(Intent.EXTRA_TEXT, Text);

        startActivity(Intent.createChooser(Share, "Поделиться"));

    }

    public void PonyClick(View view) {

        Toast.makeText(getApplicationContext(),
                "Не тыкай", Toast.LENGTH_LONG).show();

    }


    // Отображение кнопки назад на главном экране настроек
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

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
