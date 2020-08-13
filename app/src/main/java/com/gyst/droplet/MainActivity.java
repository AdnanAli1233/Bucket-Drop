package com.gyst.droplet;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import com.gyst.droplet.adapters.AdapterDrops;
import com.gyst.droplet.adapters.AddListener;
import com.gyst.droplet.adapters.CompleteListener;
import com.gyst.droplet.adapters.Divider;
import com.gyst.droplet.adapters.MarkListener;
import com.gyst.droplet.adapters.SimpleTouchCallback;
import com.gyst.droplet.beans.Drop;
import com.gyst.droplet.widgets.BucketRecyclerView;

//TODO add a layout manager for the RecyclerView
public class MainActivity extends AppCompatActivity implements MarkListener {

    public static final String TAG = "sajeel";
    Toolbar mToolbar;
    Button mBtnAdd;
    BucketRecyclerView mRecycler;
    Realm mRealm;
    RealmResults<Drop> mResults;
    AdapterDrops mAdapter;
    View emptyView;

    private View.OnClickListener mBtnAddListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            showDialogAdd();
        }
    };

    private AddListener addListener = new AddListener() {
        @Override
        public void add() {
            showDialogAdd();
        }
    };

    RealmChangeListener mChangeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object o) {
            Log.d(TAG, "onChange: was called");
            mAdapter.update(mResults);
        }
    };

    private CompleteListener mCompleteListener = new CompleteListener() {
        @Override
        public void onComplete(int position) {
//            mAdapter.
        }
    };
    private void showDialogAdd() {
        DialogAdd dialog = new DialogAdd();
        dialog.show(getSupportFragmentManager(), "Add");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRealm = Realm.getDefaultInstance();
        mResults = mRealm.where(Drop.class).findAllAsync();
        mResults.load();

        if(mResults!=null && mResults.size()>0){
            Log.e(TAG,""+mResults.size());
        }else{
            Log.e(TAG,"no result found");
        }

        emptyView = findViewById(R.id.empty_view);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mBtnAdd = (Button) findViewById(R.id.add_a_drop);
        mRecycler = (BucketRecyclerView) findViewById(R.id.ma_recyclerview);
        mRecycler.showIfEmpty(emptyView);
        mRecycler.hideIfEmpty(mToolbar);

        mAdapter = new AdapterDrops(this, mResults, addListener, mRealm,this);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.addItemDecoration(new Divider(this, LinearLayoutManager.VERTICAL));
        mRecycler.setAdapter(mAdapter);

        SimpleTouchCallback mSimpleTouchCallback = new SimpleTouchCallback(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(mSimpleTouchCallback);
        helper.attachToRecyclerView(mRecycler);

        mBtnAdd.setOnClickListener(mBtnAddListener);
        setSupportActionBar(mToolbar);
        initBackgroundImage();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mResults.addChangeListener(mChangeListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mResults.removeChangeListener(mChangeListener);
    }

    private void initBackgroundImage() {
        ImageView background = (ImageView) findViewById(R.id.background_image);
        Glide.with(this)
                .load(R.drawable.background)
                .centerCrop()
                .into(background);
    }

    @Override
    public void showDialogMark(int position) {
        MarkDialog mMarkDialog = new MarkDialog();
        mMarkDialog.show(getSupportFragmentManager(),"Mark");
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION", position);
        mMarkDialog.setArguments(bundle);
        mMarkDialog.setCompleteListener(mCompleteListener);
    }
}