package com.aliriomambo.diaryapp.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Blue on 6/26/2018.
 */

public class NetworkUtils {
    public class CheckNetworkTask extends AsyncTask<Context, Void, Boolean> {
        private CheckNetworkResponse checkNetworkResponse = null;

        public CheckNetworkTask(CheckNetworkResponse response) {
            checkNetworkResponse = response;
        }

        @Override
        protected Boolean doInBackground(Context... contexts) {
            if (isNetworkAvailable(contexts[0])) {
                try {
                    HttpURLConnection urlc = (HttpURLConnection)
                            (new URL("http://clients3.google.com/generate_204")
                                    .openConnection());
                    urlc.setRequestProperty("User-Agent", "Android");
                    urlc.setRequestProperty("Connection", "close");
                    urlc.setConnectTimeout(1500);
                    urlc.connect();
                    return (urlc.getResponseCode() == 204 &&
                            urlc.getContentLength() == 0);
                } catch (IOException e) {
                    Log.e("Network", "Error checking internet connection", e);
                }
            } else {
                Log.d("NETWORK", "No network available!");
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean hasInternetAccess) {
            super.onPostExecute(hasInternetAccess);
            checkNetworkResponse.processFinish(hasInternetAccess);
        }

        public void checkConnectivity(Context context) {
            execute(context);
        }

        public boolean isNetworkAvailable(Context context) {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity == null) {
                return false;
            } else {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }

    public interface CheckNetworkResponse {
        void processFinish(boolean hasInternetAccess);
    }
}
