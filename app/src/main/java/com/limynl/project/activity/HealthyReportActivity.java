package com.limynl.project.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.limynl.project.R;
import com.limynl.project.base.TopBarBaseActivity;
import com.limynl.project.db.UserDbHelper;
import com.limynl.project.view.LineChartView;
import com.limynl.project.view.StepArcView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class HealthyReportActivity extends TopBarBaseActivity  {
    @BindView(R.id.cc)
    StepArcView cc;
    @BindView(R.id.today_integral)
    TextView todayIntegral;

    @BindView(R.id.my_rank)
    TextView myRank;

    @BindView(R.id.chartView)
    LineChartView chartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main_integral;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("健康报告");
        setTopLeftButton(R.drawable.ic_return_white_24dp, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        UserDbHelper.setInstance(this);

        initView();

    }

    private void initView() {
        cc.setCurrentCount(270, 30);
        cc.setCurrentCount(10000, Integer.parseInt(56 + ""));

        ArrayList<String> pastDaysList = new ArrayList<>(7);
        for (int i = 7 - 1; i >= 0; i--) {
            pastDaysList.add(getPastDate(i));
        }
        List<Double> earnList = Arrays.asList(34.0, 21.0, 45.0, 10.0, 25.0, 38.0, 30.0);
        chartView.setTextSize(25, 25, 25, 25);
        chartView.setData(earnList,pastDaysList,true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        String result = format.format(today);
        return result;
    }

}
