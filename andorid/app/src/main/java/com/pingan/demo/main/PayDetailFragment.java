package com.pingan.demo.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pingan.demo.MyApplication;
import com.pingan.demo.R;
import com.pingan.demo.base.BaseFragment;
import com.pingan.demo.model.entity.Claim;
import com.pingan.demo.model.service.ClaimService;
import com.pingan.http.framework.network.PostServiceParams;
import com.pingan.http.framework.network.ServiceTask;
import com.pingan.http.framework.task.NetworkExcuter;
import com.pingan.http.framework.task.NetwrokTaskError;
import com.pingan.http.framework.task.ServiceCallback;
import com.pingan.http.utils.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by guolidong752 on 17/5/8.
 */

public class PayDetailFragment extends BaseFragment {
    private Claim claim;

    public PayDetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mainLayout = (ViewGroup) LayoutInflater.from(getActivity())
                .inflate(R.layout.layout_pay_detail, baseLayout);
        return baseLayout;
    }


    private ServiceCallback taskCallback = new ServiceCallback() {
        @Override
        public void onTaskStart(String serverTag) {
            content_ll.showProcess();
        }

        @Override
        public void onTaskSuccess(String serverTag) {
            Toast.makeText(getActivity(), "申请成功", Toast.LENGTH_SHORT).show();
            content_ll.removeProcess();
        }

        @Override
        public void onTaskFail(final NetwrokTaskError error, String serverTag) {
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mainLayout != null) {
            //            claim = (Claim) getArguments().getSerializable("claim");
            final EditText cardId = (EditText) mainLayout.findViewById(R.id.cardId_0);
            final EditText cardId1 = (EditText) mainLayout.findViewById(R.id.cardId_1);
            mainLayout.findViewById(R.id.pay_btn_1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cardId1.getText() == null || cardId.getText() == null || cardId.getText()
                            .toString().trim().equals("") || cardId1.getText().toString().trim()
                            .equals("")) {
                        Toast.makeText(getActivity(), "银行卡号不可为空", Toast.LENGTH_SHORT).show();
                    } else if (!cardId1.getText().toString().trim()
                            .equals(cardId.getText().toString().trim())) {
                        Toast.makeText(getActivity(), "银行卡号输入的不同", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "申请成功", Toast.LENGTH_SHORT).show();
                    }
                    //                    doPostClaim();
                }
            });
        }
    }

    private void doPostClaim() {
        ServiceTask serviceTask = new ServiceTask(ClaimService.doPostClaim());
        serviceTask.setCancelable(true).setShowProcess(true);
        serviceTask.setCallback(taskCallback);
        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("id_insurance", insuranceReq.getId_insurance());
//            jsonObject.put("id_user", insuranceReq.getId_user());
//            jsonObject.put("name", insuranceReq.getName());
//            jsonObject.put("idcard", insuranceReq.getIdcard());
//            jsonObject.put("mobile", insuranceReq.getMobile());
//            jsonObject.put("amount", String.valueOf(insuranceReq.getAmount()));
//            jsonObject.put("medical", insuranceReq.getMedical());
//            jsonObject.put("id_driver", insuranceReq.getId_driver());
//            jsonObject.put("id_driving", insuranceReq.getId_driving());
//            jsonObject.put("id_didi", insuranceReq.getId_didi());
//            jsonObject.put("id_csa", insuranceReq.getId_csa());
//            jsonObject.put("time_bought", DateUtil.getCurrentTime("yyyy-MM-dd hh:mm:ss"));
//            jsonObject.put("reserved", insuranceReq.getReserved());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        ((PostServiceParams) serviceTask.getServiceParams()).setJsonObject(jsonObject);
        NetworkExcuter.getInstance().excute(serviceTask, this);
    }

    private void initData() {
    }
}
