package com.qqjyb.szxhcl;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by chenkaiju on 2017/11/15.
 */

public class MyViewPageAdpter extends PagerAdapter {
    List<View> vies;
    MyViewPageAdpter(List<View> vies){
        this.vies=vies;
    }
    @Override
    public int getCount() {
        return vies.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=vies.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(vies.get(position));
    }


}
