package com.jour.myapplication

import com.jour.myapplication.base.mvvm.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DViewModel @Inject constructor(private val mRepository: DRepository) :
    BaseViewModel() {

}
