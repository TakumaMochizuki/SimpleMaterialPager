package com.github.takumalee.simplematerialpager.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.github.takumalee.simplematerialpager.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.Random;

/**
 * Created by TakumaLee on 15/1/21.
 */
public class SimpleMaterialBarView extends LinearLayout {
    private static final String TAG = SimpleMaterialBarView.class.getSimpleName();

    private AppCompatActivity appCompatActivity;
    private Context context;
    private LayoutInflater inflater;
    private View view;
    private LinearLayout parent;
    private Toolbar toolbar;
    private FrameLayout frameLayout;

    private SystemBarTintManager systemBarTintManager;

    private Drawable oldBackground = null;
    private int currentColor;

    public SimpleMaterialBarView(Context context) {
        super(context);
        this.context = context;
        this.appCompatActivity = (AppCompatActivity) context;
        initView(0);
    }

    public SimpleMaterialBarView(Context context, int newColor) {
        super(context);
        this.context = context;
        this.appCompatActivity = (AppCompatActivity) context;
        initView(newColor);
    }

    public SimpleMaterialBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(0);
    }

    public SimpleMaterialBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(0);
    }

    private void initView(int newColor) {
        this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        this.setOrientation(VERTICAL);
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.material_bar, null);
        parent = (LinearLayout) view.findViewById(R.id.linearLayout_materialbar);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_materialbar);
        frameLayout = (FrameLayout) view.findViewById(R.id.frameLayout_materialbar);
        appCompatActivity.setSupportActionBar(toolbar);
//        ((ActionBarActivity)context).getSupportActionBar().setDisplayShowHomeEnabled(true);
//        ((ActionBarActivity)context).getSupportActionBar().setLogo(R.drawable.ic_menu_white);

        systemBarTintManager = new SystemBarTintManager((Activity) context);
        systemBarTintManager.setStatusBarTintEnabled(true);

        changeColor(newColor == 0 ? getRandomBackgroundColor() : newColor);
        this.addView(view);
    }

    public int getRandomBackgroundColor() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(64) + 192, rnd.nextInt(64) + 192, rnd.nextInt(64) + 192);
        return color;
    }

    public void changeTextColor(int newColor) {
        toolbar.setTitleTextColor(newColor);
    }

    public void changeColor(int newColor) {
        systemBarTintManager.setTintColor(newColor);
        // change ActionBar color just if an ActionBar is available
        Drawable colorDrawable = new ColorDrawable(newColor);
        Drawable bottomDrawable = new ColorDrawable(getResources().getColor(android.R.color.transparent));
        LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});
        if (oldBackground == null) {
            appCompatActivity.getSupportActionBar().setBackgroundDrawable(ld);
        } else {
            TransitionDrawable td = new TransitionDrawable(new Drawable[]{oldBackground, ld});
            appCompatActivity.getSupportActionBar().setBackgroundDrawable(td);
            td.startTransition(200);
        }

        oldBackground = ld;
        currentColor = newColor;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public FrameLayout getFrameLayout() {
        return frameLayout;
    }

    public LinearLayout getBarParent() {
        return parent;
    }

    public SystemBarTintManager getSystemBarTintManager() {
        return systemBarTintManager;
    }

    public int getCurrentColor() {
        return currentColor;
    }

    public Drawable getOldBackground() {
        return oldBackground;
    }
}
