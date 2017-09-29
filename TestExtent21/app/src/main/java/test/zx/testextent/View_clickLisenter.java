package test.zx.testextent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zx on 2017/2/27.
 */

public class View_clickLisenter implements View.OnClickListener {


    private handler mHandler;

    private AlertDialog.Builder mBuilder;
    private AlertDialog mAlert;

    private MapView mMapView;
    private SimpleLineSymbol mRouteSymbol;
    private GraphicsOverlay mGraphicsOverlay;
    private ArcGISMap map;
    private Context mContext;
    private ListView mDrawerList;

    public View_clickLisenter() {
    }

    public View_clickLisenter(Context mContext) {
        this.mContext = mContext;

        final LayoutInflater inflater = LayoutInflater.from(mContext);
    }


    public View_clickLisenter(handler mHandler) {
        this.mHandler = mHandler;
    }

    public View_clickLisenter(handler mHandler, Context mContext, GraphicsOverlay mGraphicsOverlay) {

        this.mHandler = mHandler;
        this.mContext = mContext;
        this.mGraphicsOverlay = mGraphicsOverlay;

    }


    public View_clickLisenter(Context mContext, GraphicsOverlay mGraphicsOverlay, handler mHandler, MapView mMapView, ListView mDrawerList) {
        this.mContext = mContext;
        this.mGraphicsOverlay = mGraphicsOverlay;
        this.mHandler = mHandler;
        this.mMapView = mMapView;
        this.mDrawerList = mDrawerList;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btn_Quer:
//
//
//                EditText costText;
//                EditText ageText;
//                EditText stateText;
//                ListView resulteList;
//
//                final LayoutInflater inflater = LayoutInflater.from(mContext);
//                View view_custom = inflater.inflate(R.layout.query, null, false);
//
//                costText = (EditText) view_custom.findViewById(R.id.costText);
//                ageText = (EditText) view_custom.findViewById(R.id.agetext);
//                stateText = (EditText) view_custom.findViewById(R.id.stateText);
//                resulteList = (ListView) view_custom.findViewById(R.id.resultContext);
//                String cost=(String) costText.getText().toString();
//                String age=(String) ageText.getText().toString();
//                String state=(String) stateText.getText().toString();
//
//                break;
//            case R.id.attr:
//               /* new Thread() {
//                    @Override
//                    public void run() {
//
//                        mHandler.sendEmptyMessage(0x00);
//                    }
//                }.start();*/
//                mBuilder = new AlertDialog.Builder(mContext);
//                final LayoutInflater inflater1 = LayoutInflater.from(mContext);
//                View view_custom1 = inflater1.inflate(R.layout.users_select_dialog, null, false);
//                mBuilder.setView(view_custom1);
//                mBuilder.setCancelable(false);
//                mAlert = mBuilder.create();
//                mAlert.show();
//                break;
            case R.id.attr:
                    Intent intent=new Intent();
                    intent.setClass(mContext,Query.class);
                    mContext.startActivity(intent);
                break;
            case R.id.btn_weather:
                new Thread() {
                    @Override
                    public void run() {
                        new DoWork().getPointRoute(1);
                        mHandler.sendEmptyMessage(0x001);
                    }
                }.start();
                break;
            case R.id.btn_attribution:
                new Thread(new Runnable() {
                    public void run() {
//                        new DoWork().getListPlan();

                        mHandler.sendEmptyMessage(0x002);
                    }
                }).start();
                break;
            case R.id.fab:
                new Thread() {
                    @Override
                    public void run() {
                        List<ItemStation> result = new DoWork().getPointRoute(1);
                        ArrayList list = new ArrayList();
                        list.add(result);
                        // 创建消息
                        Message msg = new Message();
                        msg.what = 0x003;
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("result", list);
                        msg.setData(bundle);
                        mHandler.sendMessage(msg);
                    }
                }.start();

                break;
        }
    }
}
