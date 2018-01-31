package com.amihealth.amihealth.Adaptadores;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amihealth.amihealth.AppConfig.notification.NotificationAction;
import com.amihealth.amihealth.Configuraciones.Configuracion;
import com.amihealth.amihealth.Models.AmIHealthNotificacion;
import com.amihealth.amihealth.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by GITCE on 01/24/18.
 */

public class AdapterNotification extends RealmRecyclerViewAdapter{

    private NotificationAction notificationAction;
    private Context context;

    public AdapterNotification(Context contexte, NotificationAction notificationAction, @Nullable OrderedRealmCollection data, boolean autoUpdate) {
        super(data, autoUpdate);
        this.notificationAction = notificationAction;
        this.context = contexte;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

           View view   = LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.notification_item, parent, false);
           return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder v = (ViewHolder) holder;
            Toast.makeText(context,String.valueOf(getItemCount()),Toast.LENGTH_LONG).show();
            AmIHealthNotificacion notificacion = (AmIHealthNotificacion) getData().get(position);
            v.setNotificacion(notificacion);
            v.mensaje_notify.setText(notificacion.getBody());
            v.nombre_notify.setText(notificacion.getMedico().getNombre() + " " + notificacion.getMedico().getApellido());
            v.setImageView(notificacion.getMedico().getImg());



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
        private ImageView imageView;
        private Timer timer = new Timer();
        private CountDownTimer countDownTimer;


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
            imageView =(ImageView) itemView.findViewById(R.id.img_doc_perfil);

            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notificationAction.aceptar(notificacion.getId());
                }
            });
            btn_decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notificationAction.declinar(notificacion.getId());
                }
            });



        }

        public void setImageView(String url){

            Picasso.with(context).load(Configuracion.URL_IMG_ROOT + url).into(imageView);
            setTime();

        }

        private void setTime(){

            long initialTime;

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date fecha = new Date();
            try {
                fecha = df.parse(notificacion.getCreated_at());
            } catch (ParseException e) {
                e.printStackTrace();
            }


            Date hoy = Calendar.getInstance().getTime();

            initialTime = hoy.getTime() - fecha.getTime();
            countDownTimer = new CountDownTimer(initialTime, 100000) {

                @Override
                public void onTick(long millisUntilFinished) {

                    int days = (int) ((millisUntilFinished / 1000) / 86400);
                    int hours = (int) (((millisUntilFinished / 1000)
                            - (days * 86400)) / 3600);
                    int minutes = (int) (((millisUntilFinished / 1000)
                            - (days * 86400) - (hours * 3600)) / 60);
                    int seconds = (int) ((millisUntilFinished / 1000) % 60);

                    String countDownText = String.format("hace %2d Dias %2d : %2d Min", days, hours, minutes);
                    apellido_notify.setText(countDownText);
                }

                @Override
                public void onFinish() {
                    //apellido_notify.setText(DateUtils.formatElapsedTime(0));
                }

            }.start();

            apellido_notify.setVisibility(View.VISIBLE);
        }
    }
}
