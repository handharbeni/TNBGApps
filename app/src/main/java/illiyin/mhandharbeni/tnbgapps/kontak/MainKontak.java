package illiyin.mhandharbeni.tnbgapps.kontak;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import illiyin.mhandharbeni.tnbgapps.R;

/**
 * Created by root on 9/6/17.
 */

public class MainKontak extends Fragment {
    View v;
    WebView kontak;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.kontak_layout, container, false);

        String content;

        kontak = v.findViewById(R.id.kontak);
        kontak.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        kontak.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
        kontak.setLongClickable(false);
        kontak.setHapticFeedbackEnabled(false);
        WebSettings contentsetting = kontak.getSettings();
        contentsetting.setJavaScriptEnabled(true);
        contentsetting.setUseWideViewPort(false);
        contentsetting.setLoadWithOverviewMode(true);
        kontak.getSettings();
        kontak.setBackgroundColor(Color.TRANSPARENT);
        content = "<style>body{margin-left: 20px;margin-right: 20px;text-align: center;font-size: 20px;}a{font-weight: bold;}</style>";
        content += "<body>Jika anda punya berita yang ingin di post atau berita yang ingin orang lain ketahui sebagai pelajaran, info atau lainnya. Silakan kirim email ke <a href=\"#\">lapor@tnbg.news</a><br><br>Untuk permasalahan mengenai website, <p>silakan email ke <a href=\"#\">admin@tnbg.news</a></p></body>";
        kontak.loadData(content, "text/html","UTF-8");
        return v;
    }
}
