package com.app.bandnara.ToolBar;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.app.bandnara.R;
import com.app.bandnara.announceActivity;
import com.app.bandnara.informationUserActivity;
import com.app.bandnara.newsActivity;
import com.app.bandnara.settingActivity;

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
                context.startActivity(intent);
            }
        });

        LinearLayout menu_report = frameLayout.findViewById(R.id.menu_report);
        menu_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, announceActivity.class);
                context.startActivity(intent);
            }
        });

        LinearLayout menu_setting = frameLayout.findViewById(R.id.menu_setting);
        menu_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, settingActivity.class);
                context.startActivity(intent);
            }
        });


    }
}
