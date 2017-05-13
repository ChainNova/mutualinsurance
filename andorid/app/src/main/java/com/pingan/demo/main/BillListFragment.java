package com.pingan.demo.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pingan.demo.MyApplication;
import com.pingan.demo.R;
import com.pingan.demo.adapter.BillListAdapter;
import com.pingan.demo.base.BaseFragment;
import com.pingan.demo.controller.FragmentManagerControl;
import com.pingan.demo.loadframe.ListDataLoadHandler;
import com.pingan.demo.model.entity.Insurance;
import com.pingan.demo.model.entity.InsurancesRes;
import com.pingan.demo.model.service.InsurancesService;
import com.pingan.demo.refreshlist.XListView;
import com.pingan.http.framework.network.ServiceTask;
import com.pingan.http.framework.task.NetworkExcuter;
import com.pingan.http.framework.task.NetwrokTaskError;
import com.pingan.http.framework.task.ServiceCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guolidong752 on 17/5/4.
 */

public class BillListFragment extends BaseFragment {
    private BaseFragment billDetailFragment;


    public BillListFragment() {

    }

    private XListView mListView;
    private LinearLayout refreshView;
    private ListDataLoadHandler listDataLoadHandler;


    InsurancesRes insurancesRes;
    private ServiceCallback taskCallback = new ServiceCallback() {
        @Override
        public void onTaskStart(String serverTag) {
            content_ll.showProcess();
        }

        @Override
        public void onTaskSuccess(String serverTag) {
            if (serverTag.equals(InsurancesService.SERVICE_TAG_getInsurance)) {

            }
            MyApplication.getAppContext().getHandler().post(new Runnable() {
                @Override
                public void run() {
                    initData();
                }
            });
            content_ll.removeProcess();
        }

        @Override
        public void onTaskFail(final NetwrokTaskError error, String serverTag) {

            insurancesRes.setData(new ArrayList<Insurance>());
            MyApplication.getAppContext().getHandler().post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), error.errorString, Toast.LENGTH_SHORT).show();
                    content_ll.showRequestError();
                }
            });
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mainLayout = (ViewGroup) LayoutInflater.from(getActivity())
                .inflate(R.layout.layout_bill_list, baseLayout);
        return baseLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mainLayout != null) {
            insurancesRes = new InsurancesRes();
            mListView = (XListView) mainLayout.findViewById(R.id.listView);
            refreshView = (LinearLayout) mainLayout.findViewById(R.id.no_data_layout);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getInsurances();
    }

    private void getInsurances() {
        ServiceTask serviceTask = new ServiceTask(InsurancesService.getInsurances(insurancesRes));
        serviceTask.setCancelable(true).setShowProcess(true);
        serviceTask.setCallback(taskCallback);
        NetworkExcuter.getInstance().excute(serviceTask, this);
    }


    private void initData() {
//        Insurance insurance1 = new Insurance();
//        insurance1.setName("上海滴滴司机互助");
//        List<String> descriptions1 = new ArrayList<String>();
//        descriptions1.add("驾乘意外伤害");
//        descriptions1.add("驾乘意外医疗");
//        descriptions1.add("descriptions");
//        descriptions1.add("descriptions");
//        descriptions1.add("descriptions");
//        descriptions1.add("descriptions");
//        descriptions1.add("descriptions");
//        insurance1.setDescription(descriptions1);
//        insurance1.setAmount_max("30000");
//        insurance1.setCount_bought("20");
//        insurance1.setFee("5");
//        insurance1.setIssuer("由上海滴滴司机联盟发起保障");
//        insurancesRes.getData().add(insurance1);
//        Insurance insurance2 = new Insurance();
//        insurance2.setName("中青年抗癌互助");
//        List<String> descriptions2 = new ArrayList<String>();
//        descriptions2.add("胃癌、肝癌等各种癌症");
//        descriptions2.add("18-50周岁");
//        descriptions2.add("descriptions");
//        descriptions2.add("descriptions");
//        descriptions2.add("descriptions");
//        descriptions2.add("descriptions");
//        descriptions2.add("descriptions");
//        insurance2.setDescription(descriptions2);
//        insurance2.setAmount_max("30");
//        insurance2.setCount_bought("120");
//        insurance2.setFee("5");
//        insurance2.setIssuer("由抗癌公社发起保障");
//        insurancesRes.getData().add(insurance2);
//        Insurance insurance3 = new Insurance();
//        insurance3.setName("南航金卡延误互助");
//        List<String> descriptions3 = new ArrayList<String>();
//        descriptions3.add("南航金卡会员");
//        descriptions3.add("当年延误超过15次");
//        descriptions3.add("descriptions");
//        descriptions3.add("descriptions");
//        descriptions3.add("descriptions");
//        descriptions3.add("descriptions");
//        descriptions3.add("descriptions");
//        insurance3.setDescription(descriptions3);
//        insurance3.setAmount_max("1");
//        insurance3.setCount_bought("30");
//        insurance3.setFee("5");
//        insurance3.setIssuer("由南航明珠俱乐部发起保障");
//        insurancesRes.getData().add(insurance3);
        BillListAdapter adapter = new BillListAdapter(getActivity(), insurancesRes.getData());
        mListView.setAdapter(adapter);
        mListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                mListView.stopRefresh();
                getInsurances();
            }

            @Override
            public void onLoadMore() {
                mListView.stopLoadMore();
            }

            @Override
            public void changeShortcut(boolean isShow) {

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position <= insurancesRes.getData().size()) {
                    billDetailFragment = new BillDetailFragment();
                    Bundle data = new Bundle();
                    data.putSerializable("Insurance", insurancesRes.getData().get(position - 1));
                    billDetailFragment.setArguments(data);
                    FragmentManagerControl.getInstance().addFragment(billDetailFragment);
                }
            }
        });
    }
}
