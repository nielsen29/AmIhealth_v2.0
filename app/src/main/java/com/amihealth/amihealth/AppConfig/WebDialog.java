package com.amihealth.amihealth.AppConfig;

import android.app.Activity;
import android.app.Dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.amihealth.amihealth.Models.Peso;
import com.amihealth.amihealth.ModuloAntropomorficas.Home.fragments.AddPesoDialogFragment;
import com.amihealth.amihealth.R;

import io.realm.Realm;

/**
 * Created by GITCE on 01/19/18.
 */

public class WebDialog extends DialogFragment {
    private String datoPeso = "";
    private EditText peso;
    private Spinner sp_kg;
    private String url;


    public WebDialog() {
        super();
    }


   public static WebDialog newInstance(String url) {
        WebDialog f = new WebDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("URL", url);
        f.setArguments(args);

        return f;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        return crearDialogo(getArguments().getString("URL"));

    }

    public AlertDialog crearDialogo(String url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.web_view, null);
        WebView webView = (WebView) v.findViewById(R.id.webview);
        webView.loadUrl(url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        webView.setWebViewClient(new WebViewClient());

        builder.setView(v);

        builder.setPositiveButton(getActivity().getString(R.string.accept), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton(getActivity().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.setTitle(getString(R.string.nuevo_WebTerminos_titulo));

        return builder.create();

    }


    public interface OnWebDialogResponse {
        public void onDialogPositiveClick();
        public void onDialogPositiveEdit(String id, double value);
        public void onDialogNegativeClick();
    }

    // Use this instance of the interface to deliver action events
    OnWebDialogResponse mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener


    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        if(childFragment instanceof OnWebDialogResponse){
            try {
                // Instantiate the NoticeDialogListener so we can send events to the host
                mListener = (OnWebDialogResponse) childFragment;
            } catch (ClassCastException e) {
                // The activity doesn't implement the interface, throw exception
                throw new ClassCastException(childFragment.toString()
                        + " must implement NoticeDialogListener");
            }
        }else{
            mListener = (OnWebDialogResponse) this;
        }
    }
}