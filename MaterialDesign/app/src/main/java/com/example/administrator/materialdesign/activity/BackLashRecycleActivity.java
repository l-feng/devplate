package com.example.administrator.materialdesign.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.materialdesign.R;
import com.example.administrator.materialdesign.adapter.recyclelist.BacklashRecycleAdapter;
import com.example.administrator.materialdesign.model.DataBean;
import com.example.administrator.materialdesign.model.Datas;
import com.example.core.activity.BaseActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/7/12 0012.
 */


public class BackLashRecycleActivity extends BaseActivity {

   private List<DataBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initDate();
        initView();

    }

    private void initView(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rec_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BacklashRecycleAdapter adapter = new BacklashRecycleAdapter(list);
        recyclerView.setAdapter(adapter);
    }
    private void initDate() {
        list= Datas.getDatas();
    }
}
