package com.amihealth.amihealth.ApiAmIHealth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import static android.net.NetworkInfo.State;

/**
 * Created by Viraj Tank, 18-06-2016.
 */
public class InternetConnection {
    private Context context;

}