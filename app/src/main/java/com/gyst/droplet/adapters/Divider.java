package com.gyst.droplet.adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyst.droplet.R;

public class Divider extends RecyclerView.ItemDecoration {
    private Drawable mDivider;
    private int orientation;

    public Divider(Context context, int orientation){
        mDivider = context.getResources().getDrawable(R.drawable.divider);
        if(orientation !=LinearLayoutManager.VERTICAL){
            throw new IllegalArgumentException("orientation should be vertical");
        }
        this.orientation = orientation;

    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (orientation == LinearLayoutManager.VERTICAL) {
            drawHorizontalDevider(c, parent, state);
        }
    }

    private void drawHorizontalDevider(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left,right, top, bottom;
        left = parent.getPaddingLeft();
        right = parent.getWidth() - parent.getPaddingRight();

        for(int i = 0 ; i < parent.getChildCount(); i++){
            View current = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) parent.getLayoutParams();
            top = current.getBottom() - layoutParams.bottomMargin;
            bottom = mDivider.getIntrinsicHeight() + top;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (orientation == LinearLayoutManager.VERTICAL){
            outRect.set(0,0,0,mDivider.getIntrinsicHeight());
        }
    }
}
