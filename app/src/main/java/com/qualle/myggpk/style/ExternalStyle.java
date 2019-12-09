package com.qualle.myggpk.style;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String setStyle(String html, String css) {
        String fontLink = "<link href=\"https://fonts.googleapis.com/css?family=Roboto&display=swap\" rel=\"stylesheet\">";
        Pattern stylePattern = Pattern.compile("<style[^>]*>([\\s\\S]*)<\\/style>");
        Matcher matcher = stylePattern.matcher(html);
        return matcher.replaceAll(fontLink + "<style>" + css + "</style>");
    }

    public static String getWithStyle(String url, String css) {
        HtmlManager htmlManager = new HtmlManager();
        htmlManager.execute(url);
        String result = null;

        try {
            result = setStyle(htmlManager.get(), css);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }
}
