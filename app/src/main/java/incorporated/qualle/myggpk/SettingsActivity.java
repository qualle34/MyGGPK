package incorporated.qualle.myggpk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
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
        PreferenceSummaryForGroup(findPreference("pref_group_list"));
        PreferenceSummaryForNightMode(findPreference("pref_night_mode_list"));
        PreferenceSummaryForLanguage(findPreference("pref_language_list"));

        Preference reset = findPreference("pref_restart");

        reset.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                System.exit(0);
                return true;
            }
        });
    }

    private static Preference.OnPreferenceChangeListener PreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // Для list preference устанавливает значение примечания
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Устанавливает новое значение примечания
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else {
                // Для всех других настроек устанавливает примечание в значение
                // простое строковое представление.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    private void PreferenceSummaryForGroup (Preference preference) {      // Summary

        preference.setOnPreferenceChangeListener(PreferenceSummaryToValueListener);

        PreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), " "));
    }

    private void PreferenceSummaryForNightMode(Preference preference) {      // Summary

        preference.setOnPreferenceChangeListener(PreferenceSummaryToValueListener);

        PreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), " "));
    }

    private void PreferenceSummaryForLanguage(Preference preference) {      // Summary

        preference.setOnPreferenceChangeListener(PreferenceSummaryToValueListener);

        PreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), " "));
    }

    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }


    private void setupActionBar() {      // Отображение панели действий
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {  // Кнопка назад на панели действий
        switch (item.getItemId()) {  // Слушатель кнопки

            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}