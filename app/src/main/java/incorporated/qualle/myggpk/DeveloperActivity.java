package incorporated.qualle.myggpk;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class DeveloperActivity extends AppCompatActivity {

    TextView me;
    String MyMail = "qualle.inc@gmail.com";
    String MyURL = "https://play.google.com/store/apps/details?id=incorporated.qualle.myggpk";
    EditText InputPassword;
    private String password_1 = "anime322";
    private String password_2 = "sobolev";
    private String password_3 = "xolod";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
        setupActionBar();
        OnLongPress();
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

    public void OnLongPress() {

        me = findViewById(R.id.Me);
        me.setOnLongClickListener(new View.OnLongClickListener() {
                                      @Override
                                      public boolean onLongClick(View v) {

                                          OnMeClick();

                                          return true;
                                      }
                                  }

        );

    }


    public void OnMeClick() {

        Boolean PasswordOne = getSharedPreferences("PASSWORD_ONE", MODE_PRIVATE).getBoolean("password_one", false);
        Boolean PasswordTwo = getSharedPreferences("PASSWORD_TWO", MODE_PRIVATE).getBoolean("password_two", false);
        Boolean PasswordThree = getSharedPreferences("PASSWORD_THREE", MODE_PRIVATE).getBoolean("password_three", false);

        AlertDialog.Builder builder = new AlertDialog.Builder(DeveloperActivity.this);
        builder.setTitle("Переход на Pro-version");
        builder.setMessage("Введите Код");
        InputPassword = new EditText(this);
        builder.setView(InputPassword);

        builder.setPositiveButton("Принять", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                String pole = InputPassword.getText().toString();

                if (pole.equals(password_1)) {

                    getSharedPreferences("PASSWORD_ONE", MODE_PRIVATE).edit().putBoolean("password_one", true).apply();

                    getSharedPreferences("PASSWORD_TWO", MODE_PRIVATE).edit().putBoolean("password_two", false).apply();
                    getSharedPreferences("PASSWORD_THREE", MODE_PRIVATE).edit().putBoolean("password_three", false).apply();

                } else if (pole.equals(password_2)) {
                    getSharedPreferences("PASSWORD_TWO", MODE_PRIVATE).edit().putBoolean("password_two", true).apply();

                    getSharedPreferences("PASSWORD_ONE", MODE_PRIVATE).edit().putBoolean("password_one", false).apply();
                    getSharedPreferences("PASSWORD_THREE", MODE_PRIVATE).edit().putBoolean("password_three", false).apply();

                } else if (pole.equals(password_3)) {
                    getSharedPreferences("PASSWORD_THREE", MODE_PRIVATE).edit().putBoolean("password_three", true).apply();

                    getSharedPreferences("PASSWORD_ONE", MODE_PRIVATE).edit().putBoolean("password_one", false).apply();
                    getSharedPreferences("PASSWORD_TWO", MODE_PRIVATE).edit().putBoolean("password_two", false).apply();
                } else {
                    dialog.cancel();

                    getSharedPreferences("PASSWORD_ONE", MODE_PRIVATE).edit().putBoolean("password_one", false).apply();
                    getSharedPreferences("PASSWORD_TWO", MODE_PRIVATE).edit().putBoolean("password_two", false).apply();
                    getSharedPreferences("PASSWORD_THREE", MODE_PRIVATE).edit().putBoolean("password_three", false).apply();

                    Toast.makeText(DeveloperActivity.this, "Неправильный код", Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setNegativeButton("Отмена",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        AlertDialog alert = builder.create();
        alert.show();
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