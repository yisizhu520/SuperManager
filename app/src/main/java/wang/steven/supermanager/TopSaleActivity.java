package wang.steven.supermanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TopSaleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_sale);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,CatalogTopSaleFragment.newInstance())
                .commit();
    }
}
