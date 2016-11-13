package wang.steven.supermanager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import wang.steven.supermanager.db.DailySaleDao;
import wang.steven.supermanager.db.Product;
import wang.steven.supermanager.db.ProductDao;
import wang.steven.supermanager.db.ProductProfitsInfo;
import wang.steven.supermanager.util.DateUtil;

public class ProductDetailActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    Button submit;
    LineChart lineChart;
    Product product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        TextView name = (TextView) findViewById(R.id.name);
        TextView type = (TextView) findViewById(R.id.type);
        TextView stocks = (TextView) findViewById(R.id.stocks);
        EditText ratio = (EditText) findViewById(R.id.ratio);
        lineChart = (LineChart) findViewById(R.id.chart);
        submit = (Button) findViewById(R.id.btn_submit);
        submit.setVisibility(View.GONE);
        initLineChart();
        ProductDao dao = new ProductDao();
        int productId = getIntent().getIntExtra("productId", -1);
        product = dao.getProductById(productId);
        initChartData();
        name.setText(product.getName());
        type.setText(product.getType());
        stocks.setText(product.getStock() + "");
        if (product.getStockNeedAlarm() == 0) {
            ratio.setText("");
            ratio.setHint("click to set");
        } else {
            ratio.setText(product.getMinStockRatio());
        }
        ratio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                submit.setVisibility(View.VISIBLE);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "commit successful", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initLineChart() {
        lineChart.setOnChartValueSelectedListener(this);
        lineChart.setDrawGridBackground(false);

        // no description text
        lineChart.getDescription().setEnabled(false);
        // enable touch gestures
        lineChart.setTouchEnabled(true);

        // enable scaling and dragging
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(true);

        // set an alternative background color
        // mChart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
//        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
//        mv.setChartView(lineChart); // For bounds control
//        lineChart.setMarker(mv); // Set the marker to the chart

        // x-axis limit line
//        LimitLine llXAxis = new LimitLine(10f, "Index 10");
//        llXAxis.setLineWidth(4f);
//        llXAxis.enableDashedLine(10f, 10f, 0f);
//        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        llXAxis.setTextSize(10f);
        lineChart.getAxisRight().setEnabled(false);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0);
        xAxis.setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        //xAxis.enableGridDashedLine(10f, 10f, 0f);
        //xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line
    }

    private List<ProductProfitsInfo> infos;

    private void initChartData() {
        DailySaleDao dao = new DailySaleDao();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) - 7);
        String fromDate = DateUtil.getDateStr(cal.getTime());
        infos = dao.getProductWeekProfits(product.getId(), fromDate, DateUtil.getDateStr(new Date()));

        ArrayList<Entry> profitsValue = new ArrayList<Entry>();
        ArrayList<Entry> volumeValue = new ArrayList<Entry>();
        for (int i = 0; i < infos.size(); i++) {
            profitsValue.add(new Entry(i, infos.get(i).profits));
            volumeValue.add(new Entry(i, infos.get(i).volumes));
        }
        LineDataSet set1 = new LineDataSet(profitsValue, "profit");
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

        LineDataSet set2 = new LineDataSet(volumeValue, "volume");
        set2.setColor(Color.YELLOW);
        set2.setCircleColor(Color.YELLOW);
        set2.enableDashedLine(10f, 5f, 0f);
        set2.setLineWidth(1f);
        set2.setCircleRadius(3f);
        set2.setDrawCircleHole(false);
        set2.setValueTextSize(9f);
        set2.setDrawFilled(false);

        lineChart.getXAxis().setGranularity(1f);
        lineChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int index = (int) value;
                index = index > 0 ? index : 0;
                index = index > infos.size() - 1 ? infos.size() - 1 : index;
                return infos.get(index).date;
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets
        dataSets.add(set2); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(dataSets);

        // set data
        lineChart.setData(data);
        lineChart.animateXY(1000,1000);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
