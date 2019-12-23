package incorporated.qualle.myggpk;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qualle.myggpk.R;

import incorporated.qualle.myggpk.settings.AppSettings;

public class DeveloperActivity extends AppCompatActivity {

    private static final String DEVELOPER_MAIL = "qualle.inc@gmail.com";
    private static final String SUBJECT = "Отзыв о My GGPK";
    private static final String MOBILE_MARKET_URL = "market://details?id=incorporated.qualle.myggpk";
    private static final String WEB_MARKET_URL = "https://play.google.com/store/apps/details?id=incorporated.qualle.myggpk";
    private static final String GIT_URL = "https://github.com/qualle34/MyGGPK";
    private static final String PASSWORD = "uselesspassword";

    private EditText inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
        onLongPress();
    }

    public void onMailClick(View view) {
        Intent sendMail = new Intent(Intent.ACTION_SEND);
        sendMail.putExtra(Intent.EXTRA_EMAIL, DEVELOPER_MAIL);
        sendMail.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);

        sendMail.setType("message/rfc822");
        startActivity(Intent.createChooser(sendMail, "Выберите почтовый клиент"));
    }

    public void onRateClick(View view) {

        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(MOBILE_MARKET_URL)));

        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(WEB_MARKET_URL)));
        }
    }

    public void onShareClick(View view) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        String Text = "My GGPK: " + WEB_MARKET_URL;

        share.putExtra(Intent.EXTRA_TEXT, Text);
        startActivity(Intent.createChooser(share, "Поделиться"));
    }

    public void onGitHubClick(View view) {
        Intent webSite = new Intent(Intent.ACTION_VIEW, Uri.parse(GIT_URL));
        startActivity(webSite);
    }

    public void onLongPress() {
        TextView developer = findViewById(R.id.developer);
        developer.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        onDeveloperClick();
                        return true;
                    }
                }
        );
    }

    public void onDeveloperClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DeveloperActivity.this);
        builder.setTitle("Переход на Vip-version");
        builder.setMessage("Введите Код");
        inputPassword = new EditText(this);
        builder.setView(inputPassword);

        builder.setPositiveButton("Принять", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String input = inputPassword.getText().toString();

                if (input.equals(PASSWORD)) {
                   AppSettings.setVip(getApplicationContext(), true);

                } else {
                    dialog.cancel();
                    AppSettings.setVip(getApplicationContext(), false);

                    Toast.makeText(DeveloperActivity.this, "Неправильный код", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void back(View view) {
        super.onBackPressed();
    }
}