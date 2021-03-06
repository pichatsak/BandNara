package com.app.bandnara.ToolBar;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.app.bandnara.ListReportActivity;
import com.app.bandnara.R;
import com.app.bandnara.announceActivity;
import com.app.bandnara.informationUserActivity;
import com.app.bandnara.newsActivity;
import com.app.bandnara.settingActivity;
import com.app.bandnara.typeActivity;

public class BottomBar {
    private Context context;
    private FrameLayout frameLayout;

    public BottomBar(Context context, FrameLayout frameLayout) {
        this.context = context;
        this.frameLayout = frameLayout;
        LinearLayout menu_home = frameLayout.findViewById(R.id.menu_home);
        menu_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, newsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        LinearLayout menu_report = frameLayout.findViewById(R.id.menu_report);
        menu_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ListReportActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        LinearLayout menu_setting = frameLayout.findViewById(R.id.menu_setting);
        menu_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, settingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        LinearLayout menu_regis = frameLayout.findViewById(R.id.menu_regis);
        menu_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, typeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });



    }
}
