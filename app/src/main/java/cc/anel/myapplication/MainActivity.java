package cc.anel.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private WebView web_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView    = (TextView)    findViewById(R.id.TextView01);
        web_view    = (WebView)     findViewById(R.id.web_view);
    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(params[0])
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                }else {
                    return "Download failed";
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Download failed";

        }

        @Override
        protected void onPostExecute(String s) {
            textView.setVisibility(View.GONE);
            web_view.loadData(s,"text/html; charset=UTF-8", null);
        }
    }

    public void onClick(View view) {
        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute(new String[]{"http://www.vogella.com/index.html"});
        textView.setText("Loading ... ");
    }


}
