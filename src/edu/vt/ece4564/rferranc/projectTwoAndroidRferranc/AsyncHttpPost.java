package edu.vt.ece4564.rferranc.projectTwoAndroidRferranc;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public class AsyncHttpPost extends AsyncTask<String, String, String> {
    SensorData data;

    /**
     * constructor
     */
    public AsyncHttpPost(SensorData d) {
        data = d;
    }

    /**
     * background
     */
    @Override
    protected String doInBackground(String... params) {
        byte[] result = null;
        String str = "";
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(params[0].trim());// in this case, params[0] is URL
        try {
            // set up post data
        	// Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
            nameValuePairs.add(new BasicNameValuePair("time", Long.toString(data.getTime())));
            nameValuePairs.add(new BasicNameValuePair("measuredfor", data.getMeasuredFor()));
            nameValuePairs.add(new BasicNameValuePair("temp", Integer.toString(data.getTemp())));
            nameValuePairs.add(new BasicNameValuePair("humi", Integer.toString(data.getHumidity())));
            nameValuePairs.add(new BasicNameValuePair("light", Integer.toString(data.getLight())));
            
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = client.execute(post);
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpURLConnection.HTTP_OK){
                result = EntityUtils.toByteArray(response.getEntity());
                str = new String(result, "UTF-8");
            }
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
        }
        return str;
    }

    /**
     * on getting result
     */
    @Override
    protected void onPostExecute(String result) {
        // something...
    }
}