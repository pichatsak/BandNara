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
import com.app.bandnara.ViewLandMarkActivity;
import com.app.bandnara.ViewNewsAndEventActivity;
import com.app.bandnara.keepFireStory.LandMarkModel;
import com.app.bandnara.tools.DateTool;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class LandMarkAllAdapter extends RecyclerView.Adapter<LandMarkAllAdapter.DailyViewHolder> {
    private Context mContext;
    private ArrayList<LandMarkModel> mLandMarkList;
    private OnClickBill mOnClickBill;
    private RecyclerView.Adapter mAdapter;
    DateTool dateTool = new DateTool();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();

    public LandMarkAllAdapter(Context context, ArrayList<LandMarkModel> data) {
        mContext = context;
        mLandMarkList = data;
    }

    @NonNull
    @Override
    public DailyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_land, viewGroup, false);
        final DailyViewHolder viewHolder = new DailyViewHolder(v, mOnClickBill);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final DailyViewHolder viewHolder, final int i) {
        final LandMarkModel newsModel = mLandMarkList.get(i);
        viewHolder.tvnews.setText(newsModel.getLandName());
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyy hh:mm", Locale.US);

        storageRef.child("landmarkPic/land_"+newsModel.getKeyId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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

        Log.d("CHKMAX","Max : "+mLandMarkList.size()+" CUR : "+i);
        if(mLandMarkList.size()==(i+1)){
            Log.d("CHKMAX","YES");
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 10, 0, 150);
            viewHolder.contMain.setLayoutParams(layoutParams);
        }else{
            Log.d("CHKMAX","NO");
        }

        viewHolder.contMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ViewLandMarkActivity.class);
                intent.putExtra("keyId",newsModel.getKeyId());
                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mLandMarkList.size();
    }


    public class DailyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgnews;
        TextView tvnews;
        TextView tvDate;
        LinearLayout contMain;
        public OnClickBill onClickBill;

        public DailyViewHolder(View itemView, OnClickBill onClickBill) {
            super(itemView);
            imgnews = itemView.findViewById(R.id.imgnews);
            tvnews = itemView.findViewById(R.id.tvnews);
            tvDate = itemView.findViewById(R.id.tvDate);
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
