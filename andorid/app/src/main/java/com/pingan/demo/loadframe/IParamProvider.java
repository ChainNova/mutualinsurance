package com.pingan.demo.loadframe;

import android.widget.BaseAdapter;

import com.pingan.http.framework.model.response.BaseResponse;

import java.util.List;

/**
 * Created by xra on 16/4/5.
 */
public interface IParamProvider {

    public int getCount();

    public List<?> getList();

    public BaseAdapter getAdapter(Object obj);
}
