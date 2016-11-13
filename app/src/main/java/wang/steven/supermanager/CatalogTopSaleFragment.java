package wang.steven.supermanager;


import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import wang.steven.supermanager.db.DailySaleDao;
import wang.steven.supermanager.db.ProductProfitsInfo;
import wang.steven.supermanager.db.SaleTopVO;
import wang.steven.supermanager.util.DateUtil;

import static wang.steven.supermanager.util.DateUtil.getDateStr;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CatalogTopSaleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CatalogTopSaleFragment extends Fragment implements OnChartValueSelectedListener {


    BarChart barChart;
    DailySaleDao saleDao;
    Button fromDataBtn;
    Button toDataBtn;
    Button doneBtn;

    public CatalogTopSaleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CatalogTopSaleFragment.
     */
    public static CatalogTopSaleFragment newInstance() {
        CatalogTopSaleFragment fragment = new CatalogTopSaleFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saleDao = new DailySaleDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_catalog_top_sale, container, false);
        barChart = (BarChart) v.findViewById(R.id.chart);
        fromDataBtn = (Button) v.findViewById(R.id.btn_date_from);
        toDataBtn = (Button) v.findViewById(R.id.btn_date_to);
        doneBtn = (Button) v.findViewById(R.id.btn_done);
        initBarChart();
        initDateBtn();
        initChartData();
        return v;
    }

    private void initDateBtn() {
        fromDataBtn.setText(getDateStr(new Date()));
        toDataBtn.setText(getDateStr(new Date()));
        fromDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFromDatePickDialog();
            }
        });
        toDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToDatePickDialog();
            }
        });
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initChartData();
            }
        });

    }

    void showFromDatePickDialog(){
        Dialog.Builder builder = null;
        builder = new DatePickerDialog.Builder(R.style.Material_App_Dialog_DatePicker_Light){
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                DatePickerDialog dialog = (DatePickerDialog)fragment.getDialog();
                String date = DateUtil.getDateStr(dialog.getCalendar().getTime());
                fromDataBtn.setText(date);
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.positiveAction("OK")
                .negativeAction("CANCEL");
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getFragmentManager(), null);
    }

    void showToDatePickDialog(){
        Dialog.Builder builder = null;
        builder = new DatePickerDialog.Builder(R.style.Material_App_Dialog_DatePicker_Light){
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                DatePickerDialog dialog = (DatePickerDialog)fragment.getDialog();
                String date = DateUtil.getDateStr(dialog.getCalendar().getTime());
                toDataBtn.setText(date);
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.positiveAction("OK")
                .negativeAction("CANCEL");
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getFragmentManager(), null);
    }

    private void initBarChart() {
        // scaling can now only be done on x- and y-axis separately
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(false);
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(false);

        barChart.setDrawValueAboveBar(false);
        barChart.setHighlightFullBarEnabled(false);

        // change the position of the y-labels
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setValueFormatter(new MoneyAxisValueFormatter());
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        barChart.getAxisRight().setEnabled(false);
        leftAxis.setDrawGridLines(false);
        XAxis xLabels = barChart.getXAxis();
        xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);
        xLabels.setDrawGridLines(false);

        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);

        barChart.setOnChartValueSelectedListener(this);
    }

    public static final int TOP_COLORS[] = {R.color.top3, R.color.top2, R.color.top1};

    private List<SaleTopVO> data;

    private void initChartData() {
        data = saleDao.getSaleTopData("20156-11-01", toDataBtn.getText().toString());
        //TODO 需要改正过来
//        data = saleDao.getSaleTopData(fromDataBtn.getText().toString(), toDataBtn.getText().toString());
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            SaleTopVO vo = data.get(i);
            float[] profitArray = {
                    vo.sales.get(0).profits,
                    vo.sales.get(1).profits,
                    vo.sales.get(2).profits
            };
            yVals1.add(new BarEntry(i, profitArray, vo.typeName ));
        }
        BarDataSet dataSet = new BarDataSet(yVals1, "catalog profit top 3");
        dataSet.setColors(TOP_COLORS, getActivity());
        dataSet.setStackLabels(new String[]{"TOP3", "TOP2", "TOP1"});
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
//                int index = value > data.size() ? data.size() : (int) value;
//                index = index < 0 ? 0 : index;
                return data.get((int) value).typeName;
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(dataSet);

        BarData barData = new BarData(dataSets);
        barChart.setData(barData);
        barChart.animateY(1000);
        barChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        final ProductProfitsInfo info = data.get((int) e.getX()).sales.get(h.getStackIndex());
//        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.Material_App_BottomSheetDialog);
//        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_product_profit, null);
//        ViewUtil.setBackground(v, new ThemeDrawable(R.drawable.bg_window_light));
//        TextView name = (TextView) v.findViewById(R.id.name);
//        TextView type = (TextView) v.findViewById(R.id.type);
//        TextView profits = (TextView) v.findViewById(R.id.profits);
//        TextView volumes = (TextView) v.findViewById(R.id.volumes);
//        name.setText(info.productName);
//        type.setText(info.productType);
//        profits.setText(info.profits+"");
//        volumes.setText(info.volumes+"");
//        mBottomSheetDialog.contentView(v)
//                .show();
        Dialog.Builder builder = null;
        builder = new SimpleDialog.Builder(R.style.SimpleDialogLight){

            @Override
            protected void onBuildDone(final Dialog dialog) {
                dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView name = (TextView) dialog.findViewById(R.id.name);
                TextView type = (TextView) dialog.findViewById(R.id.type);
                TextView profits = (TextView) dialog.findViewById(R.id.profits);
                TextView volumes = (TextView) dialog.findViewById(R.id.volumes);
                name.setText(info.productName);
                name.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
                name.getPaint().setAntiAlias(true);//抗锯齿
                type.setText(info.productType);
                profits.setText(info.profits+"");
                volumes.setText(info.volumes+"");
                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //dialog.dismissImmediately();
                        Intent intent = new Intent(getActivity(),ProductDetailActivity.class);
                        intent.putExtra("productId",info.productId);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.title("details")
//                .positiveAction("CONNECT")
//                .negativeAction("CANCEL")
                .contentView(R.layout.dialog_product_profit);
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getFragmentManager(), null);
    }

    @Override
    public void onNothingSelected() {

    }

    public class MoneyAxisValueFormatter implements IAxisValueFormatter {

        private DecimalFormat mFormat;

        public MoneyAxisValueFormatter() {
            mFormat = new DecimalFormat("###,###,###,##0");
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mFormat.format(value) + " $";
        }

        @Override
        public int getDecimalDigits() {
            return 1;
        }
    }


}
