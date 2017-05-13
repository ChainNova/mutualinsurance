package com.pingan.demo.model.viewmodel;

import com.pingan.demo.model.entity.Insurance;
import com.pingan.http.framework.model.viewmodel.BaseViewModel;

import java.util.List;

/**
 * Created by guolidong752 on 17/5/9.
 */

public class GetInsurancesModel extends BaseViewModel {
    private List<Insurance> data;

    public List<Insurance> getData() {
        return data;
    }

    public void setData(List<Insurance> data) {
        this.data = data;
    }
}
