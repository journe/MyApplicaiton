package com.jour.myapplication.ui

import android.view.ActionMode
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.jour.myapplication.R
import com.jour.myapplication.base.mvvm.vm.EmptyViewModel
import com.jour.myapplication.common.ui.BaseFragment
import com.jour.myapplication.databinding.FragmentCustomSelectionBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CustomSelectionActionFragment :
    BaseFragment<FragmentCustomSelectionBinding, EmptyViewModel>() {
    override val mViewModel: EmptyViewModel by viewModels()

    override fun FragmentCustomSelectionBinding.initView() {
        val callback = object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                val menuInflater: MenuInflater? = mode?.menuInflater
                menuInflater?.inflate(R.menu.selection_action_menu, menu)
                return true //返回false则不会显示弹窗

            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                //根据item的ID处理点击事件
                when (item?.itemId) {
                    R.id.Informal22 -> {
                        Toast.makeText(requireContext(), "点击的是22", Toast.LENGTH_SHORT).show()
                        mode?.finish() //收起操作菜单
                    }
                    R.id.Informal33 -> {
                        Toast.makeText(requireContext(), "点击的是33", Toast.LENGTH_SHORT).show()
                        mode?.finish()
                    }
                }
                return false //返回true则系统的"复制"、"搜索"之类的item将无效，只有自定义item有响应

            }

            override fun onDestroyActionMode(mode: ActionMode?) {
            }
        }

        val callback2 = object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return true //返回false则不会显示弹窗

            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                menu?.clear()
                val menuInflater: MenuInflater? = mode?.menuInflater
                menuInflater?.inflate(R.menu.selection_action_menu, menu)
                return true
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                //根据item的ID处理点击事件
                when (item?.itemId) {
                    R.id.Informal22 -> {
                        Toast.makeText(requireContext(), "点击的是22", Toast.LENGTH_SHORT).show()
                        mode?.finish() //收起操作菜单
                    }
                    R.id.Informal33 -> {
                        Toast.makeText(requireContext(), "点击的是33", Toast.LENGTH_SHORT).show()
                        mode?.finish()
                    }
                }
                return false //返回true则系统的"复制"、"搜索"之类的item将无效，只有自定义item有响应

            }

            override fun onDestroyActionMode(mode: ActionMode?) {
            }
        }

        textview.customSelectionActionModeCallback = callback
        edittext.customSelectionActionModeCallback = callback2
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }


}
