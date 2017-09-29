package test.zx.testextent;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zx on 2017/3/31.
 */

public class adapter extends BaseAdapter {

    private List<ItemPlan> mData;
    private Context mContext;
    private Picture picture;
    private ArrayList adult;

    public adapter(ArrayList adult, List<ItemPlan> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
        this.adult =adult;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.planview, parent, false);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
        TextView txt_aName = (TextView) convertView.findViewById(R.id.test);
        TextView txt_aName1 = (TextView) convertView.findViewById(R.id.test1);
        TextView txt_aName2 = (TextView) convertView.findViewById(R.id.test11);
        BitmapDrawable bm = (BitmapDrawable) mContext.getDrawable(R.drawable.szld);
        try {
            picture = new Picture();
            BitmapDrawable Bp = picture.getPictureSymbol(mContext, mData.get(position).getName());
            imageView.setImageDrawable(Bp);
        } catch (Exception e) {
            imageView.setImageDrawable(bm);
        }
        txt_aName.setText(mData.get(position).getIntroducton());
        txt_aName1.setText(String.valueOf(mData.get(position).getStation()));
        Map<String,Object> atrr= (HashMap)adult.get(0);
        switch ((int)atrr.get("adult")){
            case 0:txt_aName2.setText("¥"+String.valueOf(mData.get(position).getCost())+"起");
                break;

            case 1:
                txt_aName2.setText("¥"+String.valueOf(mData.get(position).getCost())+"起");
                break;

            case 2:
                switch ((int)atrr.get("cost")){
                    case 0:txt_aName2.setText("¥"+String.valueOf(mData.get(position).getCost())+"起");break;
                    case 1:txt_aName2.setText("¥"+String.valueOf(300+mData.get(position).getCost())+"起");break;
                    case 2:txt_aName2.setText("¥"+String.valueOf(1000+mData.get(position).getCost())+"起");break;
                    case 3:txt_aName2.setText("¥"+String.valueOf(5000+mData.get(position).getCost())+"起");break;
                }
            default:
                switch ((int)atrr.get("cost")){
                    case 0:txt_aName2.setText("¥"+String.valueOf(mData.get(position).getCost())+"起");break;
                    case 1:txt_aName2.setText("¥"+String.valueOf(300+mData.get(position).getCost())+"起");break;
                    case 2:txt_aName2.setText("¥"+String.valueOf(1000+mData.get(position).getCost())+"起");break;
                    case 3:txt_aName2.setText("¥"+String.valueOf(5000+mData.get(position).getCost())+"起");break;
                }
        }
        return convertView;
    }
}