package test.zx.testextent;

import android.content.Context;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.Callout;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private handler mHandler;
    private MapView mMapView;
    private GraphicsOverlay mGraphicsOverlay;
    private Context mContext;
    private ArcGISMap mMap;
    private FloatingActionButton mDirectionFab;
    private FloatingActionButton mAttr;
    private ListView mDrawerList;
    private View_clickLisenter mVCL;
    private Viewpoint sanDiegoPoint;
    private Callout mCallout;
    private android.graphics.Point clickPoint;
    private View mCalloutView;
    private int position;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mContext = MainActivity.this;
        mGraphicsOverlay = new GraphicsOverlay();
        mMap = new ArcGISMap(Basemap.createStreets());
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mMapView = (MapView) findViewById(R.id.mapView);
        mHandler = new handler(mDrawerLayout, mContext, mMapView, mGraphicsOverlay, mDrawerList, mCallout);
        sanDiegoPoint = new Viewpoint(36.07, 120.33, 200000);
        mDirectionFab = (FloatingActionButton) findViewById(R.id.fab);
        mAttr = (FloatingActionButton) findViewById(R.id.attr);
        mVCL = new View_clickLisenter(mContext, mGraphicsOverlay, mHandler, mMapView, mDrawerList);

        mMap.setInitialViewpoint(sanDiegoPoint);
        mMapView.setMap(mMap);
        mCallout = mMapView.getCallout();
        mMapView.getGraphicsOverlays().add(mGraphicsOverlay);



        bindViews();
        try {

            //新页面接收数据
            Bundle bundle = this.getIntent().getExtras();
            //接收name值
            position = bundle.getInt("position");

            if (position>=0){
                new Thread() {
                    @Override
                    public void run() {
                        List<ItemStation> result = new DoWork().getPointRoute(position);
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
            }
        }
        catch (Exception e){

        }
    }


    private void bindViews() {

        mDirectionFab.setOnClickListener(mVCL);
        mAttr.setOnClickListener(mVCL);
        mMapView.setOnTouchListener(new DefaultMapViewOnTouchListener(this, mMapView) {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
//                Toast.makeText(mContext, "触屏成功", Toast.LENGTH_SHORT).show();
                clickPoint = new android.graphics.Point(Math.round(e.getX()), Math.round(e.getY()));

                new Thread() {
                    @Override
                    public void run() {
                        List<ItemStation> result = new DoWork().getPointRoute(position);
                        ArrayList list = new ArrayList(); //这个list用于在budnle中传递 需要传递的ArrayList<Object>
                        list.add(result);
                        // 创建消息
                        Message msg = new Message();
                        msg.what = 0x004;
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("result", list);
                        ArrayList screenPoint = new ArrayList();
                        screenPoint.add(clickPoint);
                        // 创建消息

                        bundle.putParcelableArrayList("clickPoint", screenPoint);

                        msg.setData(bundle);
                        mHandler.sendMessage(msg);
                    }
                }.start();
                return super.onSingleTapConfirmed(e);
            }
        });
    }
}
