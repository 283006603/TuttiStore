package com.apptutti.tuttistore;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.apptutti.tuttistore.base.BaseActivity;
import com.apptutti.tuttistore.base.BaseFragment;
import com.apptutti.tuttistore.fragment.DiscoveryFragment;
import com.apptutti.tuttistore.fragment.GameRecoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.apptutti.tuttistore.R.id.main_rb_applist;
import static com.apptutti.tuttistore.R.id.main_rb_detail;
import static com.apptutti.tuttistore.R.id.main_rb_stats;

public class MainActivity extends BaseActivity {


    @BindView(R.id.main_ViewPager)
    ViewPager mainViewPager;
    @BindView(main_rb_applist)
    RadioButton mainRbApplist;
    @BindView(main_rb_stats)
    RadioButton mainRbStats;
    @BindView(main_rb_detail)
    RadioButton mainRbDetail;
    @BindView(R.id.main_RadioGroup)
    RadioGroup mainRadioGroup;
    private List<BaseFragment> fragments;
    private long lastBackTime = 0;

    @Override
    protected void initWidget(){

        initFragment();
        initView();
    }


    @Override
    protected int getLayoutId(){
        return R.layout.activity_main;
    }

    private void initView(){
        //http://blog.csdn.net/qq_35874987/article/details/54923050   ViewPager不可滑动
        mainRbApplist.setChecked(true);
        //初始化 适配器
        mainViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()){
            @Override
            public Fragment getItem(int position){
                return fragments.get(position);
            }

            @Override
            public int getCount(){
                return fragments.size();
            }
        });
        mainRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId){
                switch(checkedId){
                    case main_rb_applist:
                        mainViewPager.setCurrentItem(0, false);
                       /* mainRadioGroup.getChildAt(0).setBackgroundColor(Color.YELLOW);*/
                        break;
                    case main_rb_stats:
                        mainViewPager.setCurrentItem(1, false);
                        /*mainRadioGroup.getChildAt(1).setBackgroundColor(Color.BLUE);*/
                        break;
                    case main_rb_detail:
                        mainViewPager.setCurrentItem(2, false);
                        /*mainRadioGroup.getChildAt(2).setBackgroundColor(Color.GREEN);*/
                        break;
                }
            }
        });
        mainViewPager.setOffscreenPageLimit(4);//防止Viewpager 自动销毁
        mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
            }

            @Override
            public void onPageSelected(int position){
                ((RadioButton) mainRadioGroup.getChildAt(position)).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state){
            }
        });





    }

    private void initFragment(){
        fragments = new ArrayList<>();
        fragments.add(new GameRecoFragment().newInstance());
        fragments.add(new DiscoveryFragment().newInstance());
        fragments.add(new DiscoveryFragment().newInstance());
    }



    @Override
    public void onBackPressed() {
        long currentBackTime = System.currentTimeMillis();
        if (currentBackTime - lastBackTime > 2000) {
            Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
            lastBackTime = currentBackTime;
        } else {
            super.onBackPressed();
            System.exit(0);
        }
    }


}
