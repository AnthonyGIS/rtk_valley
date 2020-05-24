package com.weng.rtkvalley.ui.banner_show;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.google.android.material.snackbar.Snackbar;
import com.weng.rtkvalley.R;
import com.weng.rtkvalley.ui.banner_show.adapter.*;
import com.weng.rtkvalley.ui.banner_show.bean.DataBean;
import com.weng.rtkvalley.ui.banner_show.ui.VideoActivity;
import com.weng.rtkvalley.ui.banner_show.ui.Vp2FragmentRecyclerviewActivity;
import com.youth.banner.Banner;
import com.youth.banner.config.BannerConfig;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.indicator.RectangleIndicator;
import com.youth.banner.indicator.RoundLinesIndicator;
import com.youth.banner.listener.OnPageChangeListener;
import com.youth.banner.transformer.AlphaPageTransformer;
import com.youth.banner.transformer.ZoomOutPageTransformer;
import com.youth.banner.util.BannerUtils;


@SuppressWarnings({"rawtypes", "unchecked", "CodeBlock2Expr", "Convert2Lambda"})
public class BannerShowFragment extends Fragment implements OnPageChangeListener {
    private static final String TAG = "banner_log";
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.indicator)
    RoundLinesIndicator indicator;
    @BindView(R.id.banner2)
    Banner banner2;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout refresh;

    private Unbinder mUnbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_banner_show, container, false);
        // butterKnife在Fragment中的使用， https://blog.csdn.net/dummyo/article/details/89698817
        mUnbinder = ButterKnife.bind(this, v);

        initBanner();

        return v;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void initBanner()
    {

        //设置适配器
        ImageAdapter adapter = new ImageAdapter(DataBean.getTestData());
        banner.setAdapter(adapter);
        //设置指示器
        banner.setIndicator(new CircleIndicator(this.getContext()));
        //设置点击事件
        banner.setOnBannerListener((data, position) -> {
            Snackbar.make(banner, ((DataBean) data).title, Snackbar.LENGTH_SHORT).show();
        });
        //添加切换监听
        banner.addOnPageChangeListener(this);
        //圆角
        banner.setBannerRound(BannerUtils.dp2px(5));

        //魅族效果
        //banner.setBannerGalleryMZ(20);

        //实现1号店和淘宝头条类似的效果
        banner2.setAdapter(new TopLineAdapter(DataBean.getTestData2()))
                .setOrientation(Banner.VERTICAL)
                .setPageTransformer(new ZoomOutPageTransformer())
                .setOnBannerListener((data, position) -> {
                    Snackbar.make(banner, ((DataBean) data).title, Snackbar.LENGTH_SHORT).show();
                });

        //和下拉刷新配套使用
        refresh.setOnRefreshListener(() -> {
            //模拟网络请求需要3秒，请求完成，设置setRefreshing 为false
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    refresh.setRefreshing(false);
                    //给banner重新设置数据
                    banner.setDatas(DataBean.getTestData2());
                    //对setdatas不满意？你可以自己控制数据，可以参考setDatas()的实现修改
                    //adapter.updateData(DataBean.getTestData2());
                }
            }, 3000);
        });
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.e(TAG, "onPageSelected:" + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    /**
     * 如果你需要考虑更好的体验，可以这么操作
     */
    @Override
    public void onStart() {
        super.onStart();
        banner.start();
        banner2.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stop();
        banner2.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        banner.destroy();
        banner2.destroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    // 界面上的按钮事件
    @OnClick({R.id.style_image, R.id.style_image_title, R.id.style_image_title_num, R.id.style_multiple,
            R.id.style_net_image, R.id.change_indicator, R.id.gallery,
            R.id.vp_banner, R.id.banner_video})
    public void click(View view) {
        indicator.setVisibility(View.GONE);
        switch (view.getId()) {
            case R.id.style_image:
                refresh.setEnabled(true);
                banner.setAdapter(new ImageAdapter(DataBean.getTestData()));
                banner.setIndicator(new CircleIndicator(this.getContext()));
                banner.setIndicatorGravity(IndicatorConfig.Direction.CENTER);
                break;
            case R.id.style_image_title:
                refresh.setEnabled(true);
                banner.setAdapter(new ImageTitleAdapter(DataBean.getTestData()));
                banner.setIndicator(new CircleIndicator(this.getContext()));
                banner.setIndicatorGravity(IndicatorConfig.Direction.RIGHT);
                banner.setIndicatorMargins(new IndicatorConfig.Margins(0, 0,
                        BannerConfig.INDICATOR_MARGIN, (int) BannerUtils.dp2px(12)));
                break;
            case R.id.style_image_title_num:
                refresh.setEnabled(true);
                banner.setAdapter(new ImageTitleNumAdapter(DataBean.getTestData()));
                banner.removeIndicator();
                break;
            case R.id.style_multiple:
                refresh.setEnabled(true);
                banner.setAdapter(new MultipleTypesAdapter(this.getContext(), DataBean.getTestData()));
                banner.setIndicator(new RectangleIndicator(this.getContext()));
                banner.setIndicatorNormalWidth((int) BannerUtils.dp2px(12));
                banner.setIndicatorSpace((int) BannerUtils.dp2px(4));
                banner.setIndicatorRadius(0);
                break;
            case R.id.style_net_image:
                refresh.setEnabled(false);
                banner.setAdapter(new ImageNetAdapter(DataBean.getTestData3()));
                banner.setIndicator(new RoundLinesIndicator(this.getContext()));
                banner.setIndicatorSelectedWidth((int) BannerUtils.dp2px(15));
                break;
            case R.id.change_indicator:
                indicator.setVisibility(View.VISIBLE);
                //在布局文件中使用指示器，这样更灵活
                banner.setIndicator(indicator, false);
                banner.setIndicatorSelectedWidth((int) BannerUtils.dp2px(15));
                break;
            case R.id.gallery:
                refresh.setEnabled(false);
                banner.setAdapter(new ImageNetAdapter(DataBean.getTestData3()));

                //添加画廊效果(可以和其他PageTransformer组合使用，比如AlphaPageTransformer，注意但和其他带有缩放的PageTransformer会显示冲突)
                banner.setBannerGalleryEffect(18,10);
                //添加透明效果(画廊配合透明效果更棒)
                banner.addPageTransformer(new AlphaPageTransformer());
                break;
            case R.id.vp_banner:
                startActivity(new Intent(this.getContext(), Vp2FragmentRecyclerviewActivity.class));
                break;
            case R.id.banner_video:
                startActivity(new Intent(this.getContext(), VideoActivity.class));
                break;
        }
    }
}
