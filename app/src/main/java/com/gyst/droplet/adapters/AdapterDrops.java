package com.gyst.droplet.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import io.realm.RealmResults;
import com.gyst.droplet.R;
import com.gyst.droplet.beans.Drop;

public class AdapterDrops extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mInflater;
    private RealmResults<Drop> mResults;
    public static final String TAG="VIVZ";

    public static final int ITEM = 0;
    public static final int FOOTER = 1;

    public AdapterDrops(Context context, RealmResults<Drop> results) {
        mInflater = LayoutInflater.from(context);
        update(results);
    }

    public void update(RealmResults<Drop> results){
        mResults = results;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder newHolder;
        if(viewType==FOOTER){
            View view =mInflater.inflate(R.layout.footer, parent, false);
            newHolder=  new FootHolder(view);
        }else{
            View view = mInflater.inflate(R.layout.row_drop, parent, false);
            newHolder= new DropHolder(view);
        }
        return newHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if(mResults==null || position<mResults.size()){
                return ITEM;
        }else{
            return FOOTER;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof DropHolder){
            DropHolder dropholder = (DropHolder) holder;
            Drop drop=mResults.get(position);
            dropholder.mTextWhat.setText(drop.getWhat());
            Log.d(TAG, "onBindViewHolder: "+position);
        }

    }

    @Override
    public int getItemCount() {
        return mResults.size()+1;
    }

    public static class DropHolder extends RecyclerView.ViewHolder {

        TextView mTextWhat;
        public DropHolder(View itemView) {
            super(itemView);
            mTextWhat = (TextView) itemView.findViewById(R.id.tv_what);
        }
    }

    public static class FootHolder extends RecyclerView.ViewHolder {

        Button add;
        public FootHolder(View itemView) {
            super(itemView);
            add = (Button) itemView.findViewById(R.id.footer);
        }
    }
}