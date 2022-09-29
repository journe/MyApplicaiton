package com.jour.myapplication.ui.arouter

import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.jour.myapplication.base.mvvm.vm.EmptyViewModel
import com.jour.myapplication.common.ui.BaseActivity
import com.jour.myapplication.databinding.ActivityArouterBinding

@Route(path = "/test/activity")
class ArouterActivity : BaseActivity<ActivityArouterBinding, EmptyViewModel>() {

    @Autowired(name = "comment")
    @JvmField
    var mCommentId: String? = ""

//  @Autowired(name = "auditS")
//  @JvmField
//  var mAuditId: String? = ""

    @Autowired(name = "audit")
    @JvmField
    var mAuditIdInt: Int = 0

    override val mViewModel: EmptyViewModel by viewModels()

    override fun ActivityArouterBinding.initView() {
        TODO("Not yet implemented")
    }

    override fun initObserve() {
        mBinding.arouterTv1.text = mCommentId
        mBinding.arouterTv2.text = mAuditIdInt.toString()
    }

    override fun initRequestData() {
    }
}
