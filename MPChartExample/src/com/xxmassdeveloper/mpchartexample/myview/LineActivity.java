package com.xxmassdeveloper.mpchartexample.myview;

import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.xxmassdeveloper.mpchartexample.R;
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 时间戳折线图, 固定可视长度,可左右滑动
 */
public class LineActivity extends DemoBase {
    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_line);
        mChart = findViewById(R.id.chart);
        initChart();
        setLineChartData(200);
        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setTextSize(11f);
        l.setTextColor(Color.GRAY);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
//        l.setYOffset(11f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //xAxis.setGranularity(60f);// 设置X轴显示间隔,有问题
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int a = (int) value;
                if (value % 30 == 0) {
                    // index 转化时间戳,+16*60是调整时间戳
                    long time = TimeUnit.MINUTES.toMillis(a + 16 * 60);
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                    return format.format(new Date(time));
                } else {
                    return "";
                }
            }
        });
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(Color.BLACK


        );
        //leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularityEnabled(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    private void initChart() {
        mChart.getDescription().setEnabled(false);
        mChart.setTouchEnabled(true);
        mChart.setDragDecelerationFrictionCoef(0.9f);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(false);
        mChart.animateX(2500);
    }

    // 插入随机数
    public void setLineChartData(int count) {
        ArrayList<Entry> yVals1 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            yVals1.add(new Entry(i, (float) (Math.random() * 100)));
        }
        LineDataSet set1 = getLineDataSet(mChart, 0, "maple", yVals1);

        // LineData data = new LineData(set1, set2, set3, set4);
        LineData data = new LineData(set1);
        data.setValueTextSize(9f);

        // set data
        mChart.setData(data);
        // 设置X轴可视最小距离,最大距离
        mChart.setVisibleXRange(60f, 60f);
        mChart.getData().notifyDataChanged();
        mChart.notifyDataSetChanged();
    }

    public static LineDataSet getLineDataSet(LineChart chart, int i, String label, ArrayList<Entry> entries) {
        LineDataSet set1;
        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(entries);
        } else {
            set1 = new LineDataSet(entries, label);
            set1.setDrawCircles(false);
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighlightEnabled(false);
            set1.setDrawCircleHole(false);
            set1.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    int valueInt = (int) value;
                    if (valueInt == 0) return "";
                    // 每十条数据显示第二条, 避免第一条与轴重叠
                    if (entry.getX() % 10 == 1) return String.valueOf(valueInt);
                    return "";
                }
            });
            set1.setValueTextColor(Color.BLACK);
        }

        return set1;
    }
}
/*
*     switch (i) {
                case 0:

                    break;
                case 1:
                    set1.setColor(Color.RED);
                    set1.setFillColor(Color.RED);
                    break;
                case 2:
                    set1.setColor(Color.YELLOW);
                    set1.setFillColor(Color.YELLOW);
                    break;
                case 3:
                    set1.setColor(Color.GREEN);
                    set1.setFillColor(Color.GREEN);
                    break;
            }*/