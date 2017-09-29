/* Copyright 2016 Esri
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package test.zx.testextent;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.tasks.networkanalysis.DirectionManeuver;
import com.esri.arcgisruntime.tasks.networkanalysis.Route;
import com.esri.arcgisruntime.tasks.networkanalysis.RouteParameters;
import com.esri.arcgisruntime.tasks.networkanalysis.RouteResult;
import com.esri.arcgisruntime.tasks.networkanalysis.RouteTask;
import com.esri.arcgisruntime.tasks.networkanalysis.Stop;

import java.util.ArrayList;
import java.util.List;

public class FindRoute {

    private DrawerLayout mDrawerLayout;
    private static final String TAG = "FindRouteSample";
    private RouteTask mRouteTask;
    private RouteParameters mRouteParams;
    private Route mRoute;
    private SimpleLineSymbol mRouteSymbol;
    private GraphicsOverlay mGraphicsOverlay;
    private Resources resources;
    private Context mContext;
    private ListView mDrawerList;

    private final LayoutInflater inflater;
    private MapView mMapView;
    private String[] directionsArray;
    private List<Point> mPoints;
   private   List<ItemStation> itemStations;
    private Picture picture;

    public FindRoute(Context mContext, GraphicsOverlay mGraphicsOverlay) {
        this.mContext = mContext;
        this.mGraphicsOverlay = mGraphicsOverlay;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPoints = new ArrayList<Point>();
        itemStations=new ArrayList<ItemStation>();
        picture=new Picture();
    }

    public FindRoute(DrawerLayout mDrawerLayout, Context mContext, GraphicsOverlay mGraphicsOverlay, MapView mMapView, ListView mDrawerList) {
        this.mDrawerLayout = mDrawerLayout;
        this.mContext = mContext;
        this.mGraphicsOverlay = mGraphicsOverlay;
        this.mMapView = mMapView;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mDrawerList = mDrawerList;
        mPoints = new ArrayList<Point>();
        itemStations=new ArrayList<ItemStation>();
        picture=new Picture();
    }

    public void setmPoints(List<Point> mPoints) {
        this.mPoints = mPoints;
    }
    public void setItemStation(List<ItemStation> itemStations) {
        this.itemStations = itemStations;
    }
    protected void findRoute() {

        View view = inflater.inflate(R.layout.main, null);


        resources = mContext.getResources();
        String route = resources.getString(R.string.routing_service);
        mRouteSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.GREEN, 5);
        // create RouteTask instance
        mRouteTask = new RouteTask(route);

        final ListenableFuture<RouteParameters> listenableFuture = mRouteTask.createDefaultParametersAsync();
        listenableFuture.addDoneListener(new Runnable() {
            @Override
            public void run() {
                try {
                    if (listenableFuture.isDone()) {
                        int i = 0;
                        mRouteParams = listenableFuture.get();

                        // get List of Stops
                        List<Stop> routeStops = mRouteParams.getStops();
                        // set return directions as true to return turn-by-turn directions in the result of getDirectionManeuvers().
                        mRouteParams.setReturnDirections(true);
                        mRouteParams.setFindBestSequence(false);
                        // add your stops to it 32.7254716,-117.1508181 32.7076359,-117.1592837 -117.15557279683529
                        //-13041171, 3860988, SpatialReference(3857) -13041693, 3856006, SpatialReference(3857)
//
//                        routeStops.add(new Stop(new Point(-117.15083257944445, 32.741123367963446, SpatialReferences.getWgs84())));
//                        routeStops.add(new Stop(new Point(-117.15557279683529, 32.703360305883045, SpatialReferences.getWgs84())));
//                        routeStops.add(new Stop(new Point(120.3523, 36.08263, SpatialReferences.getWgs84())));
//                        routeStops.add(new Stop(new Point(120.3718, 36.08913, SpatialReferences.getWgs84())));



                        int j=1;
                        for (ItemStation tmp : itemStations) {

                            if (tmp.getSubject().equals("jd")) {
                                final Point point = new Point(tmp.getX(), tmp.getY(), SpatialReferences.getWgs84());
                                routeStops.add(new Stop(point));

                                BitmapDrawable start_end = (BitmapDrawable) mContext.getDrawable(R.drawable.jd);
                                final PictureMarkerSymbol pinSourceSymbol = new PictureMarkerSymbol(start_end);
                                pinSourceSymbol.loadAsync();
                                pinSourceSymbol.addDoneLoadingListener(new Runnable() {
                                    public void run() {
                                        Graphic pinSourceGraphic = new Graphic(point, pinSourceSymbol);
                                        mGraphicsOverlay.getGraphics().add(pinSourceGraphic);
                                    }
                                });
                                pinSourceSymbol.setOffsetY(20);
//                                isstart=false;
                            }
                            else {

                                final Point point = new Point(tmp.getX(), tmp.getY(), SpatialReferences.getWgs84());
                                routeStops.add(new Stop(point));
                                String dwString="dw"+String.valueOf(j);
                                BitmapDrawable dw=picture.getPictureSymbol(mContext,dwString);
//                                BitmapDrawable dw = (BitmapDrawable) mContext.getDrawable(R.drawable.dw);
                                final PictureMarkerSymbol pinSourceSymbol = new PictureMarkerSymbol(dw);
                                pinSourceSymbol.loadAsync();
                                pinSourceSymbol.addDoneLoadingListener(new Runnable() {
                                    public void run() {
                                        Graphic pinSourceGraphic = new Graphic(point, pinSourceSymbol);
                                        mGraphicsOverlay.getGraphics().add(pinSourceGraphic);
                                    }
                                });
                                pinSourceSymbol.setOffsetY(20);
                                j++;
                            }


                        }

//
//                        routeStops.add(new Stop(mPoints.get(0)));
//                        routeStops.add(new Stop(mPoints.get(1)));
                        // solve
                        RouteResult result = mRouteTask.solveRouteAsync(mRouteParams).get();
                        final List routes = result.getRoutes();
                        mRoute = (Route) routes.get(0);
                        // create a mRouteSymbol graphic
                        Graphic routeGraphic = new Graphic(mRoute.getRouteGeometry(), mRouteSymbol);
                        // add mRouteSymbol graphic to the map
                        mGraphicsOverlay.getGraphics().add(routeGraphic);
//                        get directions
                        // NOTE: to get turn-by-turn directions Route Parameters should set returnDirection flag as true
                        final List<DirectionManeuver> directions = mRoute.getDirectionManeuvers();

                        directionsArray = new String[directions.size()];

                        for (DirectionManeuver dm : directions) {
                            directionsArray[i++] = dm.getDirectionText();
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.drawer_layout_text, directionsArray);
                        mDrawerList.setAdapter(adapter);
                        // Set the adapter for the list view
                        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //TODO
                                //图形多于三条时，会出现错误，需要clear一下
                                if (mGraphicsOverlay.getGraphics().size() > (mPoints.size() + 1)) {
                                    mGraphicsOverlay.getGraphics().remove(mGraphicsOverlay.getGraphics().size() - 1);
                                }
                                mDrawerLayout.closeDrawers();
                                DirectionManeuver dm = directions.get(position);
                                Geometry gm = dm.getGeometry();
                                Viewpoint vp = new Viewpoint(gm.getExtent(), 20);
                                mMapView.setViewpointAsync(vp, 3);
                                SimpleLineSymbol selectedRouteSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 5);
                                Graphic selectedRouteGraphic = new Graphic(directions.get(position).getGeometry(), selectedRouteSymbol);
                                mGraphicsOverlay.getGraphics().add(selectedRouteGraphic);
                            }
                        });


                    }
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        });
    }

    protected void findRoute_copy() {

        View view = inflater.inflate(R.layout.main, null);


        resources = mContext.getResources();
        String route = resources.getString(R.string.routing_service);
        mRouteSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.GREEN, 5);
        // create RouteTask instance
        mRouteTask = new RouteTask(route);

        final ListenableFuture<RouteParameters> listenableFuture = mRouteTask.createDefaultParametersAsync();
        listenableFuture.addDoneListener(new Runnable() {
            @Override
            public void run() {
                try {
                    if (listenableFuture.isDone()) {
                        int i = 0;
                        mRouteParams = listenableFuture.get();

                        // get List of Stops
                        List<Stop> routeStops = mRouteParams.getStops();
                        // set return directions as true to return turn-by-turn directions in the result of getDirectionManeuvers().
                        mRouteParams.setReturnDirections(true);
                        mRouteParams.setFindBestSequence(false);

                        // add your stops to it 32.7254716,-117.1508181 32.7076359,-117.1592837 -117.15557279683529
                        //-13041171, 3860988, SpatialReference(3857) -13041693, 3856006, SpatialReference(3857)
//
//                        routeStops.add(new Stop(new Point(-117.15083257944445, 32.741123367963446, SpatialReferences.getWgs84())));
//                        routeStops.add(new Stop(new Point(-117.15557279683529, 32.703360305883045, SpatialReferences.getWgs84())));
//                        routeStops.add(new Stop(new Point(120.3523, 36.08263, SpatialReferences.getWgs84())));
//                        routeStops.add(new Stop(new Point(120.3718, 36.08913, SpatialReferences.getWgs84())));


                        final Point endPiont = mPoints.get(0);
                        boolean isstart = true;
                        for (Point tmp : mPoints) {

                            if (isstart) {
                                routeStops.add(new Stop(tmp));
                                final Point point = tmp;
                                BitmapDrawable start_end = (BitmapDrawable) mContext.getDrawable(R.drawable.start);
                                final PictureMarkerSymbol pinSourceSymbol = new PictureMarkerSymbol(start_end);
                                pinSourceSymbol.loadAsync();
                                pinSourceSymbol.addDoneLoadingListener(new Runnable() {
                                    public void run() {
                                        Graphic pinSourceGraphic = new Graphic(point, pinSourceSymbol);
                                        mGraphicsOverlay.getGraphics().add(pinSourceGraphic);
                                    }
                                });
                                pinSourceSymbol.setOffsetY(20);
                                isstart=false;
                            }
                            else {
                                routeStops.add(new Stop(tmp));
                                final Point point = tmp;
                                BitmapDrawable dw = (BitmapDrawable) mContext.getDrawable(R.drawable.dw);
                                final PictureMarkerSymbol pinSourceSymbol = new PictureMarkerSymbol(dw);
                                pinSourceSymbol.loadAsync();
                                pinSourceSymbol.addDoneLoadingListener(new Runnable() {
                                    public void run() {
                                        Graphic pinSourceGraphic = new Graphic(point, pinSourceSymbol);
                                        mGraphicsOverlay.getGraphics().add(pinSourceGraphic);
                                    }
                                });
                                pinSourceSymbol.setOffsetY(20);
                            }


                        }
                        routeStops.add(new Stop(endPiont));
//
//                        routeStops.add(new Stop(mPoints.get(0)));
//                        routeStops.add(new Stop(mPoints.get(1)));
                        // solve
                        RouteResult result = mRouteTask.solveRouteAsync(mRouteParams).get();
                        final List routes = result.getRoutes();
                        mRoute = (Route) routes.get(0);
                        // create a mRouteSymbol graphic
                        Graphic routeGraphic = new Graphic(mRoute.getRouteGeometry(), mRouteSymbol);
                        // add mRouteSymbol graphic to the map
                        mGraphicsOverlay.getGraphics().add(routeGraphic);
//                        get directions
                        // NOTE: to get turn-by-turn directions Route Parameters should set returnDirection flag as true
                        final List<DirectionManeuver> directions = mRoute.getDirectionManeuvers();

                        directionsArray = new String[directions.size()];

                        for (DirectionManeuver dm : directions) {
                            directionsArray[i++] = dm.getDirectionText();
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.drawer_layout_text, directionsArray);
                        mDrawerList.setAdapter(adapter);
                        // Set the adapter for the list view
                        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //TODO
                                //图形多于三条时，会出现错误，需要clear一下
                                if (mGraphicsOverlay.getGraphics().size() > (mPoints.size() + 1)) {
                                    mGraphicsOverlay.getGraphics().remove(mGraphicsOverlay.getGraphics().size() - 1);
                                }
                                mDrawerLayout.closeDrawers();
                                DirectionManeuver dm = directions.get(position);
                                Geometry gm = dm.getGeometry();
                                Viewpoint vp = new Viewpoint(gm.getExtent(), 20);
                                mMapView.setViewpointAsync(vp, 3);
                                SimpleLineSymbol selectedRouteSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 5);
                                Graphic selectedRouteGraphic = new Graphic(directions.get(position).getGeometry(), selectedRouteSymbol);
                                mGraphicsOverlay.getGraphics().add(selectedRouteGraphic);
                            }
                        });


                    }
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        });
    }
}
