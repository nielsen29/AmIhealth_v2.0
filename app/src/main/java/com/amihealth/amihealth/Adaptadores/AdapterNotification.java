package com.amihealth.amihealth.Adaptadores;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.amihealth.amihealth.AppConfig.notification.NotificationAction;
import com.amihealth.amihealth.Models.AmIHealthNotificacion;
import com.amihealth.amihealth.R;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by GITCE on 01/24/18.
 */

public class AdapterNotification extends RealmRecyclerViewAdapter{

    private NotificationAction notificationAction;

    public AdapterNotification(NotificationAction notificationAction, @Nullable OrderedRealmCollection data, boolean autoUpdate) {
        super(data, autoUpdate);
        this.notificationAction = notificationAction;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder v = (ViewHolder) holder;
        AmIHealthNotificacion notificacion = (AmIHealthNotificacion) getData().get(position);
        v.setNotificacion(notificacion);
        v.mensaje_notify.setText(notificacion.getBody());
        v.nombre_notify.setText(notificacion.getFrom());
    }


    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Nullable
    @Override
    public OrderedRealmCollection getData() {
        return super.getData();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView nombre_notify;
        private TextView apellido_notify;
        private TextView mensaje_notify;
        private String from;
        private String to;
        private String created_at;
        private Button btn_ok;
        private Button btn_decline;
        private AmIHealthNotificacion notificacion;

        public AmIHealthNotificacion getNotificacion() {
            return notificacion;
        }

        public void setNotificacion(AmIHealthNotificacion notificacion) {
            this.notificacion = notificacion;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            nombre_notify = (TextView) itemView.findViewById(R.id.name_notify);
            apellido_notify = (TextView) itemView.findViewById(R.id.lastname_notify);
            mensaje_notify = (TextView) itemView.findViewById(R.id.message_notify);
            btn_ok = (Button) itemView.findViewById(R.id.btn_ok);
            btn_decline = (Button) itemView.findViewById(R.id.btn_decline);


        }
    }
}
