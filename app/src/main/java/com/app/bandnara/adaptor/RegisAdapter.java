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
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bandnara.R;
import com.app.bandnara.ViewNewsAndEventActivity;
import com.app.bandnara.models.ItemRegis;
import com.app.bandnara.tools.DateTool;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class RegisAdapter extends RecyclerView.Adapter<RegisAdapter.DailyViewHolder> {
    private Context mContext;
    private ArrayList<ItemRegis> mRegisList;
    private OnClickBill mOnClickBill;
    private RecyclerView.Adapter mAdapter;
    DateTool dateTool = new DateTool();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();

    public RegisAdapter(Context context, ArrayList<ItemRegis> data) {
        mContext = context;
        mRegisList = data;
    }

    @NonNull
    @Override
    public DailyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_regis, viewGroup, false);
        final DailyViewHolder viewHolder = new DailyViewHolder(v, mOnClickBill);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final DailyViewHolder viewHolder, final int i) {
        final ItemRegis itemRegis = mRegisList.get(i);
        viewHolder.tvName.setText(itemRegis.getNameRegis());
        viewHolder.tvDate.setText(itemRegis.getDateRegis());
        viewHolder.tvAge.setText("อายุ "+itemRegis.getAgeRegis()+" ปี");
        if(itemRegis.getTypeData()==3){
            viewHolder.tvAge.setText("");
        }
        if(itemRegis.getStatusRegis().equals("wait")){
            viewHolder.statusShow.setText("รอดำเนินการ");
            viewHolder.statusShow.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.orange));
        }else if(itemRegis.getStatusRegis().equals("no")){
            viewHolder.statusShow.setText("ไม่ผ่าน");
            viewHolder.statusShow.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.red));
        }else if(itemRegis.getStatusRegis().equals("yes")){
            viewHolder.statusShow.setText("ลงทะเบียนเสร็จสิ้น");
            viewHolder.statusShow.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.green));
        }
    }


    @Override
    public int getItemCount() {
        return mRegisList.size();
    }


    public class DailyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvName,tvDate,tvAge;
        AppCompatButton statusShow;
        public OnClickBill onClickBill;

        public DailyViewHolder(View itemView, OnClickBill onClickBill) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvAge = itemView.findViewById(R.id.tvAge);
            statusShow = itemView.findViewById(R.id.statusShow);

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
