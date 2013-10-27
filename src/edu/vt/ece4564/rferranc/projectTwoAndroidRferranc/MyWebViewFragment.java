package edu.vt.ece4564.rferranc.projectTwoAndroidRferranc;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/*
 * Fragment for viewing the graph on the server
 */
public class MyWebViewFragment extends Fragment {
	String url;
	WebView webView;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {   
        View mainView = (View) inflater.inflate(R.layout.webviewfragment, container, false);
        webView = (WebView) mainView.findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        return mainView;
    }
    
    public void setURL(String aURL) {
    	url = aURL;
    	if(webView != null) {
    		webView.loadUrl(url);
    	}
    }
	
}
