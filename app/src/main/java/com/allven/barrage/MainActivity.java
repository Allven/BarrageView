package com.allven.barrage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BarrageView barrageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        barrageView = findViewById(R.id.bv_barrage_view);

        List<String> barrageData = new ArrayList<>();
        barrageData.add("疏影横斜水清浅");
        barrageData.add("暗香浮动月黄昏");
        barrageData.add("十里平湖霜满天");
        barrageData.add("寸寸青丝愁华年");
        barrageData.add("对月形单望相互");
        barrageData.add("只羡鸳鸯不羡仙");
        barrageView.setBarrageData(barrageData);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
