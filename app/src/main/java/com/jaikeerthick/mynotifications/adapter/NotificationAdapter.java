package com.jaikeerthick.mynotifications.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.jaikeerthick.mynotifications.R;
import com.jaikeerthick.mynotifications.model.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ImageViewholder> {

    private Context mContext;
    private List<Notification> mNotifications;
    private String finder1;

    public NotificationAdapter(Context context, List<Notification> notifications) {
        mContext= context;
        mNotifications= notifications;

    }

    @NonNull
    @Override
    public ImageViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.adapter_item, parent, false);
        return new ImageViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewholder holder, int position) {

        Notification uploader = mNotifications.get(position);
        holder.title.setText(uploader.getTitleNoti());
        holder.body.setText(uploader.getTextNoti());

        String packageName = uploader.getPackageNameNoti();

        try {
            Drawable icon = mContext.getPackageManager().getApplicationIcon(packageName);
            holder.icon.setImageDrawable(icon);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            holder.icon.setImageResource(R.drawable.ic_launcher_background);
        }


    }

    @Override
    public int getItemCount() {
        return mNotifications.size();
    }

    public class ImageViewholder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView body;
        public ImageView icon;

        public ImageViewholder(@NonNull final View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_adapter);
            body = itemView.findViewById(R.id.text_adapter);
            icon = itemView.findViewById(R.id.image_adapter);


        }
    }
}


