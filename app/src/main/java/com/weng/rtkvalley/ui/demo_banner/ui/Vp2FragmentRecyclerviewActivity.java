package com.weng.rtkvalley.ui.demo_banner.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.material.tabs.TabLayout;
import com.weng.rtkvalley.R;
import com.weng.rtkvalley.ui.demo_banner.util.TabLayoutMediator;

public class Vp2FragmentRecyclerviewActivity extends AppCompatActivity {

    @BindView(R.id.vp2)
    ViewPager2 viewPager2;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vp2_fragment_recyclerview);
        ButterKnife.bind(this);

        viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                if (position==0) {
                    return BannerListFragment.newInstance(position);
                }else if (position==1){
                    return BlankFragment.newInstance();
                }else{
                    return BannerFragment.newInstance();
                }
            }

            @Override
            public int getItemCount() {
                return 3;
            }
        });

        new TabLayoutMediator(mTabLayout,viewPager2,(tab, position) -> {
            tab.setText("页面"+position);
        }).attach();
    }
}
