package com.app.bandnara.adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bandnara.R;
import com.app.bandnara.models.ReportsModel;
import com.app.bandnara.tools.DateTool;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.DailyViewHolder> {
    private Context mContext;
    private ArrayList<ReportsModel> mRegisList;
    private OnClickBill mOnClickBill;
    private RecyclerView.Adapter mAdapter;
    DateTool dateTool = new DateTool();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();

    public ReportsAdapter(Context context, ArrayList<ReportsModel> data) {
        mContext = context;
        mRegisList = data;
    }

    @NonNull
    @Override
    public DailyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_report, viewGroup, false);
        final DailyViewHolder viewHolder = new DailyViewHolder(v, mOnClickBill);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final DailyViewHolder viewHolder, final int i) {
        final ReportsModel itemReports = mRegisList.get(i);
        viewHolder.tvTitle.setText(itemReports.getReasons());
        viewHolder.tvType.setText(itemReports.getTypeReportName());
        viewHolder.tvDate.setText(itemReports.getDateTime());
        if(itemReports.getStatus().equals("0")){
            viewHolder.statusShow.setText("รอดำเนินการ");
            viewHolder.statusShow.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.orange));
        }else if(itemReports.getStatus().equals("1")){
            viewHolder.statusShow.setText("รับเรื่องแล้ว");
            viewHolder.statusShow.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.orange));
        }else if(itemReports.getStatus().equals("2")){
            viewHolder.statusShow.setText("กำลังดำเนินการแก้ไข");
            viewHolder.statusShow.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.orange));
        }else if(itemReports.getStatus().equals("3")){
            viewHolder.statusShow.setText("เสร็จสิ้น");
            viewHolder.statusShow.setBackgroundTintList(mContext.getResources().getColorStateList(R.color.green));
        }
        if(mRegisList.size()==(i+1)){
            Log.d("CHKMAX","YES");
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 25, 0, 300);
            viewHolder.contMain.setLayoutParams(layoutParams);
        }else{
            Log.d("CHKMAX","NO");
        }
    }


    @Override
    public int getItemCount() {
        return mRegisList.size();
    }


    public class DailyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle,tvDate,tvType;
        AppCompatButton statusShow;
        LinearLayout contMain;
        public OnClickBill onClickBill;

        public DailyViewHolder(View itemView, OnClickBill onClickBill) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvType = itemView.findViewById(R.id.tvType);
            contMain = itemView.findViewById(R.id.contMain);
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
