package com.app.bandnara.adaptor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bandnara.R;
import com.app.bandnara.ViewNewsAndEventActivity;
import com.app.bandnara.models.NotiUserModel;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class NotiAdapter extends RecyclerView.Adapter<NotiAdapter.DailyViewHolder> {
    private Context mContext;
    private ArrayList<NotiUserModel> mEventList;
    private OnClickBill mOnClickBill;
    private RecyclerView.Adapter mAdapter;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();

    public NotiAdapter(Context context, ArrayList<NotiUserModel> data) {
        mContext = context;
        mEventList = data;
    }

    @NonNull
    @Override
    public DailyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_noti, viewGroup, false);
        final DailyViewHolder viewHolder = new DailyViewHolder(v, mOnClickBill);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final DailyViewHolder viewHolder, final int i) {
        final NotiUserModel newsModel = mEventList.get(i);
        viewHolder.tvnoti.setText(newsModel.getTxtNoti());
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyy hh:mm", Locale.US);
        String dateShow = df.format(newsModel.getDate());
        viewHolder.tvDate.setText(dateShow);
    }


    @Override
    public int getItemCount() {
        return mEventList.size();
    }


    public class DailyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvnoti,tvDate;
        public OnClickBill onClickBill;
        LinearLayout contMain;
        public DailyViewHolder(View itemView, OnClickBill onClickBill) {
            super(itemView);
            tvnoti = itemView.findViewById(R.id.tvnoti);
            tvDate = itemView.findViewById(R.id.tvDate);
            this.onClickBill = onClickBill;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

    }

    public interface OnClickBill {
        void onClickBill(int position);
    }

}
