package com.app.bandnara.adaptor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.app.bandnara.keepFireStory.EventsModel;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.DailyViewHolder> {
    private Context mContext;
    private ArrayList<EventsModel> mEventList;
    private OnClickBill mOnClickBill;
    private RecyclerView.Adapter mAdapter;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();

    public EventsAdapter(Context context, ArrayList<EventsModel> data) {
        mContext = context;
        mEventList = data;
    }

    @NonNull
    @Override
    public DailyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_events, viewGroup, false);
        final DailyViewHolder viewHolder = new DailyViewHolder(v, mOnClickBill);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final DailyViewHolder viewHolder, final int i) {
        final EventsModel newsModel = mEventList.get(i);
        viewHolder.tvnews.setText(newsModel.getEventTitle());

        storageRef.child("eventsPic/events_"+newsModel.getKeyId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(mContext)
                .load(uri)
                .into(viewHolder.imgnews);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        viewHolder.contMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ViewNewsAndEventActivity.class);
                intent.putExtra("keyId",newsModel.getKeyId());
                intent.putExtra("type","events");
                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mEventList.size();
    }


    public class DailyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgnews;
        TextView tvnews;
        LinearLayout contMain;
        public OnClickBill onClickBill;

        public DailyViewHolder(View itemView, OnClickBill onClickBill) {
            super(itemView);
            imgnews = itemView.findViewById(R.id.imgnews);
            tvnews = itemView.findViewById(R.id.tvnews);
            contMain = itemView.findViewById(R.id.contMain);
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
