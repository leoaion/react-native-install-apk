package com.heyao216.react_native_installapk;

import android.content.Intent;
import android.net.Uri;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.File;
import android.support.v4.content.FileProvider;

/**
 * Created by heyao on 2016/11/4.
 */
public class InstallApkModule extends ReactContextBaseJavaModule {
    private ReactApplicationContext _context = null;

    public InstallApkModule(ReactApplicationContext reactContext) {
        super(reactContext);
        _context = reactContext;
    }

    @Override
    public String getName() {
        return "InstallApk";
    }

    @ReactMethod
    public void install(int sdkInt, String applicationId, String path) {
        String cmd = "chmod 777 " +path;
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        File file = new File(path);
        Uri fileUri = Uri.fromFile(file);
        
        if (sdkInt >= 24) {
            fileUri = FileProvider.getUriForFile(_context, applicationId + ".provider", file);
        }

        intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
        //intent.setDataAndType(Uri.parse("file://" + path),"application/vnd.android.package-archive");
	    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        _context.startActivity(intent);
    }
}
