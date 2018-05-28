package ambystico.barung;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import android.support.multidex.MultiDex;
import android.text.TextUtils;

public class AppController extends Application {

    String TAG = AppController.class.getSimpleName();
    RequestQueue requestQueue;
    ImageLoader imageLoader;
    LruBitmapCache lruBitmapChace;

    static AppController instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static synchronized AppController getInstance(){
        return instance;
    }

    public RequestQueue getRequestQueue(){
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    public ImageLoader getImageLoader(){
        getRequestQueue();
        if (imageLoader == null){
            getLruBitmapChace();
            imageLoader = new ImageLoader(this.requestQueue, lruBitmapChace);
        }
        return this.imageLoader;
    }

    public LruBitmapCache getLruBitmapChace() {
        if (lruBitmapChace == null)
            lruBitmapChace = new LruBitmapCache();
        return lruBitmapChace;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag){
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req){
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequest(Object tag){
        if (requestQueue == null)
            requestQueue.cancelAll(tag);
    }

}
