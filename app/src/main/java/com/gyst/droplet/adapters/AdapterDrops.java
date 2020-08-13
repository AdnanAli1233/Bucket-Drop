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

import io.realm.Realm;
import io.realm.RealmResults;
import com.gyst.droplet.R;
import com.gyst.droplet.beans.Drop;

public class AdapterDrops extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeListener {
    private LayoutInflater mInflater;
    private RealmResults<Drop> mResults;
    public static final String TAG="VIVZ";

    public static final int ITEM = 0;
    public static final int FOOTER = 1;

    AddListener addListener;
    Realm realm;
    MarkListener mMarkListener;
    public AdapterDrops(Context context, RealmResults<Drop> results, AddListener addListener, Realm realm, MarkListener markListener) {
        mInflater = LayoutInflater.from(context);
        update(results);
        this.addListener = addListener;
        this.realm = realm;
        mMarkListener = markListener;
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
            newHolder=  new FootHolder(view, addListener);
        }else{
            View view = mInflater.inflate(R.layout.row_drop, parent, false);
            newHolder= new DropHolder(view, mMarkListener);
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
        if(mResults== null || mResults.isEmpty()){
            return 0;
        }else {
            return mResults.size()+1;
        }
    }

    @Override
    public void onSwipe(int position) {
        if(position<mResults.size()){
            realm.beginTransaction();
            mResults.get(position).deleteFromRealm();
            realm.commitTransaction();
            notifyItemRemoved(position);
        }

    }

    public static class DropHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTextWhat;
        MarkListener mMarkListener;
        public DropHolder(View itemView, MarkListener mMarkListener) {
            super(itemView);
            mTextWhat = (TextView) itemView.findViewById(R.id.tv_what);
            itemView.setOnClickListener(this);
            this.mMarkListener = mMarkListener;
        }

        @Override
        public void onClick(View v) {
            mMarkListener.showDialogMark(getAdapterPosition());
        }
    }

    public static class FootHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        Button add;
        AddListener addListener;
        public FootHolder(View itemView, AddListener addListener) {
            super(itemView);
            add = (Button) itemView.findViewById(R.id.footer);
            add.setOnClickListener(this);
            this.addListener = addListener;
        }

        @Override
        public void onClick(View v) {
            addListener.add();
        }
    }
}