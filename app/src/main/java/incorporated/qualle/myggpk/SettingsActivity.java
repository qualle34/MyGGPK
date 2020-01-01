package incorporated.qualle.myggpk;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceManager;
import android.view.MenuItem;


@SuppressLint("ExportedPreferenceActivity")
public class SettingsActivity extends AppCompatPreferenceActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();


        addPreferencesFromResource(R.xml.settings_screen);
        PreferenceSummaryForGroup(findPreference("general_screen_group"));
        PreferenceSummaryForNightMode(findPreference("general_screen_theme"));

        Preference reset = findPreference("restart");

        reset.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                System.exit(0);
                return true;
            }
        });

    }

    //для  предпочтений (Summary)
    private Preference.OnPreferenceChangeListener PreferenceSummaryListener = new Preference.OnPreferenceChangeListener() {

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {

                onRestart();

                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else {

                onRestart();

                preference.setSummary(stringValue);

        }
            return true;
        }
    };


    private void PreferenceSummaryForGroup (Preference preference) {      // Summary

        preference.setOnPreferenceChangeListener(PreferenceSummaryListener);

        PreferenceSummaryListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), " "));



    }

    private void PreferenceSummaryForNightMode(Preference preference) {      // Summary

        preference.setOnPreferenceChangeListener(PreferenceSummaryListener);

        PreferenceSummaryListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), " "));

    }

    // Отображение кнопки назад на главном экране настроек
    public void setupActionBar() {
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





