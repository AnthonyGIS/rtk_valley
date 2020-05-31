package com.weng.rtkvalley.ui.demo_banner.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.weng.rtkvalley.R;
import com.weng.rtkvalley.ui.demo_banner.adapter.MyRecyclerViewAdapter;

public class BannerListFragment extends Fragment {
    private static int index;
    @BindView(R.id.net_rv)
    RecyclerView recyclerView;
    @BindView(R.id.text)
    TextView text;

    public static Fragment newInstance(int i) {
        index = i;
        return new com.weng.rtkvalley.ui.demo_banner.ui.BannerListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_recyclerview_banner, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        text.setText("当前页:"+index);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MyRecyclerViewAdapter(getActivity()));
    }

}
