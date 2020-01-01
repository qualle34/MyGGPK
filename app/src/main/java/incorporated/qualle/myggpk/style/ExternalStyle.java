package incorporated.qualle.myggpk.style;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import incorporated.qualle.myggpk.settings.AppSettings;

public class ExternalStyle {

    private static class HtmlManager extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            try {
                URL url1 = new URL(url[0]);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url1.openStream(), "windows-1251"));
                StringBuilder builder = new StringBuilder();
                String input;

                while ((input = bufferedReader.readLine()) != null) {
                    builder.append(input);
                }

                return builder.toString();

            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    public static String getWithStyle(Context applicationContext, String url, String css, boolean isOnline) throws ExecutionException, InterruptedException, IOException {
        HtmlManager htmlManager = new HtmlManager();
        htmlManager.execute(url);
        if (isOnline) {
            AppSettings.setHtml(applicationContext, url, htmlManager.get());
            return setStyle(htmlManager.get(), css);
        } else {
            String html = AppSettings.getHtml(applicationContext, url);
            if (html != null && !html.equals("")) {
                return setStyle(html, css);
            } else {
                return getOfflinePage(applicationContext);
            }
        }
    }

    private static String setStyle(String html, String css) {
        String fontLink = "<link href=\"https://fonts.googleapis.com/css?family=Roboto&display=swap\" rel=\"stylesheet\">";
        Pattern stylePattern = Pattern.compile("<style[^>]*>([\\s\\S]*)<\\/style>");
        Matcher matcher = stylePattern.matcher(html);
        return matcher.replaceAll(fontLink + "<style>" + css + "</style>");
    }

    private static String getOfflinePage(Context applicationContext) throws IOException {
        AssetManager am = applicationContext.getAssets();
        InputStream inputStream = am.open("offline.html");
        java.util.Scanner s = new java.util.Scanner(inputStream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
