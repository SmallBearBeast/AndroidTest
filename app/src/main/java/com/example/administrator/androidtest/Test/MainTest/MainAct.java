package com.example.administrator.androidtest.Test.MainTest;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bear.libcomponent.ComponentAct;
import com.bear.libkv.SpVal.SpHelper;
import com.bear.libkv.MmkvVal.MmkvVal;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.AspectTest.AspectJTestComponent;
import com.example.administrator.androidtest.Test.MainTest.BottomSheetTest.BottomSheetTestComponent;
import com.example.administrator.androidtest.Test.MainTest.BottomViewTest.BottomViewTestComponent;
import com.example.administrator.androidtest.Test.MainTest.BusTest.BusTestComponent;
import com.example.administrator.androidtest.Test.MainTest.CaseViewTest.CaseViewComponent;
import com.example.administrator.androidtest.Test.MainTest.CoordinatorLayoutTest.BehaviorTestComponent;
import com.example.administrator.androidtest.Test.MainTest.CoordinatorLayoutTest.CoordinatorLayoutTestComponent;
import com.example.administrator.androidtest.Test.MainTest.DialogTest.DialogTestComponent;
import com.example.administrator.androidtest.Test.MainTest.EditTextTest.EditTextTestComponent;
import com.example.administrator.androidtest.Test.MainTest.MarqueeTest.MarqueeComponent;
import com.example.administrator.androidtest.Widget.FullTextView.FullTextView;
import com.example.administrator.androidtest.Widget.FullTextView.TextOpt;
import com.example.administrator.androidtest.Widget.LikeView.LikeView;
import com.example.administrator.androidtest.Widget.LoopViewPager.LoopViewPager;
import com.example.libbase.Util.ToastUtil;

import java.util.Random;

public class MainAct extends ComponentAct {
    private FullTextView ftvFullText;
    private LikeView likeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ftvFullText = findViewById(R.id.fullTextView);
        likeView = findViewById(R.id.likeView);
        testGetSpVal();
        initLoopViewPager();
        regComponent(new BehaviorTestComponent());
        regComponent(new CoordinatorLayoutTestComponent());
        regComponent(new BottomSheetTestComponent());
        regComponent(new BusTestComponent());
        regComponent(new AspectJTestComponent());
        regComponent(new DialogTestComponent());
        regComponent(new MarqueeComponent());
        regComponent(new CaseViewComponent());
        regComponent(new EditTextTestComponent());
        regComponent(new BottomViewTestComponent());
    }

    @Override
    protected int layoutId() {
        return R.layout.act_other_test;
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.fullTextButton:
                testFullTextView();
                break;

            case R.id.likeView:
                testLikeView();
                break;

            case R.id.startBgServiceButton:
                testStartBgService();
                break;

            case R.id.stopBgServiceButton:
                testStopBgService();
                break;

            case R.id.setSpValButton:
                testSetSpVal();
                break;

            case R.id.getSpValButton:
                testGetSpVal();
                break;

            case R.id.spToMmkvButton:
                testSpToMmkv();
                break;

            case R.id.showMoveToMMKVButton:
                testShowMoveToMMKV();
                break;

            default:
                break;
        }
    }

    private void testStopBgService() {
        //                BackgroundService.stopByReceiver(this);
        BackgroundService.stop(this, BackgroundService.getNotificationId());
//                MainHandlerUtil.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        BackgroundService.stop(OtherTestAct.this, 1);
//                        BackgroundService.start(OtherTestAct.this, BackgroundService.STOP);
//                        stopService(new Intent(OtherTestAct.this, BackgroundService.class));
//
//                    }
//                }, 5* 1000);
    }

    private void testStartBgService() {
        BackgroundService.start(this, BackgroundService.START);
//                MainHandlerUtil.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        BackgroundService.start(OtherTestAct.this, BackgroundService.START);
//                        BackgroundService.start(OtherTestAct.this, BackgroundService.STOP);
//                        BackgroundService.stopDirectly(OtherTestAct.this);
//                        MainHandlerUtil.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                BackgroundService.stopDirectly(OtherTestAct.this);
//                            }
//                        }, 100);
//                    }
//                }, 5 * 1000);
    }

    private void testLikeView() {
        if (likeView.isLike()) {
            likeView.like();
        } else {
            likeView.unLike();
        }
    }

    private void testFullTextView() {
        TextOpt bgOpt = TextOpt.bgOpt(0, 5, Color.RED);
        TextOpt fgOpt = TextOpt.fgOpt(5, ftvFullText.length(), Color.BLUE);
        ftvFullText.bg(bgOpt).fg(fgOpt).done();
    }

    private void testSetSpVal() {
        Random random = new Random();
        int randomInt = random.nextInt(100);
        boolean randomBool = random.nextBoolean();
        randomInt = randomBool ? randomInt : -randomInt;
        SpValHelper.testBoolSp.set(randomBool);
        SpValHelper.testIntSp.set(randomInt);
        SpValHelper.testFloatSp.set((float) randomInt);
        SpValHelper.testStringSp.set(randomInt + "" + randomBool);
    }

    private void testGetSpVal() {
        TextView tvSpValTip = findViewById(R.id.spValTipTextView);
        StringBuilder builder = new StringBuilder();
        builder.append("testBoolSp = ").append(SpValHelper.testBoolSp.get()).append("\n");
        builder.append("testIntSp = ").append(SpValHelper.testIntSp.get()).append("\n");
        builder.append("testFloatSp = ").append(SpValHelper.testFloatSp.get()).append("\n");
        builder.append("testStringSp = ").append(SpValHelper.testStringSp.get());
        tvSpValTip.setText(builder.toString());
    }

    private void testSpToMmkv() {
        MmkvVal.importFromSharedPreferences(this, MmkvValHelper.MMKV_GLOBAL_CONFIG, SpValHelper.SP_GLOBAL_CONFIG);
        MmkvVal.importFromSharedPreferences(this, MmkvVal.DEFAULT_MMPVAL_ID, SpHelper.DEFAULT_SPVAL_NAME);
    }

    private void testShowMoveToMMKV() {
        TextView tvMmkvValTip = findViewById(R.id.tv_move_to_mmkv_tip);
        StringBuilder builder = new StringBuilder();
        builder.append("testBoolSp = ").append(MmkvValHelper.testBoolSp.get()).append("\n");
        builder.append("testIntSp = ").append(MmkvValHelper.testIntSp.get()).append("\n");
        builder.append("testFloatSp = ").append(MmkvValHelper.testFloatSp.get()).append("\n");
        builder.append("testStringSp = ").append(MmkvValHelper.testStringSp.get());
        tvMmkvValTip.setText(builder.toString());
    }

    private void initLoopViewPager() {
        LoopViewPager loopViewPager = findViewById(R.id.loopViewpager);
        loopViewPager.setAdapter(new PagerAdapter() {
            private int[] mColors = new int[]{
                    Color.BLACK,
                    Color.RED,
                    Color.YELLOW,
                    Color.GRAY,
            };

            @Override
            public int getCount() {
                return mColors.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, final int position) {
                TextView tv = new TextView(container.getContext());
                ViewPager.LayoutParams lp = new ViewPager.LayoutParams();
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                tv.setLayoutParams(lp);
                tv.setBackgroundColor(mColors[position]);
                container.addView(tv);
                tv.setOnClickListener(v -> ToastUtil.showToast("I am TextView " + position));
                return tv;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });
        loopViewPager.startLoop();
    }
}
