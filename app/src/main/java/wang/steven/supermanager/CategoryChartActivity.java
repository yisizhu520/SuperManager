package wang.steven.supermanager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

import wang.steven.supermanager.db.CategoryDailySale;
import wang.steven.supermanager.db.CategoryDailySaleDao;
import wang.steven.supermanager.util.DateUtil;

public class CategoryChartActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    PieChart mChart;
    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_chart);
        mChart = (PieChart) findViewById(R.id.chart);
        lineChart = (LineChart) findViewById(R.id.lineChart);
        initPieChart();
        initPieData();
        initLineChart();
    }



    private List<CategoryDailySale> infos;

    private void initPieData() {
        CategoryDailySaleDao dao = new CategoryDailySaleDao();
        infos =  dao.getProductProfits("2016-10-01","2016-11-30");
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        float sum = 0;
        for (int i = 0; i < infos.size(); i++) {
            sum += infos.get(i).profit;
        }
        for (int i = 0; i < infos.size() ; i++) {
            entries.add(new PieEntry(infos.get(i).profit,infos.get(i).categoryName,infos.get(i)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Category Profits");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<Integer>();
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
        data.setValueFormatter(new MyPercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
//        data.setValueTypeface(mTfLight);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

    }

    class MyPercentFormatter implements IValueFormatter{

        float sum = 0;

        public MyPercentFormatter(){
            for (int i = 0; i < infos.size(); i++) {
                sum += infos.get(i).profit;
            }
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            //CategoryDailySale info = infos.get((int) value);
            return (int)value + "("+ (int)(value/sum*100) +"%)";
        }
    }

    private void initPieChart() {
        mChart.setCenterText("Category Profits");
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);
       // mChart.setTransparentCircleColor(Color.WHITE);
        //mChart.setTransparentCircleAlpha(110);
        mChart.setHoleRadius(38f);
        mChart.getDescription().setEnabled(false);
       // mChart.setTransparentCircleRadius(61f);
        mChart.setDrawCenterText(false);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        //mChart.setEntryLabelTypeface(mTfRegular);
        mChart.setEntryLabelTextSize(12f);

    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {
        PieEntry entry = (PieEntry) e;
        CategoryDailySale info = (CategoryDailySale) entry.getData();
        showLine(info);
    }

    @Override
    public void onNothingSelected() {

    }

    private void initLineChart() {
        lineChart.setDrawGridBackground(false);
        // no description text
        lineChart.getDescription().setEnabled(false);
        // enable touch gestures
        lineChart.setTouchEnabled(true);
        // enable scaling and dragging
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(true);
        lineChart.getAxisRight().setEnabled(false);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0);
        xAxis.setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
    }

    private void showLine(CategoryDailySale cate){
        CategoryDailySaleDao dao = new CategoryDailySaleDao();
        final List<CategoryDailySale> items = dao.getCateProfitsByDate(cate.categoryId,"2016-10-01","2016-11-30");

        ArrayList<Entry> profitsValue = new ArrayList<Entry>();
        for (int i = 0; i < items.size(); i++) {
            profitsValue.add(new Entry(i, items.get(i).profit));
        }


        LineDataSet set1 = new LineDataSet(profitsValue, cate.categoryName);
        set1.setColor(Color.RED);
        set1.setCircleColor(Color.RED);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(false);
//        set1.setFormLineWidth(1f);
//        set1.setFormSize(15.f);
//        set1.setFillColor(Color.RED);


        lineChart.getXAxis().setGranularity(1f);
        lineChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int index = (int) value;
                index = index > 0 ? index : 0;
                index = index > items.size() - 1 ? items.size() - 1 : index;
                return DateUtil.getMonthAndDay(items.get(index).date);
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });

//        if (lineChart.getData() != null &&
//                lineChart.getData().getDataSetCount() > 0) {
//            ((LineData)lineChart.getData()).addDataSet(set1);
//            lineChart.getData().notifyDataChanged();
//            lineChart.notifyDataSetChanged();
//        }else{
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);
            // set data
            lineChart.setData(data);
            lineChart.animateXY(1000,1000);
//        }



    }


}
