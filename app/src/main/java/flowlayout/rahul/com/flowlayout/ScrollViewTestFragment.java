package flowlayout.rahul.com.flowlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.Set;
import java.util.zip.Inflater;

public class ScrollViewTestFragment extends Fragment
{
    EditClickInterface editClickedInterface;
    boolean edit = false;
    private String[] mVals = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView","Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld"};

    private ItemsFlowLayout mFlowLayout;
    private FlowAdapter<String> mAdapter ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.activity_main_sc, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        final LayoutInflater mInflater = LayoutInflater.from(getActivity());
        mFlowLayout = (ItemsFlowLayout) view.findViewById(R.id.id_flowlayout);
        //mFlowLayout.setMaxSelectCount(3);

        mFlowLayout.setAdapter(mAdapter = new FlowAdapter<String>(mVals) {

            @Override
            public View getView(FlowLayout parent, final int position, String s) {

                LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.item,null);
                TextView textView = (TextView) view.findViewById(R.id.text);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Code to show product detail screen
                    }
                });

                ImageView delete = (ImageView) view.findViewById(R.id.delete);
                if(edit == false)
                    delete.setVisibility(View.GONE);
                else
                    delete.setVisibility(View.VISIBLE);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mVals[position] = null;
                        mAdapter.notifyDataChanged();
                    }
                });

                if(mVals[position] == null){
                    return null;
                }
                textView.setText(s);
                return view;
            }
        });

        //mAdapter.setSelectedList(1,3,5,7,8,9);
        mFlowLayout.setOnItemClickListener(new ItemsFlowLayout.OnItemClickListener()
        {
            @Override
            public boolean onItemClick(View view, int position, FlowLayout parent)
            {
                //Toast.makeText(getActivity(), mVals[position], Toast.LENGTH_SHORT).show();
                //view.setVisibility(View.GONE);
                return true;
            }
        });

        editClickedInterface = (EditClickInterface)getActivity();
    }

    public void editClicked(){
        if(edit == false){
            edit = true;
        }
        else{
            edit = false;
        }
        editClickedInterface.editClicked(edit);
        mAdapter.notifyDataChanged();
    }

    public interface EditClickInterface{
        public void editClicked(boolean edit);
    }
}
