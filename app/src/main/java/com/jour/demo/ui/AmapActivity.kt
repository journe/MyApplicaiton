package com.jour.demo.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.maps.LocationSource
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.CameraUpdate
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.SupportMapFragment
import com.amap.api.maps2d.model.LatLng
import com.amap.api.maps2d.model.Marker
import com.amap.api.maps2d.model.MarkerOptions
import com.jour.demo.base.ktx.clickDelay
//import com.amap.api.services.busline.BusStationQuery
//import com.amap.api.services.busline.BusStationResult
//import com.amap.api.services.busline.BusStationSearch
//import com.amap.api.maps2d.AMap
//import com.amap.api.maps2d.LocationSource
//import com.amap.api.maps2d.model.MyLocationStyle
import com.jour.demo.base.ktx.d
import com.jour.demo.base.mvvm.vm.EmptyViewModel
import com.jour.demo.base.utils.toast
import com.jour.demo.common.ui.BaseActivity
import com.jour.demo.databinding.ActivityAmapBinding
import com.jour.demo.ui.amap.AmapTextureActivity
import com.permissionx.guolindev.PermissionX
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AmapActivity : BaseActivity<ActivityAmapBinding, EmptyViewModel>() {

    private var mListener: LocationSource.OnLocationChangedListener? = null

    private lateinit var mlocationClient: AMapLocationClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun ActivityAmapBinding.initView() {
        //初始化地图控制器对象
        val aMap = mapFragment.getFragment<SupportMapFragment>().map

        aMap.uiSettings.isZoomControlsEnabled = false

        val latLng = LatLng(39.906901, 116.397972)
        aMap.addMarker(MarkerOptions().position(latLng).title("北京").snippet("DefaultMarker"))

        val suzhouLatLng = LatLng(31.249102, 120.692032)
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(suzhouLatLng))
        aMap.moveCamera(CameraUpdateFactory.zoomTo(5f))
        aMap.addMarker(
            MarkerOptions().position(suzhouLatLng).title("苏州市").snippet("尹山湖韵佳苑")
        )

        aMap.setOnInfoWindowClickListener {
           "infowindow clicked".d()
        }


        // latitude=31.249102
        // longitude=120.692032
        // province=江苏省#coordType=GCJ02#city=苏州市#district=吴中区#cityCode=0512#adCode=320506
        // address=江苏省苏州市吴中区郭莘路105号靠近尹山湖韵佳苑#country=中国#road=郭莘路
        // poiName=尹山湖韵佳苑#street=郭莘路#streetNum=105号#aoiName=尹山湖韵佳苑
        // poiid=#floor=#errorCode=0
        // errorInfo=success
        // locationDetail=#id:SdGdqZWttaWRkZmFhYmk3NmczZ2dlYzYwNjZkZThkLA==
        // csid:a5c345791ff44819aa988933772a6fcf#pm101011
        // description=在尹山湖韵佳苑附近#locationType=4#conScenario=0
        mlocationClient = AMapLocationClient(this@AmapActivity).apply {
            setLocationListener {
                if (it != null && it.errorCode == 0) {
                    mListener?.onLocationChanged(it)// 显示系统小蓝点
                    it.d()
                } else {
                    val errText =
                        "定位失败," + it.getErrorCode() + ": " + it.getErrorInfo()
                    errText.d()
                }
            }
            //设置定位参数
            setLocationOption(AMapLocationClientOption().apply {
                locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
                //获取最近3s内精度最高的一次定位结果.setOnceLocation(boolean b)接口也会被设置为true
                isOnceLocation = true
                isOnceLocationLatest = true
            })
        }


        textureMap.clickDelay {
            startActivity(Intent(this@AmapActivity, AmapTextureActivity::class.java))
        }

        myLocationBtn.clickDelay {
            mlocationClient.startLocation()
        }

    }

    override fun initObserve() {
    }

    override fun initRequestData() {
        val plist = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
        PermissionX.init(this).permissions(plist).onExplainRequestReason { scope, deniedList ->
            val message = "需要您同意以下权限才能正常使用"
            scope.showRequestReasonDialog(deniedList, message, "允许", "拒绝")
        }.request { allGranted, grantedList, deniedList ->
            if (allGranted) {
//                toast("授权成功")
            } else {
                toast("您拒绝了如下权限：$deniedList")
            }
        }
    }

}
