package com.gyst.droplet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.gyst.droplet.adapters.CompleteListener;

public class MarkDialog extends DialogFragment {
    private ImageButton mButtonClose;
    private Button mButtonComplete;
    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.mark_completed:
                        markAsComplete();
                    break;
            }
            dismiss();
        }


    };
    private CompleteListener mCompleteListener;

    private void markAsComplete() {
        Bundle bundle = getArguments();
        if(mCompleteListener!=null && bundle!=null){
            mCompleteListener.onComplete(bundle.getInt("POSITION"));
            Toast.makeText(getActivity(), ""+bundle.getInt("POSITION"), Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_mark, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mButtonClose = view.findViewById(R.id.btn_close);
        mButtonComplete = view.findViewById(R.id.mark_completed);

        mButtonClose.setOnClickListener(mButtonClickListener);
        mButtonComplete.setOnClickListener(mButtonClickListener);


    }

    public void setCompleteListener(CompleteListener mCompleteListener) {
        this.mCompleteListener = mCompleteListener;
    }
}
