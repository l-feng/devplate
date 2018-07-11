package com.example.administrator.materialdesign.activity;


import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.administrator.materialdesign.R;
import com.example.administrator.materialdesign.adapter.viewpaper.ViewPagerAdapter;
import com.example.administrator.materialdesign.fragment.HomeFragment;
import com.example.administrator.materialdesign.fragment.SecondFragment;
import com.example.administrator.materialdesign.fragment.ThirdFragment;
import com.example.administrator.materialdesign.utils.ActivityUtils;
import com.example.administrator.materialdesign.utils.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;

    private DrawerLayout mDrawerLayout;

    private NavigationView mNavigationView;

    private    PopupWindow mPopupWindow;
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private ViewPagerAdapter viewPagerAdapter;
    private boolean mIsExit;
    private MenuItem menuItem;
    private boolean isBack=false;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityUtils.setStatusBarColor(this, R.color.common_color);//设置状态栏颜色
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBar mActionBar = getSupportActionBar();

        if(mActionBar!=null)
        {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
            mActionBar.setTitle("动态");
        }

        mNavigationView = (NavigationView)findViewById(R.id.nav_view);


        viewPager=findViewById(R.id.view_pager);
        bottomNavigationView=findViewById(R.id.navigation_view);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new HomeFragment());
        viewPagerAdapter.addFragment(new SecondFragment());
        viewPagerAdapter.addFragment(new ThirdFragment());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(0);


        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {

                mDrawerLayout.closeDrawers();
                switch (item.getItemId()) {
                     case  R.id.nav_call:
                         Toast.makeText(MainActivity.this, "加载第一个界面", Toast.LENGTH_SHORT).show();
                         break;
                    case R.id.nav_friends:
                        Toast.makeText(MainActivity.this, "加载第二个界面", Toast.LENGTH_SHORT).show();
                   //     mDrawerLayout.
                        break;
                    default:
                }
                return true;
            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        viewPager.setCurrentItem(0);//首页
                        return true;
                    case R.id.navigation_community:
                        viewPager.setCurrentItem(1);//社区
                        return true;
                    case R.id.navigation_shopCart:
                        viewPager.setCurrentItem(2);//购物车
                        return true;
                    case R.id.navigation_user:
                        viewPager.setCurrentItem(3);//我的
                        return true;
                }
                return false;
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //Toast.makeText(MainActivity.this, "悬浮按钮被点击了", Toast.LENGTH_SHORT).show();

                Snackbar.make(view, "你好！", Snackbar.LENGTH_SHORT)
                        .setAction("发送", new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "消息已发送！", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                Toast.makeText(this, "按钮被点击了", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "按钮被点击了", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                popUpMyOverflow();
                break;

            default:
        }
        return true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                //若当前不在主页，则先返回主页
                if (viewPager.getCurrentItem() != 0) {
                    viewPager.setCurrentItem(0);
                    return false;
                }
                // 双击返回桌面，默认返回true，调用finish()
                if (!isBack) {
                    isBack = true;
                    Toast.makeText(this,"再按一次返回键回到桌面",Toast.LENGTH_LONG).show();
                    bottomNavigationView.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            isBack = false;
                        }
                    }, 2000);
                    return false;
                }

            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }






    public void popUpMyOverflow() {
        //获取状态栏高度
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        //状态栏高度+toolbar的高度
        int yOffset = frame.top + toolbar.getHeight();
        if (null == mPopupWindow) {
            //初始化PopupWindow的布局
            View popView = getLayoutInflater().inflate(R.layout.action_overflow_popwindow, null);
            //popView即popupWindow的布局，ture设置focusAble.
            mPopupWindow = new PopupWindow(popView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
            //必须设置BackgroundDrawable后setOutsideTouchable(true)才会有效
            mPopupWindow.setBackgroundDrawable(new ColorDrawable());
            //点击外部关闭。
            mPopupWindow.setOutsideTouchable(true);
            //设置一个动画。
            mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
            //设置Gravity，让它显示在右上角。
            mPopupWindow.showAtLocation(toolbar, Gravity.RIGHT | Gravity.TOP, 0, yOffset);
            //设置item的点击监听
            popView.findViewById(R.id.ll_item1).setOnClickListener(this);
            popView.findViewById(R.id.ll_item2).setOnClickListener(this);
            popView.findViewById(R.id.ll_item3).setOnClickListener(this);
        } else {
            mPopupWindow.showAtLocation(toolbar, Gravity.RIGHT | Gravity.TOP, 0, yOffset);
        }

    }

    @Override
    public void onClick(View view) {

    }
}
