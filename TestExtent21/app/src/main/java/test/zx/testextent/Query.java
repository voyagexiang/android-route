package test.zx.testextent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zx on 2017/3/31.
 */

public class Query extends Activity {

    private Context mContext;

    private View_clickLisenter mVCL;
    private handler mHandler;


    private boolean one_selected = false;
    private boolean two_selected = false;
    private EditText stateText;
    private Button resulteButton;
    private ListView resulteList;
    private TextView testView;
    private ArrayList IDList;
    private Map<String, Object> AttrMap;
    private Spinner spin_cost;
    private Spinner spin_age;
    private Spinner spin_children;
    private Spinner spin_adult;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query);
        mContext = Query.this;
        spin_cost = (Spinner) findViewById(R.id.costSpinner);
        spin_age = (Spinner) findViewById(R.id.ageSpinner);
        spin_children = (Spinner) findViewById(R.id.childrenSpinner);
        spin_adult = (Spinner) findViewById(R.id.adultSpinner);
        spin_adult.setSelection(1);
        spin_cost.setSelection(1);

        stateText = (EditText) findViewById(R.id.stateText);
        resulteButton = (Button) findViewById(R.id.btn_Quer);
        resulteList = (ListView) findViewById(R.id.resultContext);
        IDList = new ArrayList();

        mHandler = new handler(mContext, testView, resulteList);
        bindViews();

        AttrMap = new HashMap();
        AttrMap.put("state", stateText.getText().toString());
        AttrMap.put("cost", 1);
        AttrMap.put("age", 1);
        AttrMap.put("adult", 1);

    }


    private void bindViews() {

        resulteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (IDList != null) {
                    IDList.clear();
                }
                AttrMap.put("state", stateText.getText().toString());
                new Thread() {
                    @Override
                    public void run() {
                        List<ItemPlan> result = new DoWork().getListPlan(AttrMap);
                        for (ItemPlan tmp : result) {
                            IDList.add(tmp.getID());
                        }
                        ArrayList list = new ArrayList();
                        list.add(result);
                        ArrayList atrr = new ArrayList();
                        atrr.add(AttrMap);
                        // 创建消息
                        Message msg = new Message();
                        msg.what = 0x001;
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("result", list);
                        bundle.putParcelableArrayList("atrr", atrr);
                        msg.setData(bundle);
                        mHandler.sendMessage(msg);
                    }
                }.start();

            }
        });

        resulteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(mContext, "你点击了第" + position + "项", Toast.LENGTH_SHORT).show();
                final int positionCLK = (int) IDList.get(position);
//                final int positionCLK = position;
                Intent intent = new Intent();
                intent.setClass(mContext, MainActivity.class);
                //用Bundle携带数据
                Bundle bundle = new Bundle();
                //传递name参数为tinyphp
                bundle.putInt("position", positionCLK);
                intent.putExtras(bundle);
                mContext.startActivity(intent);

            }
        });
        spin_cost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AttrMap.put("cost", position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_adult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AttrMap.put("adult", position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AttrMap.put("age", position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
