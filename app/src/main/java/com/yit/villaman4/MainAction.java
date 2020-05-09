package com.yit.villaman4;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

public class MainAction extends AppCompatActivity {

    private static final String __TAG__ = "MainAction♥♥:";

    /////////////////////////////////////////////////////////
    public static void setMapPointAndMark_Sub(ArrayList<RGetRoomEnty> rGetRoomList, MapView mapView, Context context, String sMess) {

        MapPointBounds mapPointBounds = new MapPointBounds();
        // Log.d(__TAG__,"rGetRoomList.size():"+rGetRoomList.size());

        Toast.makeText(context.getApplicationContext(), sMess + " 지도 표시 중 ...", Toast.LENGTH_LONG).show();
        for (int i = 0; i < rGetRoomList.size(); i++) {

            if (!rGetRoomList.get(i).getMa_lat().equals(null) && !rGetRoomList.get(i).getMa_lon().equals(null)) {

                if (rGetRoomList.get(i).getGubun().equals("new")) { // 신축빌라이면
                    setMapPointNew(i, mapPointBounds, rGetRoomList,  mapView,  context);
                } else {  // 구옥빌라이면
                    if (rGetRoomList.get(i).getMa_Use_Yn2().equals("S")) { // 대기이면
                        setMapPointSmb(i, mapPointBounds, rGetRoomList,  mapView,  context);
                    } else {
                        setMapPointOld(i, mapPointBounds, rGetRoomList,  mapView,  context);
                    }

                }
            } // if 위치정보가 있으면

        } // for

        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds));
        mapView.removeAllCircles();
        // Toast.makeText(context.getApplicationContext(), "지도 표시 중...", Toast.LENGTH_SHORT).show();

    }


    private static void setMapPointNew(Integer i, MapPointBounds mapPointBounds, ArrayList<RGetRoomEnty> rGetRoomList,MapView mapView, Context context) {

        MapPOIItem poiItem = new MapPOIItem();
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(Double.parseDouble(rGetRoomList.get(i).getMa_lat()), Double.parseDouble(rGetRoomList.get(i).getMa_lon()));
        poiItem.setMapPoint(mapPoint);
        mapPointBounds.add(mapPoint);

        ////////////////////////////////////////////////////////
        poiItem.setMarkerType(MapPOIItem.MarkerType.CustomImage);

        if (rGetRoomList.get(i).getMa_Use_Yn().equals("N")) { // 매매완료 이면
            poiItem.setCustomImageResourceId(R.drawable.map_pin_complete);
        } else { // 매매중 이면
            if (rGetRoomList.get(i).getGubun().equals("new")) { // 신축빌라이면
                if (rGetRoomList.get(i).getFcnt().equals("Y")) { // 사진유무
                    poiItem.setCustomImageResourceId(R.drawable.map_pin_new_bep);
                    // poiItem.setCustomImageResourceId(R.drawable.map_pin_new_bep);
                } else {
                    poiItem.setCustomImageResourceId(R.drawable.map_pin_new_be);
                    // poiItem.setCustomImageResourceId(R.drawable.map_pin_new_be);
                }
            }
        }

        poiItem.setCustomImageAutoscale(false);
        poiItem.setCustomImageAnchor(0.5f, 1.0f);

        String strStr = new String();
        strStr = "▶ ";
        if (!rGetRoomList.get(i).getMa_bld_nm().equals(null) && !rGetRoomList.get(i).getMa_bld_nm().equals("")) {
            if (rGetRoomList.get(i).getMa_bld_nm().toString().length() > 8) {
                strStr = strStr + rGetRoomList.get(i).getMa_bld_nm().toString().substring(0, 8) + " ";
            } else {
                strStr = strStr + rGetRoomList.get(i).getMa_bld_nm().toString() + " ";
            }
        }

        if (!rGetRoomList.get(i).getMa_mae_ney().equals(null) && !rGetRoomList.get(i).getMa_mae_ney().equals("")) {
            strStr = strStr + "" + rGetRoomList.get(i).getMa_mae_ney().toString();
        }
        // strStr = strStr + "\n ..";

        poiItem.setItemName(strStr);

        poiItem.setTag(i);


        // 핀에 표시한다.
        mapView.addPOIItem(poiItem);

        // mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds));

    }

    private static void setMapPointSmb(Integer i, MapPointBounds mapPointBounds, ArrayList<RGetRoomEnty> rGetRoomList, MapView mapView, Context context) {

        // MapPointBounds mapPointBounds = new MapPointBounds();
        MapPOIItem poiItem = new MapPOIItem();
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(Double.parseDouble(rGetRoomList.get(i).getMa_lat()), Double.parseDouble(rGetRoomList.get(i).getMa_lon()));
        poiItem.setMapPoint(mapPoint);
        mapPointBounds.add(mapPoint);

        ////////////////////////////////////////////////////////
        poiItem.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        poiItem.setCustomImageResourceId(R.drawable.pin_back_s);

        poiItem.setCustomImageAutoscale(false);
        poiItem.setCustomImageAnchor(0.5f, 1.0f);

        String strStr = new String();
        strStr = "▶ ";
        if (!rGetRoomList.get(i).getMa_bld_nm().equals(null) && !rGetRoomList.get(i).getMa_bld_nm().equals("")) {
            if (rGetRoomList.get(i).getMa_bld_nm().toString().length() > 8) {
                strStr = strStr + rGetRoomList.get(i).getMa_memo6().toString().substring(0, 8) + " ";
            } else {
                strStr = strStr + rGetRoomList.get(i).getMa_memo6().toString() + " ";
            }
        }

        if (!rGetRoomList.get(i).getMa_mae_ney().equals(null) && !rGetRoomList.get(i).getMa_mae_ney().equals("")) {
            strStr = strStr + "" + rGetRoomList.get(i).getMa_mae_ney().toString();
        }

        poiItem.setItemName(strStr);
        poiItem.setTag(i);

        // 핀에 표시한다.
        mapView.addPOIItem(poiItem);
        // mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds));

    }

    // 구옥 이면
    private static void setMapPointOld(Integer i, MapPointBounds mapPointBounds, ArrayList<RGetRoomEnty> rGetRoomList, MapView mapView, Context context) {

        // MapPointBounds mapPointBounds = new MapPointBounds();
        MapPointBounds mapPointBounds2 = new MapPointBounds();
        MapPointBounds mapPointBounds3 = new MapPointBounds();
        MapPointBounds mapPointBounds4 = new MapPointBounds();
        MapPointBounds mapPointBounds5 = new MapPointBounds();
        MapPointBounds mapPointBounds6 = new MapPointBounds();
        MapPointBounds mapPointBounds7 = new MapPointBounds();

        MapPOIItem poiItem = new MapPOIItem();
        MapPOIItem poiItem2 = new MapPOIItem();
        MapPOIItem poiItem3 = new MapPOIItem();
        MapPOIItem poiItem4 = new MapPOIItem();
        MapPOIItem poiItem5 = new MapPOIItem();
        MapPOIItem poiItem6 = new MapPOIItem();
        MapPOIItem poiItem7 = new MapPOIItem();

        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(Double.parseDouble(rGetRoomList.get(i).getMa_lat()), Double.parseDouble(rGetRoomList.get(i).getMa_lon()));
        MapPoint mapPoint2 = MapPoint.mapPointWithGeoCoord(Double.parseDouble(rGetRoomList.get(i).getMa_lat()), Double.parseDouble(rGetRoomList.get(i).getMa_lon()));
        MapPoint mapPoint3 = MapPoint.mapPointWithGeoCoord(Double.parseDouble(rGetRoomList.get(i).getMa_lat()), Double.parseDouble(rGetRoomList.get(i).getMa_lon()));
        MapPoint mapPoint4 = MapPoint.mapPointWithGeoCoord(Double.parseDouble(rGetRoomList.get(i).getMa_lat()), Double.parseDouble(rGetRoomList.get(i).getMa_lon()));
        MapPoint mapPoint5 = MapPoint.mapPointWithGeoCoord(Double.parseDouble(rGetRoomList.get(i).getMa_lat()), Double.parseDouble(rGetRoomList.get(i).getMa_lon()));
        MapPoint mapPoint6 = MapPoint.mapPointWithGeoCoord(Double.parseDouble(rGetRoomList.get(i).getMa_lat()), Double.parseDouble(rGetRoomList.get(i).getMa_lon()));
        MapPoint mapPoint7 = MapPoint.mapPointWithGeoCoord(Double.parseDouble(rGetRoomList.get(i).getMa_lat()), Double.parseDouble(rGetRoomList.get(i).getMa_lon()));

        poiItem.setMapPoint(mapPoint);
        poiItem2.setMapPoint(mapPoint2);
        poiItem3.setMapPoint(mapPoint3);
        poiItem4.setMapPoint(mapPoint4);
        poiItem5.setMapPoint(mapPoint5);
        poiItem6.setMapPoint(mapPoint6);
        poiItem7.setMapPoint(mapPoint7);

        mapPointBounds.add(mapPoint);
        mapPointBounds2.add(mapPoint2);
        mapPointBounds3.add(mapPoint3);
        mapPointBounds4.add(mapPoint4);
        mapPointBounds5.add(mapPoint5);
        mapPointBounds6.add(mapPoint6);
        mapPointBounds7.add(mapPoint7);

        ////////////////////////////////////////////////////////
        poiItem.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        poiItem2.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        poiItem3.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        poiItem4.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        poiItem5.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        poiItem6.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        poiItem7.setMarkerType(MapPOIItem.MarkerType.CustomImage);

        if (rGetRoomList.get(i).getMa_Use_Yn().equals("N")) { // 매매완료 이면
            poiItem.setCustomImageResourceId(R.drawable.map_pin_complete);
        } else { // 매매중 이면

                if (rGetRoomList.get(i).getMa_Good_Yn().equals("Y")) {
                    poiItem.setCustomImageResourceId(R.drawable.pin_back_rec);
                } else {
                    poiItem.setCustomImageResourceId(R.drawable.pin_back);
                }

                // 공실여부
                if (rGetRoomList.get(i).getMa_status1().equals("E")) { // 공실(E)
                    poiItem2.setCustomImageResourceId(R.drawable.pin_ma_status_e);
                } else {
                    poiItem2.setCustomImageResourceId(R.drawable.pin_ma_status_b);
                }

                // 층수
                if (rGetRoomList.get(i).getMa_level().substring(0, 1).equals("0")) { // 반지하
                    poiItem3.setCustomImageResourceId(R.drawable.pin_level_0);
                } else if (rGetRoomList.get(i).getMa_level().substring(0, 1).equals("1")) { //
                    poiItem3.setCustomImageResourceId(R.drawable.pin_level_1);
                } else if (rGetRoomList.get(i).getMa_level().substring(0, 1).equals("2")) { //
                    poiItem3.setCustomImageResourceId(R.drawable.pin_level_2);
                } else if (rGetRoomList.get(i).getMa_level().substring(0, 1).equals("3")) { //
                    poiItem3.setCustomImageResourceId(R.drawable.pin_level_3);
                } else if (rGetRoomList.get(i).getMa_level().substring(0, 1).equals("4")) { //
                    poiItem3.setCustomImageResourceId(R.drawable.pin_level_4);
                } else if (rGetRoomList.get(i).getMa_level().substring(0, 1).equals("5")) { //
                    poiItem3.setCustomImageResourceId(R.drawable.pin_level_5);
                } else {
                    poiItem3.setCustomImageResourceId(R.drawable.pin_level_6);
                }

                // 등록일자
                if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 0) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_0);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 1) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_1);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 2) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_2);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 3) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_3);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 4) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_4);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 5) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_5);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 6) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_6);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 7) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_7);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 8) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_8);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 9) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_9);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 10) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_10);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 11) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_11);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 12) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_12);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 13) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_13);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 14) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_14);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 15) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_15);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 16) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_16);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 17) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_17);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 18) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_18);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 19) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_19);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 20) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_20);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 21) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_21);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 22) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_22);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 23) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_23);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 24) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_24);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 25) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_25);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 26) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_26);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 27) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_27);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 28) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_28);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 29) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_29);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 30) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_30);
                } else if (Integer.parseInt(rGetRoomList.get(i).getDtTermCnt()) == 31) {
                    poiItem4.setCustomImageResourceId(R.drawable.pin_day_31);
                } else {
                }

                if (rGetRoomList.get(i).getFcnt().equals("Y")) { // 사진유무
                    poiItem5.setCustomImageResourceId(R.drawable.pin_picture);
                } else {
                    if (rGetRoomList.get(i).getMa_pass_yn().equals("Y")) { // 공실이고, 비밀번호 있으면
                        poiItem5.setCustomImageResourceId(R.drawable.pin_picture_n);
                    }
                }

                // 전용
                if (Integer.parseInt(rGetRoomList.get(i).getMa_jeon_area()) <= 8) {
                    poiItem6.setCustomImageResourceId(R.drawable.pin_jeon_8);
                } else if (Integer.parseInt(rGetRoomList.get(i).getMa_jeon_area()) == 9) {
                    poiItem6.setCustomImageResourceId(R.drawable.pin_jeon_9);
                } else if (Integer.parseInt(rGetRoomList.get(i).getMa_jeon_area()) == 10) {
                    poiItem6.setCustomImageResourceId(R.drawable.pin_jeon_10);
                } else if (Integer.parseInt(rGetRoomList.get(i).getMa_jeon_area()) == 11) {
                    poiItem6.setCustomImageResourceId(R.drawable.pin_jeon_11);
                } else if (Integer.parseInt(rGetRoomList.get(i).getMa_jeon_area()) == 12) {
                    poiItem6.setCustomImageResourceId(R.drawable.pin_jeon_12);
                } else if (Integer.parseInt(rGetRoomList.get(i).getMa_jeon_area()) == 13) {
                    poiItem6.setCustomImageResourceId(R.drawable.pin_jeon_13);
                } else if (Integer.parseInt(rGetRoomList.get(i).getMa_jeon_area()) == 14) {
                    poiItem6.setCustomImageResourceId(R.drawable.pin_jeon_14);
                } else if (Integer.parseInt(rGetRoomList.get(i).getMa_jeon_area()) == 15) {
                    poiItem6.setCustomImageResourceId(R.drawable.pin_jeon_15);
                } else if (Integer.parseInt(rGetRoomList.get(i).getMa_jeon_area()) == 16) {
                    poiItem6.setCustomImageResourceId(R.drawable.pin_jeon_16);
                } else if (Integer.parseInt(rGetRoomList.get(i).getMa_jeon_area()) == 17) {
                    poiItem6.setCustomImageResourceId(R.drawable.pin_jeon_17);
                } else if (Integer.parseInt(rGetRoomList.get(i).getMa_jeon_area()) == 18) {
                    poiItem6.setCustomImageResourceId(R.drawable.pin_jeon_18);
                } else if (Integer.parseInt(rGetRoomList.get(i).getMa_jeon_area()) == 19) {
                    poiItem6.setCustomImageResourceId(R.drawable.pin_jeon_19);
                } else if (Integer.parseInt(rGetRoomList.get(i).getMa_jeon_area()) == 20) {
                    poiItem6.setCustomImageResourceId(R.drawable.pin_jeon_20);
                } else if (Integer.parseInt(rGetRoomList.get(i).getMa_jeon_area()) >= 21) {
                    poiItem6.setCustomImageResourceId(R.drawable.pin_jeon_21);
                } else {
                }

        }

        if (rGetRoomList.get(i).getFavYn().equals("Y")) { // 즐겨찾기
            poiItem7.setCustomImageResourceId(R.drawable.pin_favorite);
        }

        poiItem.setCustomImageAutoscale(false);
        poiItem.setCustomImageAnchor(0.5f, 1.0f);
        poiItem2.setCustomImageAutoscale(false);
        poiItem2.setCustomImageAnchor(0.5f, 1.0f);
        poiItem3.setCustomImageAutoscale(false);
        poiItem3.setCustomImageAnchor(0.5f, 1.0f);
        poiItem4.setCustomImageAutoscale(false);
        poiItem4.setCustomImageAnchor(0.5f, 1.0f);
        poiItem5.setCustomImageAutoscale(false);
        poiItem5.setCustomImageAnchor(0.5f, 1.0f);
        poiItem6.setCustomImageAutoscale(false);
        poiItem6.setCustomImageAnchor(0.5f, 1.0f);
        poiItem7.setCustomImageAutoscale(false);
        poiItem7.setCustomImageAnchor(0.5f, 1.0f);

        String strStr = new String();
        strStr = "▶ ";
        if (!rGetRoomList.get(i).getMa_bld_nm().equals(null) && !rGetRoomList.get(i).getMa_bld_nm().equals("")) {
            if (rGetRoomList.get(i).getMa_bld_nm().toString().length() > 8) {
                strStr = strStr + rGetRoomList.get(i).getMa_bld_nm().toString().substring(0, 8) + " ";
            } else {
                strStr = strStr + rGetRoomList.get(i).getMa_bld_nm().toString() + " ";
            }
        }

        if (!rGetRoomList.get(i).getMa_mae_ney().equals(null) && !rGetRoomList.get(i).getMa_mae_ney().equals("")) {
            strStr = strStr + "" + rGetRoomList.get(i).getMa_mae_ney().toString();
        }
        // strStr = strStr + "\n ..";

        poiItem.setItemName(strStr);
        poiItem2.setItemName(strStr);
        poiItem3.setItemName(strStr);
        poiItem4.setItemName(strStr);
        poiItem5.setItemName(strStr);
        poiItem6.setItemName(strStr);
        poiItem7.setItemName(strStr);

        poiItem.setTag(i);
        poiItem2.setTag(i);
        poiItem3.setTag(i);
        poiItem4.setTag(i);
        poiItem5.setTag(i);
        poiItem6.setTag(i);
        poiItem7.setTag(i);

        // 핀에 표시한다.
        mapView.addPOIItem(poiItem);
        mapView.addPOIItem(poiItem2);
        mapView.addPOIItem(poiItem3);
        mapView.addPOIItem(poiItem4);
        mapView.addPOIItem(poiItem5);
        mapView.addPOIItem(poiItem6);
        mapView.addPOIItem(poiItem7);

        // mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds));


    }


}
