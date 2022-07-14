package com.example.jour.myapplication.ui

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.jour.myapplication.R
import kotlinx.android.synthetic.main.activity_arouter.*

@Route(path = "/test/activity")
class ArouterActivity : BaseActivity() {

    @Autowired(name = "comment")
    @JvmField
    var mCommentId: String? = ""

//  @Autowired(name = "auditS")
//  @JvmField
//  var mAuditId: String? = ""

    @Autowired(name = "audit")
    @JvmField
    var mAuditIdInt: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance()
            .inject(this)
        setContentView(R.layout.activity_arouter)
        arouter_tv1.text = mCommentId
        arouter_tv2.text = mAuditIdInt.toString()
//    arouter_tv3.text = mAuditIdInt.toString()
    }

    override fun onResume() {
        super.onResume()
//    arouter_tv1.text = mCommentId
//    arouter_tv2.text = mAuditId
//    arouter_tv3.text = mAuditIdInt.toString()
    }
}
