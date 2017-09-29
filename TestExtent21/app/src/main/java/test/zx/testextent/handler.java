package test.zx.testextent;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.view.Callout;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zx on 2017/2/26.
 */

public class handler extends android.os.Handler {

    private DrawerLayout mDrawerLayout;
    private static Context mContext;
    private MapView mMapView;
    private GraphicsOverlay mGraphicsOverlay;
    private ListView mDrawerList;
    private Bundle bundle;
    private Callout mCallout;
    private TextView textView;
    private adapter adapter;
    private ListView resulteList;
    private Picture picture;

    public handler(Context context, TextView textView, ListView resulteList) {
        this.mContext = context;
        this.textView = textView;
        this.resulteList = resulteList;
    }

    public handler(DrawerLayout mDrawerLayout, Context context, MapView mMapView, GraphicsOverlay mGraphicsOverlay, ListView mDrawerList, Callout mCallout) {
        this.mContext = context;
        this.mMapView = mMapView;
        this.mGraphicsOverlay = mGraphicsOverlay;
        this.mDrawerList = mDrawerList;
        this.mDrawerLayout = mDrawerLayout;
    }

    public double Calculate(Point a, android.graphics.Point b) {
        android.graphics.Point tempScreenPoint = mMapView.locationToScreen(a);
        double result = Math.sqrt(Math.pow((tempScreenPoint.x - b.x), 2) + Math.pow((tempScreenPoint.y - b.y), 2));
        return result;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
//            case 0x00:
//                Toast.makeText(mContext, "计算函数成功", Toast.LENGTH_SHORT).show();
//                break;
            case 0x001:
                bundle = msg.getData();
                try {
                    ArrayList localPoint = bundle.getParcelableArrayList("result");
                    ArrayList atrr = bundle.getParcelableArrayList("atrr");
                    List<ItemPlan> itemLocalPoints = (List<ItemPlan>) localPoint.get(0);

                    adapter = new adapter(atrr,itemLocalPoints, mContext);

                    resulteList.setAdapter(adapter);

                } catch (Exception e) {
                    Toast.makeText(mContext, "触屏失败", Toast.LENGTH_SHORT).show();
                }


                Toast.makeText(mContext, "触屏", Toast.LENGTH_SHORT).show();
                break;
            case 0x002:
                Toast.makeText(mContext, "号码归属地查询成功", Toast.LENGTH_SHORT).show();
                break;
            case 0x003:
                bundle = msg.getData();
                ArrayList list = bundle.getParcelableArrayList("result");
                List<ItemStation> itemStations = (List<ItemStation>) list.get(0);//强转成你自己定义的list，这样list2就是你传过来的那个list了。
                List<Point> mPoints = new ArrayList<Point>();
                for (ItemStation item : itemStations) {
                    mPoints.add(new Point(item.getX(), item.getY(), SpatialReferences.getWgs84()));
                }
                FindRoute mFindRoute = new FindRoute(mDrawerLayout, mContext, mGraphicsOverlay, mMapView, mDrawerList);
                mFindRoute.setmPoints(mPoints);
                mFindRoute.setItemStation(itemStations);
                mFindRoute.findRoute();

                Toast.makeText(mContext, "路径查询成功", Toast.LENGTH_SHORT).show();
                break;
            case 0x004:
                bundle = msg.getData();
                try {

                    ItemStation itemStation = null;
                    ArrayList clickPointList = bundle.getParcelableArrayList("clickPoint");
                    android.graphics.Point clickPoint = (android.graphics.Point) clickPointList.get(0);
                    ArrayList localPoint = bundle.getParcelableArrayList("result");
                    List<ItemStation> itemLocalPoints = (List<ItemStation>) localPoint.get(0);

                    List<Point> mPoints1 = new ArrayList<Point>();
                    for (ItemStation item : itemLocalPoints) {
                        Point a = new Point(item.getX(), item.getY(), SpatialReferences.getWgs84());

                        if (Calculate(a, clickPoint) < 50) {
                            itemStation = item;
                            break;
                        }
                    }

                    if (itemStation != null) {
                        Point pointtt = new Point(itemStation.getX(), itemStation.getY(), SpatialReferences.getWgs84());
                        mCallout = mMapView.getCallout();
                       /* final LayoutInflater inflater = LayoutInflater.from(mContext);
                        View view_custom = inflater.inflate(R.layout.users_select_dialog, null, false);*/

                        final LayoutInflater inflater = LayoutInflater.from(mContext);
                        View calloutContent = inflater.inflate(R.layout.callout, null, false);

                        ImageView imageView = (ImageView) calloutContent.findViewById(R.id.image);
                        TextView txt_aName = (TextView) calloutContent.findViewById(R.id.test);
                        TextView txt_aName1 = (TextView) calloutContent.findViewById(R.id.test1);
                        TextView txt_aName2 = (TextView) calloutContent.findViewById(R.id.test11);
                        txt_aName.setText(itemStation.getStation());

                        switch (Integer.parseInt(itemStation.getData())){
                            case 1:         if(itemStation.getSubject().equals("jd")){
                                txt_aName1.setText(itemStation.getIntroduction() + '\n' + "酒店入住住宿:"+" 第一天 " + String.valueOf(itemStation.getTime()));
                            }else{
                                txt_aName1.setText(itemStation.getIntroduction() + '\n' + "游玩时间:"+" 第一天 " + String.valueOf(itemStation.getTime()));
                            }
                            break;
                            case 2:         if(itemStation.getSubject().equals("jd")){
                                txt_aName1.setText(itemStation.getIntroduction() + '\n' + "酒店入住住宿:"+" 第二天 " + String.valueOf(itemStation.getTime()));
                            }else{
                                txt_aName1.setText(itemStation.getIntroduction() + '\n' + "游玩时间:"+" 第二天 " + String.valueOf(itemStation.getTime()));
                            }
                                break;
                        }
                        txt_aName2.setText("¥ "+itemStation.getCost()+"起");
                        BitmapDrawable bm = (BitmapDrawable) mContext.getDrawable(R.drawable.szld);
                        try {
                            picture = new Picture();
                            BitmapDrawable Bp = picture.getPictureSymbol(mContext, itemStation.getName());
                            imageView.setImageDrawable(Bp);

                        } catch (Exception e) {
                            imageView.setImageDrawable(bm);
                        }
                        mCallout.setContent(calloutContent);
                        mCallout.setLocation(pointtt);
                        mCallout.show();
                    } else {
                        mCallout.dismiss();
                    }

                } catch (Exception e) {
                    Toast.makeText(mContext, "触屏失败", Toast.LENGTH_SHORT).show();
                }


//                Toast.makeText(mContext, "触屏", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
