package flowlayout.rahul.com.flowlayout;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

public class AllProductsActivity extends AppCompatActivity implements ScrollViewTestFragment.EditClickInterface
{
    private EditText searchText;
    private Button edit;
    private FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        edit = (Button)findViewById(R.id.edit);
        searchText = (EditText)findViewById(R.id.search);

        frame = (FrameLayout)findViewById(R.id.frame);
        final ScrollViewTestFragment scrollFragment = new ScrollViewTestFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame, scrollFragment, "ScrollView")
                .disallowAddToBackStack()
                .commit();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollFragment.editClicked();
            }
        });
    }


    @Override
    public void editClicked(boolean editVal) {
        if(editVal==true)
            edit.setText("Done");
        else
            edit.setText("Edit");
    }
}
