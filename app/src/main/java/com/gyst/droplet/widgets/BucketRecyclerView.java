package com.gyst.droplet.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BucketRecyclerView extends RecyclerView {

    List<View> mEmptyViews= Collections.emptyList();
    List<View> mNonEmptyViews= Collections.emptyList();

    AdapterDataObserver adapterDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            toggleViews();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            toggleViews();

        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            toggleViews();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            toggleViews();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            toggleViews();
        }
    };

    private void toggleViews() {
        if(getAdapter()!= null && !mNonEmptyViews.isEmpty() && !mEmptyViews.isEmpty()){
            if(getAdapter().getItemCount()==0){
                for (View view: mEmptyViews) {
                    view.setVisibility(View.VISIBLE);
                }

                for (View view: mNonEmptyViews) {
                    view.setVisibility(View.GONE);
                }
                setVisibility(GONE);
            }else{
                for (View view: mEmptyViews) {
                    view.setVisibility(View.GONE);
                }

                for (View view: mNonEmptyViews) {
                    view.setVisibility(View.VISIBLE);
                }
                setVisibility(VISIBLE);
            }
        }
    }

    public BucketRecyclerView(@NonNull Context context) {
        super(context);

    }

    public BucketRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BucketRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        super.setAdapter(adapter);
        if(adapter!= null){
            adapter.registerAdapterDataObserver(adapterDataObserver);
        }
        adapterDataObserver.onChanged();
    }

    public void showIfEmpty(View ...views) {
        mEmptyViews = Arrays.asList(views);
    }

    public void hideIfEmpty(View ...views) {
        mNonEmptyViews = Arrays.asList(views);
    }
}
