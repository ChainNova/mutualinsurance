package com.pingan.demo.loadframe;

import android.view.View;

/**
 * Created by xra on 16/3/30.
 */
public interface ILoadCallback {
    public void onNetException();

    public void onItemClick(int position, Object itemObject);
}
