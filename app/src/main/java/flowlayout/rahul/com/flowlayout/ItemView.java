package flowlayout.rahul.com.flowlayout;

import android.content.Context;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;

public class ItemView extends FrameLayout {
    private boolean isChecked;
    private static final int[] CHECK_STATE = new int[]{android.R.attr.state_checked};

    public ItemView(Context context) {
        super(context);
    }

    public View getItemView() {
        return getChildAt(0);
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        int[] states = super.onCreateDrawableState(extraSpace + 1);

        mergeDrawableStates(states, CHECK_STATE);

        return states;
    }


}
