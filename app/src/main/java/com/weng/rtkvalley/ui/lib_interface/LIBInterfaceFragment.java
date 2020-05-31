package com.weng.rtkvalley.ui.lib_interface;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.weng.rtkvalley.R;
import com.weng.rtkvalley.ui.demo_pickview.PickViewMainActivity;


public class LIBInterfaceFragment extends Fragment {

    private Unbinder mUnbinder;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_libs, container, false);
        mUnbinder = ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }


    @OnClick({R.id.btn_lib_pick_view})
    public void click(View view) {
        switch (view.getId()) {
           case R.id.btn_lib_pick_view:
                startActivity(new Intent(this.getContext(), PickViewMainActivity.class));
                break;
        }
    }





}
