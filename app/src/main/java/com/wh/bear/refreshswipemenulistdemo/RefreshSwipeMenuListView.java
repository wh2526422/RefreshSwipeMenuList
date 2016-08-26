package com.wh.bear.refreshswipemenulistdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/8/25.
 */
public class RefreshSwipeMenuListView extends ListView {
    private static final String TAG = "RefreshSwipeMenu";
    Button l_menu;
    public RefreshSwipeMenuListView(Context context) {
        this(context, null);
    }

    public RefreshSwipeMenuListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshSwipeMenuListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateMenu(context);
    }

    private void inflateMenu(Context context) {
        l_menu = (Button) inflate(context,R.layout.menu_item,null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    float downX = 0;
    float downY = 0;
    int selectItemPos;

    public void setSelectItemMenuHide() {
        View childAt = getChildAt(selectItemPos - getFirstVisiblePosition());
        if (childAt != null) {
            childAt.scrollTo(0, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        View childAt = getChildAt(selectItemPos - getFirstVisiblePosition());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "ACTION_DOWN");
                downX = event.getX();
                downY = event.getY();
                selectItemPos = pointToPosition((int) downX, (int) downY);
                if (childAt != null) {
                    childAt.scrollTo(0, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "ACTION_UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "ACTION_MOVE");
                float moveX = event.getX();
                float moveY = event.getY();
                float xDiff = downX - moveX;
                float yDiff = downY - moveY;
                if (xDiff > 50 && yDiff < 50) {
                    Log.i(TAG, "getScrollX\t" + childAt.getScrollX());
                    childAt.scrollBy((int) xDiff, 0);
                    if (childAt.getScrollX() > 460) {
                        childAt.scrollTo(460, 0);
                        childAt.setTag(childAt.getId(), true);
                    }
                    return true;
                }
                if (childAt.getScrollX() > 0 && xDiff < -50 && yDiff < 50) {
                    childAt.scrollBy((int) xDiff, 0);
                    if (childAt.getScrollX() < 0) {
                        childAt.scrollTo(0, 0);
                        childAt.setTag(childAt.getId(), false);
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
