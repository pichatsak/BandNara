package com.app.bandnara;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import com.app.bandnara.ToolBar.CloseBar;

public class elderlyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elderly);
        CloseBar closeBar = new CloseBar(this);



        final Dialog dialog = new Dialog(elderlyActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_hightold);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

//        dialog.show();
//        Display display =((WindowManager)getSystemService(elderlyActivity.this.WINDOW_SERVICE)).getDefaultDisplay();
//        int width = display.getWidth();
//        int height=display.getHeight();
//        Log.v("width", width+"");
//        dialog.getWindow().setLayout((6*width)/7,(4*height)/3);

        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);

        dialog.getWindow().setLayout(width, height);
    }
}