package com.amihealth.amihealth.Views.Fragmentos.Registro.view;


import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.widget.DatePicker;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FechaFragment extends DatePickerDialog implements DatePickerDialog.OnDateSetListener {

    public FechaFragment(@NonNull Context context, @Nullable OnDateSetListener listener, int year, int month, int dayOfMonth) {
        super(context, listener, year, month, dayOfMonth);
    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, @Nullable Menu menu, int deviceId) {

    }
}
