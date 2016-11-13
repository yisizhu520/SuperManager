package wang.steven.supermanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.rey.material.widget.Spinner;

import java.util.List;

import wang.steven.supermanager.db.Product;
import wang.steven.supermanager.db.ProductDao;

public class RecordSaleActivity extends AppCompatActivity {

    Spinner spinner1;
    TextView price1;
    EditText quantity1;
    TextView amount1;
    Spinner spinner2;
    TextView price2;
    EditText quantity2;
    TextView amount2;
    Spinner spinner3;
    TextView price3;
    EditText quantity3;
    TextView amount3;
    Spinner spinner4;
    TextView price4;
    EditText quantity4;
    TextView amount4;
    TextView total;
    ProductDao productDao;

    List<Product> products1;
    List<Product> products2;
    List<Product> products3;
    List<Product> products4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productDao = new ProductDao();
        setContentView(R.layout.activity_record_sale);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        price1 = (TextView) findViewById(R.id.price1);
        quantity1 = (EditText) findViewById(R.id.quantity1);
        amount1 = (TextView) findViewById(R.id.amount1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        price2 = (TextView) findViewById(R.id.price2);
        quantity2 = (EditText) findViewById(R.id.quantity2);
        amount2 = (TextView) findViewById(R.id.amount2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        price3 = (TextView) findViewById(R.id.price3);
        quantity3 = (EditText) findViewById(R.id.quantity3);
        amount3 = (TextView) findViewById(R.id.amount3);
        spinner4 = (Spinner) findViewById(R.id.spinner4);
        price4 = (TextView) findViewById(R.id.price4);
        quantity4 = (EditText) findViewById(R.id.quantity4);
        amount4 = (TextView) findViewById(R.id.amount4);
        total = (TextView) findViewById(R.id.total);
        initProduct1();
        initProduct2();
        initProduct3();
        initProduct4();
    }

    void initProduct1(){
        products1 = productDao.getProductsByType("Coffee");
        String[] names = new String[products1.size()];
        for (int i = 0; i < products1.size(); i++) {
            names[i] = products1.get(i).getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row_spn, names);
        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                Product p = products1.get(position);
                price1.setText(p.getPrice()+"");
                quantity1.setText(10+"");
                amount1.setText(getFloatValue(price1)*getIntValue(quantity1)+"");
            }
        });
        quantity1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                amount1.setText(getFloatValue(price1)*getIntValue(quantity1)+"");
                resetTotal();
            }
        });
    }
    void initProduct2(){
        products2 = productDao.getProductsByType("Bread");
        String[] names = new String[products2.size()];
        for (int i = 0; i < products2.size(); i++) {
            names[i] = products2.get(i).getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row_spn, names);
        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                Product p = products2.get(position);
                price2.setText(p.getPrice()+"");
                quantity2.setText(20+"");
                amount2.setText(getFloatValue(price2)*getIntValue(quantity2)+"");
            }
        });
        quantity2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                amount2.setText(getFloatValue(price2)*getIntValue(quantity2)+"");
                resetTotal();
            }
        });
    }
    void initProduct3(){
        products3 = productDao.getProductsByType("Baking");
        String[] names = new String[products3.size()];
        for (int i = 0; i < products3.size(); i++) {
            names[i] = products3.get(i).getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row_spn, names);
        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        spinner3.setAdapter(adapter);
        spinner3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                Product p = products3.get(position);
                price3.setText(p.getPrice()+"");
                quantity3.setText(30+"");
                amount3.setText(getFloatValue(price3)*getIntValue(quantity3)+"");
            }
        });
        quantity3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                amount3.setText(getFloatValue(price3)*getIntValue(quantity3)+"");
                resetTotal();
            }
        });
    }
    void initProduct4(){
        products4 = productDao.getProductsByType("Laundry");
        String[] names = new String[products4.size()];
        for (int i = 0; i < products4.size(); i++) {
            names[i] = products4.get(i).getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row_spn, names);
        adapter.setDropDownViewResource(R.layout.row_spn_dropdown);
        spinner4.setAdapter(adapter);
        spinner4.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                Product p = products4.get(position);
                price4.setText(p.getPrice()+"");
                quantity4.setText(40+"");
                amount4.setText(getFloatValue(price4)*getIntValue(quantity4)+"");
            }
        });
        quantity4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                amount4.setText(getFloatValue(price4)*getIntValue(quantity4)+"");
                resetTotal();
            }
        });
    }

    void resetTotal(){
        total.setText("$"+(getFloatValue(amount1)+getFloatValue(amount2)
                +getFloatValue(amount3)+getFloatValue(amount4))+"");
    }

    private int getIntValue(TextView tv){
        return Integer.parseInt(tv.getText().toString());
    }

    private float getFloatValue(TextView et){
        return Float.parseFloat(et.getText().toString());
    }
}
