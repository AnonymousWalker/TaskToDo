package edu.self.tasktodo;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by Hoang Anh on 01-Jul-18.
 */

public class App extends Application {
    public static final String APP_SHARED_PREFERENCE = "TaskTodoData";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void savePref(String keyId, String value){
        SharedPreferences caches = getSharedPreferences(APP_SHARED_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editCaches = caches.edit();
        editCaches.putString(keyId, value);
        editCaches.commit();
    }

    public String loadPref(String keyId)
    {
        SharedPreferences caches = getSharedPreferences(APP_SHARED_PREFERENCE, MODE_PRIVATE);
        return caches.getString(keyId, null);
    }

    public void removePref(String keyId)
    {
        SharedPreferences caches = getSharedPreferences(APP_SHARED_PREFERENCE, 0);
        SharedPreferences.Editor edCaches = caches.edit();
        edCaches.remove(keyId);
        edCaches.commit();
    }
}
