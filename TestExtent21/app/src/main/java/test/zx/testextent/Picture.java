package test.zx.testextent;

/**
 * Created by zx on 2017/4/11.
 */

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;

import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HGH on 2016/12/22.
 */


public class Picture {

    public Picture() {
    }

    BitmapDrawable getPictureSymbol(Context context, String name) {

        Map<String, BitmapDrawable> BMap;
        BMap = new HashMap<>();
        PictureMarkerSymbol pictureMarkerSymbol;
        BitmapDrawable bitmapDrawable;
        BitmapDrawable afzx = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.afzx);
        BitmapDrawable bdg = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.bdg);
        BitmapDrawable hdsj = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.hdsj);
        BitmapDrawable pcy = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.pcy);
        BitmapDrawable pjbwg = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.pjbwg);
        BitmapDrawable ptjbwg = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.ptjbwg);
        BitmapDrawable szld = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.szld);
        BitmapDrawable xqd = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.xqd);
        BitmapDrawable yymt = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.yymt);
        BitmapDrawable zq = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.zq);
        BitmapDrawable zss = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.zss);
        BitmapDrawable ls = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.ls);
        BitmapDrawable wsgc = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.wsgc);
        BitmapDrawable zlzy = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.zlzy);
        BitmapDrawable mszl = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.mszl);
        BitmapDrawable lss = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.lss);
        BitmapDrawable lsm = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.lsm);
        BitmapDrawable lsl = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.lsl);

        BitmapDrawable dw = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.dw);
        BitmapDrawable dw1 = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.dw1);
        BitmapDrawable dw2 = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.dw2);
        BitmapDrawable dw3 = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.dw3);
        BitmapDrawable dw4 = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.dw4);
        BitmapDrawable dw5 = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.dw5);
        BitmapDrawable dw6 = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.dw6);
        BitmapDrawable dw7 = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.dw7);
        BitmapDrawable dw8 = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.dw8);
        BitmapDrawable dw9 = (BitmapDrawable) ContextCompat.getDrawable(context.getApplicationContext(), R.drawable.dw9);


        BMap.put("afzx", afzx);
        BMap.put("bdg", bdg);
        BMap.put("hdsj", hdsj);
        BMap.put("pcy", pcy);
        BMap.put("pjbwg", pjbwg);
        BMap.put("ptjbwg", ptjbwg);
        BMap.put("szld", szld);
        BMap.put("xqd", xqd);
        BMap.put("yymt", yymt);
        BMap.put("zq", zq);
        BMap.put("zss", zss);
        BMap.put("ls", ls);
        BMap.put("wsgc", wsgc);
        BMap.put("zlzy", zlzy);
        BMap.put("mszl", mszl);
        BMap.put("lss", lss);
        BMap.put("lsm", lsm);
        BMap.put("lsl", lsl);

        BMap.put("dw1", dw1);
        BMap.put("dw2", dw2);
        BMap.put("dw3", dw3);
        BMap.put("dw4", dw4);
        BMap.put("dw5", dw5);
        BMap.put("dw6", dw6);
        BMap.put("dw7", dw7);
        BMap.put("dw8", dw8);
        BMap.put("dw9", dw9);



        bitmapDrawable = BMap.get(name);
        pictureMarkerSymbol = new PictureMarkerSymbol(BMap.get(name));


        return bitmapDrawable;
    }


}
