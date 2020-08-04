package com.gyst.droplet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import io.realm.Realm;
import com.gyst.droplet.beans.Drop;

/**
 * Created by vivz on 28/12/15.
 */
public class DialogAdd extends DialogFragment {

    private ImageButton mBtnClose;
    private EditText mInputWhat;
    private DatePicker mInputWhen;
    private Button mBtnAdd;

    private View.OnClickListener mBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id =v.getId();
            switch (id){
                case R.id.dialog_add:
                    addAction();
                    break;
            }
            dismiss();
        }
    };

    //TODO process date
    private void addAction() {
        //get the value of the 'goal' or 'to-do'
        //get the time when it was added
        String what=mInputWhat.getText().toString();
        long now = System.currentTimeMillis();
        Realm realm=Realm.getDefaultInstance();
        Drop drop= new Drop(what, now, 0, false);
        realm.beginTransaction();
        realm.copyToRealm(drop);
        realm.commitTransaction();
        realm.close();

    }

    public DialogAdd() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnClose = (ImageButton) view.findViewById(R.id.close_dialog);
        mInputWhat = (EditText) view.findViewById(R.id.dialog_tv);
        mInputWhen = (DatePicker) view.findViewById(R.id.dialog_datepicker);
        mBtnAdd = (Button) view.findViewById(R.id.dialog_add);

        mBtnClose.setOnClickListener(mBtnClickListener);
        mBtnAdd.setOnClickListener(mBtnClickListener);
    }
}