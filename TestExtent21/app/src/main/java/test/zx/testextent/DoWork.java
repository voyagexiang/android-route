package test.zx.testextent;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zx on 2017/2/24.
 */

public class DoWork {


    public String result;
    public List<ItemStation> itemStations;
    public List<ItemPlan> itemPlens;
    private SoapObject soapObject;

    private final String NameSpace = "http://tempuri.org/";

    private final String Planurl = "http://192.168.70.24:801/Service1.asmx";
    private final String Planmethod = "getPlanData";
    private final String PlansoapAction = "http://tempuri.org/getPlanData";
    //归属地查询相关参数

    private final String Positionurl = "http://192.168.70.24:801/Service1.asmx";
    private final String Positionmethod = "getPosition";
    private final String PositionsoapAction = "http://tempuri.org/getPosition";

    private final String Testurl = "http://192.168.70.24:801/Service1.asmx";
    private final String Testmethod = "HelloWorld";
    private final String TestsoapAction = "http://tempuri.org/HelloWorld";

    public DoWork() {
        itemStations = new ArrayList<ItemStation>();
        itemPlens =new ArrayList<ItemPlan>();
    }

    private String getString() {
        return result;
    }

    public void cal() {
        // 创建消息
       /* Message msg = new Message();
        msg.what = 0x001;
        Bundle bundle = new Bundle();
        bundle.putInt("UPPER_NUM",
                Integer.parseInt("etNum.getText().toString()"));
        msg.setData(bundle);*/
        // 向新线程中的Handler发送消息
//        mMyHandler.sendEmptyMessage(msg);
    }

    //定义一个获取路径的方法：
    public List<ItemStation> getPointRoute(int id) {
        result = "";
        if (itemStations != null) {
            itemStations.clear();
        }

        SoapObject object = KsoapHttp_findroute(id, Positionurl, PositionsoapAction, NameSpace, Positionmethod);
        InterpretXML_Route(object);
        return itemStations;

    }


    //定义一个获取方案的方法：
    public List<ItemPlan> getListPlan(Map<String, Object> itemAttr) {
        result = "";
        if (itemPlens != null) {
            itemPlens.clear();
        }
        int id = 0;
        SoapObject object = KsoapHttp_planList(itemAttr, Planurl, PlansoapAction, NameSpace, Planmethod);
        InterpretXML_Plan(object);
        return itemPlens;
    }

    //通过第三方接口连接WebService
    private SoapObject KsoapHttp_planList(Map<String, Object> itemAttr, String url, String soapAction, String nameSpace, String method) {


        final HttpTransportSE httpTransportSE = new HttpTransportSE(url);
        httpTransportSE.debug = true;
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(nameSpace, method);
        String name = itemAttr.get("state").toString();

        int age = Integer.parseInt(itemAttr.get("age").toString());
        int Cost = Integer.parseInt(itemAttr.get("cost").toString());
        int adult = Integer.parseInt(itemAttr.get("adult").toString());
        double temptcost=Math.floor(Cost/adult);
        int cost=(int)temptcost;
        soapObject.addProperty("age", age);
        soapObject.addProperty("cost", cost);
        soapObject.addProperty("name", name);
        envelope.dotNet = true;
        envelope.bodyOut = soapObject;


        try {
            httpTransportSE.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //获得服务返回的数据,并且开始解析
        SoapObject object = (SoapObject) envelope.bodyIn;//System.out.println("获得服务数据");
        return object;
    }

    //通过第三方接口连接WebService
    private SoapObject KsoapHttp_findroute(int id, String url, String soapAction, String nameSpace, String method) {


        final HttpTransportSE httpTransportSE = new HttpTransportSE(url);
        httpTransportSE.debug = true;
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        SoapObject soapObject = new SoapObject(nameSpace, method);


        soapObject.addProperty("id", id);
        envelope.dotNet = true;
        envelope.bodyOut = soapObject;


        try {
            httpTransportSE.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //获得服务返回的数据,并且开始解析
        SoapObject object = (SoapObject) envelope.bodyIn;//System.out.println("获得服务数据");
        return object;
    }

    private SoapObject KsoapHttp(String url, String soapAction, String nameSpace, String method) {

        SoapObject soapObject = new SoapObject(nameSpace, method);

        //传递的数据
        soapObject.addProperty("ID", 1);
//        soapObject.addProperty("userid", "qq");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
//        envelope.setOutputSoapObject(soapObject);
        HttpTransportSE httpTransportSE = new HttpTransportSE(url);
        httpTransportSE.debug = true;
        //	System.out.println("信息设置完毕,准备开启服务");


        try {
            httpTransportSE.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //获得服务返回的数据,并且开始解析
        SoapObject object = (SoapObject) envelope.bodyIn;//System.out.println("获得服务数据");
        return object;
    }

    private void InterpretXML_Plan(SoapObject object) {
        int count = object.getPropertyCount();//获取结果个数
        result = object.getProperty(0).toString();//System.out.println("获取信息完毕,向主线程发信息");


        // 获取子节点
        SoapObject soapChilds = (SoapObject) object.getProperty(0);

        try {


            SoapObject results = (SoapObject) soapChilds.getProperty("points");
            // 遍历获取details节点
            for (int i = 0; i < results.getPropertyCount(); i++) {
                SoapObject details = (SoapObject) results.getProperty(i);

                String name = details.getProperty("name").toString();
                String age1 = details.getProperty("Age").toString();
                String introduction = details.getProperty("Introduction").toString();
                String station = details.getProperty("Station").toString();
                String cost = details.getProperty("Cost").toString();
                String id = details.getProperty("ID").toString();
                int age = Integer.parseInt(age1);
                int ID = Integer.parseInt(id);
                int Cost=Integer.parseInt(cost);
                ItemPlan itemPoint = new ItemPlan();

                itemPoint.setName(name);
                itemPoint.setAge(age);
                itemPoint.setIntroducton(introduction);
                itemPoint.setID(ID);
                itemPoint.setStation(station);
                itemPoint.setCost(Cost);

                itemPlens.add(itemPoint);
            }

        } catch (Exception e) {

            e.getMessage();
        }
    }

    private void InterpretXML_Route(SoapObject object) {
        int count = object.getPropertyCount();//获取结果个数
        result = object.getProperty(0).toString();//System.out.println("获取信息完毕,向主线程发信息");


        // 获取子节点
        SoapObject soapChilds = (SoapObject) object.getProperty(0);

        try {


            SoapObject results = (SoapObject) soapChilds.getProperty("points");
            // 遍历获取details节点
            for (int i = 0; i < results.getPropertyCount(); i++) {
                SoapObject details = (SoapObject) results.getProperty(i);

                String Introduction = details.getProperty("Introduction").toString();
                String x = details.getProperty("X").toString();
                String y = details.getProperty("Y").toString();
                String Station = details.getProperty("Station").toString();
                String Data = details.getProperty("Data").toString();
                String Subject = details.getProperty("Subject").toString();
                String sid = details.getProperty("SID").toString();
                String Time = details.getProperty("Time").toString();
                String name = details.getProperty("name").toString();
                String Cost = details.getProperty("Cost").toString();
                String id = details.getProperty("ID").toString();

                float X = Float.parseFloat(x);
                float Y = Float.parseFloat(y);
                int ID = Integer.parseInt(id);
                int SID = Integer.parseInt(sid);
                ItemStation itemStation = new ItemStation();
                itemStation.setIntroduction(Introduction);
                itemStation.setX(X);
                itemStation.setY(Y);
                itemStation.setStation(Station);
                itemStation.setData(Data);
                itemStation.setSubject(Subject);
                itemStation.setSID(SID);
                itemStation.setTime(Time);
                itemStation.setName(name);
                itemStation.setCost(Cost);
                itemStation.setID(ID);
                itemStations.add(itemStation);
            }

        } catch (Exception e) {

        }
    }
}
