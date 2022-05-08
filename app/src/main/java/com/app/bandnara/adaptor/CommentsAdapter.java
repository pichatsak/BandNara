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
import com.app.bandnara.keepFireStory.CommentModel;
import com.app.bandnara.tools.DateTool;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.DailyViewHolder> {
    private Context mContext;
    private ArrayList<CommentModel> mCommentList;
    private OnClickBill mOnClickBill;
    private RecyclerView.Adapter mAdapter;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private DateTool dateTool = new DateTool();

    public CommentsAdapter(Context context, ArrayList<CommentModel> data) {
        mContext = context;
        mCommentList = data;
    }

    @NonNull
    @Override
    public DailyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_comment, viewGroup, false);
        final DailyViewHolder viewHolder = new DailyViewHolder(v, mOnClickBill);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final DailyViewHolder viewHolder, final int i) {
        final CommentModel newsModel = mCommentList.get(i);

        viewHolder.tvnews.setText(newsModel.getNameUser());
        viewHolder.tvComments.setText(newsModel.getTxtComment());
        viewHolder.tvDate.setText(dateTool.getDateFromTm(String.valueOf(newsModel.getDateCreate())));
        storageRef.child("imgprofile/"+newsModel.getUserId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(mContext)
                .load(uri)
                .into(viewHolder.imgProfile);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

//        Log.d("CHKMAX","Max : "+mCommentList.size()+" CUR : "+i);
//        if(mCommentList.size()==(i+1)){
//            Log.d("CHKMAX","YES");
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            layoutParams.setMargins(0, 10, 0, 150);
//            viewHolder.contMain.setLayoutParams(layoutParams);
//        }else{
//            Log.d("CHKMAX","NO");
//        }


    }


    @Override
    public int getItemCount() {
        return mCommentList.size();
    }


    public class DailyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgnews;
        TextView tvnews;
        TextView tvDate;
        public OnClickBill onClickBill;
        ShapeableImageView imgProfile;
        LinearLayout contMain;
        TextView tvComments;
        public DailyViewHolder(View itemView, OnClickBill onClickBill) {
            super(itemView);
            imgnews = itemView.findViewById(R.id.imgnews);
            tvnews = itemView.findViewById(R.id.tvnews);
            tvDate = itemView.findViewById(R.id.tvDate);
            contMain = itemView.findViewById(R.id.contMain);
            imgProfile = itemView.findViewById(R.id.imgProfile);
            tvComments = itemView.findViewById(R.id.tvComments);

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
