package com.pingan.demo.loadframe;

import com.pingan.http.framework.task.NetwrokTaskError;

/**
 * Created by xra on 16/4/8.
 */
public interface ILoadFailCallback {
    public void onRespFail(NetwrokTaskError error);

}
