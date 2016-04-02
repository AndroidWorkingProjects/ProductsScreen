package flowlayout.rahul.com.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ItemsFlowLayout extends FlowLayout implements FlowAdapter.OnDataChangedListener
{
    private FlowAdapter mItemsAdapter;
    private static final String TAG = "ItemsFlowLayout";
    private MotionEvent mMotionEvent;

    public ItemsFlowLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ItemsFlowLayout);
        ta.recycle();
        setClickable(true);
    }

    public ItemsFlowLayout(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public ItemsFlowLayout(Context context)
    {
        this(context, null);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++)
        {
            ItemView itemView = (ItemView) getChildAt(i);
            if (itemView.getVisibility() == View.GONE) continue;
            if (itemView.getItemView().getVisibility() == View.GONE)
            {
                itemView.setVisibility(View.GONE);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public interface OnSelectListener
    {
        void onSelected(Set<Integer> selectPosSet);
    }

    public interface OnItemClickListener
    {
        boolean onItemClick(View view, int position, FlowLayout parent);
    }

    private OnItemClickListener mOnItemClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        mOnItemClickListener = onItemClickListener;
        if (mOnItemClickListener != null) setClickable(true);
    }


    public void setAdapter(FlowAdapter adapter)
    {
        mItemsAdapter = adapter;
        mItemsAdapter.setOnDataChangedListener(this);
        changeAdapter();

    }

    private void changeAdapter()
    {
        removeAllViews();
        FlowAdapter adapter = mItemsAdapter;
        ItemView itemViewContainer = null;
        HashSet preCheckedList = mItemsAdapter.getPreCheckedList();
        for (int i = 0; i < adapter.getCount(); i++)
        {
            View itemView = adapter.getView(this, i, adapter.getItem(i));
            if( itemView == null) continue;

            itemViewContainer = new ItemView(getContext());
            itemView.setDuplicateParentStateEnabled(true);
            if (itemView.getLayoutParams() != null)
            {
                itemViewContainer.setLayoutParams(itemView.getLayoutParams());
            } else
            {
                MarginLayoutParams lp = new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                lp.setMargins(dip2px(getContext(), 2),
                        dip2px(getContext(), 2),
                        dip2px(getContext(), 2),
                        dip2px(getContext(), 2));
                itemViewContainer.setLayoutParams(lp);
            }
            itemViewContainer.addView(itemView);
            addView(itemViewContainer);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_UP)
        {
            mMotionEvent = MotionEvent.obtain(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick()
    {
        if (mMotionEvent == null) return super.performClick();

        int x = (int) mMotionEvent.getX();
        int y = (int) mMotionEvent.getY();
        mMotionEvent = null;

        ItemView child = findChild(x, y);
        int pos = findPosByView(child);
        if (child != null)
        {
            if (mOnItemClickListener != null)
            {
                return mOnItemClickListener.onItemClick(child.getItemView(), pos, this);
            }
        }
        return true;
    }

    private int findPosByView(View child)
    {
        final int cCount = getChildCount();
        for (int i = 0; i < cCount; i++)
        {
            View v = getChildAt(i);
            if (v == child) return i;
        }
        return -1;
    }

    private ItemView findChild(int x, int y)
    {
        final int cCount = getChildCount();
        for (int i = 0; i < cCount; i++)
        {
            ItemView v = (ItemView) getChildAt(i);
            if (v.getVisibility() == View.GONE) continue;
            Rect outRect = new Rect();
            v.getHitRect(outRect);
            if (outRect.contains(x, y))
            {
                return v;
            }
        }
        return null;
    }

    @Override
    public void onChanged()
    {
        changeAdapter();
    }

    public static int dip2px(Context context, float dpValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
