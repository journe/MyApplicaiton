package com.jour.demo.ui.amap

import android.Manifest
import android.os.Bundle
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.LocationSource
import com.amap.api.maps.model.MyLocationStyle
import com.jour.demo.base.ktx.d
import com.jour.demo.base.mvvm.vm.EmptyViewModel
import com.jour.demo.base.utils.toast
import com.jour.demo.common.ui.BaseActivity
import com.jour.demo.databinding.ActivityAmapTexturemapBinding
import com.permissionx.guolindev.PermissionX
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AmapTextureActivity : BaseActivity<ActivityAmapTexturemapBinding, EmptyViewModel>(),
    AMapLocationListener {

    private var mListener: LocationSource.OnLocationChangedListener? = null

    private var mlocationClient: AMapLocationClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.map.onCreate(savedInstanceState)
    }

    override fun ActivityAmapTexturemapBinding.initView() {
        //初始化地图控制器对象
        val aMap = map.map
        AMapLocationClient.updatePrivacyShow(this@AmapTextureActivity, true, true)
        AMapLocationClient.updatePrivacyAgree(this@AmapTextureActivity, true)
        // 设置定位监听
        aMap.setLocationSource(object : LocationSource {
            override fun activate(listener: LocationSource.OnLocationChangedListener?) {
                mListener = listener;
                if (mlocationClient == null) {
                    //初始化定位
                    mlocationClient = AMapLocationClient(this@AmapTextureActivity).apply {
                        //初始化定位参数
                        setLocationListener(this@AmapTextureActivity);
                        //设置为高精度定位模式
                        //设置定位参数
                        setLocationOption(AMapLocationClientOption().apply {
                            locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
                        });
                        startLocation();//启动定位
                    }

                }
            }

            override fun deactivate() {
            }
        })
// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.isMyLocationEnabled = true;

        val myLocationStyle = MyLocationStyle()
        //初始化定位蓝点样式类
//		myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW)
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)
        //连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//		myLocationStyle.interval(2000)
        myLocationStyle.showMyLocation(true)
        //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.myLocationStyle = myLocationStyle
        //设置定位蓝点的Style
//		aMap.uiSettings.isMyLocationButtonEnabled = true;
        //设置默认定位按钮是否显示，非必需设置。

        aMap.uiSettings.isZoomControlsEnabled = false

    }

    override fun initObserve() {
    }

    override fun initRequestData() {
        val plist = listOf(
            PermissionX.permission.POST_NOTIFICATIONS,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
        PermissionX.init(this).permissions(plist).onExplainRequestReason { scope, deniedList ->
            val message = "需要您同意以下权限才能正常使用"
            scope.showRequestReasonDialog(deniedList, message, "允许", "拒绝")
        }.request { allGranted, grantedList, deniedList ->
            if (allGranted) {
                toast("授权成功")
            } else {
                toast("您拒绝了如下权限：$deniedList")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mBinding.map.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.map.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        mBinding.map.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mBinding.map.onSaveInstanceState(outState)
    }

    override fun onLocationChanged(amapLocation: AMapLocation?) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                && amapLocation.getErrorCode() == 0
            ) {
                mListener?.onLocationChanged(amapLocation)// 显示系统小蓝点

                amapLocation.d()
                // 第一个参数表示公交站点名，第二个参数表示所在城市名或者城市区号
//				val busStationQuery = BusStationQuery("search", "cityCode")
//				val busStationSearch = BusStationSearch(this, busStationQuery);
//				busStationSearch.setOnBusStationSearchListener(this);// 设置查询结果的监听
//				busStationSearch.searchBusStationAsyn();

            } else {
                val errText =
                    "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                errText.d()
            }
        }
    }

//	override fun onBusStationSearched(result: BusStationResult?, p1: Int) {
//		val busStations = result?.busStations
//		busStations.d()
//	}
}
