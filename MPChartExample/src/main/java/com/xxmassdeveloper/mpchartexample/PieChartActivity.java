
package com.xxmassdeveloper.mpchartexample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.xxmassdeveloper.mpchartexample.notimportant.DemoBase;

import java.util.ArrayList;

public class PieChartActivity extends DemoBase implements OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    private PieChart pieChart;
    private SeekBar seekBarX, seekBarY;
    private TextView tvX, tvY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_piechart);

        setTitle("PieChartActivity");

        tvX = findViewById(R.id.tvXMax);
        tvY = findViewById(R.id.tvYMax);

        seekBarX = findViewById(R.id.seekBar1);
        seekBarY = findViewById(R.id.seekBar2);

        seekBarX.setOnSeekBarChangeListener(this);
        seekBarY.setOnSeekBarChangeListener(this);

        pieChart = findViewById(R.id.chart1);
        pieChart.setUsePercentValues(true);//true-?????????????????????????????????100%???false-?????????????????????(entry.getY())
        pieChart.getDescription().setEnabled(false);//?????????????????????
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);//????????????????????????????????????????????????????????????????????????????????????1f

        pieChart.setCenterTextTypeface(tfLight);//????????????????????????
        pieChart.setCenterText(generateCenterSpannableText());//??????????????????

        pieChart.setDrawHoleEnabled(true);//????????????????????????true-?????????default:true
        pieChart.setHoleColor(Color.WHITE);//????????????????????????

        pieChart.setTransparentCircleColor(Color.WHITE);//??????????????????????????????
        pieChart.setTransparentCircleAlpha(110);//?????????????????????????????????

        pieChart.setHoleRadius(58f);//????????????????????????
        pieChart.setTransparentCircleRadius(61f);//???????????????????????????????????????=?????????*????????????/100

        pieChart.setDrawCenterText(true);//????????????????????????

        pieChart.setRotationAngle(0);//???????????????????????????????????????270???????????????
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);//?????????????????????????????????,Default: true
        pieChart.setHighlightPerTapEnabled(true);//?????????????????????????????????????????????,Default: true

        // chart.setUnit(" ???");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        pieChart.setOnChartValueSelectedListener(this);

        seekBarX.setProgress(4);
        seekBarY.setProgress(10);

        pieChart.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        pieChart.setEntryLabelColor(Color.WHITE);//????????????????????????????????????
        pieChart.setEntryLabelTypeface(tfRegular);//????????????????????????????????????
        pieChart.setEntryLabelTextSize(12f);//????????????????????????????????????

        //pieChart.setDrawRoundedSlices(true);//???????????????????????????
        //pieChart.setCenterTextOffset(50f,80f);//????????????????????????x??????y??????????????????,??????dp,Default x = 0, y = 0
        //pieChart.setCenterTextRadiusPercent(40);//???????????????????????????????????????????????????,??????100%???????????????????????????????????????
//        pieChart.setDrawSlicesUnderHole(true);//?????????
//        pieChart.setMaxAngle(180);//?????????????????????????????????????????????????????????360???????????????????????????????????????180??????????????????????????????????????????????????????????????????90???~360???
//        pieChart.setMinAngleForSlices(10f);//?????????
    }

    private void setData(int count, float range) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count ; i++) {
            entries.add(new PieEntry((float) ((Math.random() * range) + range / 5),
                    parties[i % parties.length],
                    getResources().getDrawable(R.drawable.star)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(tfLight);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.viewGithub: {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/com/xxmassdeveloper/mpchartexample/PieChartActivity.java"));
                startActivity(i);
                break;
            }
            case R.id.actionToggleValues: {
                for (IDataSet<?> set : pieChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                pieChart.invalidate();
                break;
            }
            case R.id.actionToggleIcons: {
                for (IDataSet<?> set : pieChart.getData().getDataSets())
                    set.setDrawIcons(!set.isDrawIconsEnabled());

                pieChart.invalidate();
                break;
            }
            case R.id.actionToggleHole: {
                if (pieChart.isDrawHoleEnabled())
                    pieChart.setDrawHoleEnabled(false);
                else
                    pieChart.setDrawHoleEnabled(true);
                pieChart.invalidate();
                break;
            }
            case R.id.actionToggleMinAngles: {
                if (pieChart.getMinAngleForSlices() == 0f)
                    pieChart.setMinAngleForSlices(36f);
                else
                    pieChart.setMinAngleForSlices(0f);
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
                break;
            }
            case R.id.actionToggleCurvedSlices: {
                boolean toSet = !pieChart.isDrawRoundedSlicesEnabled() || !pieChart.isDrawHoleEnabled();
                pieChart.setDrawRoundedSlices(toSet);
                if (toSet && !pieChart.isDrawHoleEnabled()) {
                    pieChart.setDrawHoleEnabled(true);
                }
                if (toSet && pieChart.isDrawSlicesUnderHoleEnabled()) {
                    pieChart.setDrawSlicesUnderHole(false);
                }
                pieChart.invalidate();
                break;
            }
            case R.id.actionDrawCenter: {
                if (pieChart.isDrawCenterTextEnabled())
                    pieChart.setDrawCenterText(false);
                else
                    pieChart.setDrawCenterText(true);
                pieChart.invalidate();
                break;
            }
            case R.id.actionToggleXValues: {

                pieChart.setDrawEntryLabels(!pieChart.isDrawEntryLabelsEnabled());
                pieChart.invalidate();
                break;
            }
            case R.id.actionTogglePercent:
                pieChart.setUsePercentValues(!pieChart.isUsePercentValuesEnabled());
                pieChart.invalidate();
                break;
            case R.id.animateX: {
                pieChart.animateX(1400);
                break;
            }
            case R.id.animateY: {
                pieChart.animateY(1400);
                break;
            }
            case R.id.animateXY: {
                pieChart.animateXY(1400, 1400);
                break;
            }
            case R.id.actionToggleSpin: {
                pieChart.spin(1000, pieChart.getRotationAngle(), pieChart.getRotationAngle() + 360, Easing.EaseInOutCubic);
                break;
            }
            case R.id.actionSave: {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    saveToGallery();
                } else {
                    requestStoragePermission(pieChart);
                }
                break;
            }
        }
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText(String.valueOf(seekBarX.getProgress()));
        tvY.setText(String.valueOf(seekBarY.getProgress()));

        setData(seekBarX.getProgress(), seekBarY.getProgress());
    }

    @Override
    protected void saveToGallery() {
        saveToGallery(pieChart, "PieChartActivity");
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
}
