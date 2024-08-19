package com.jour.demo

import com.jour.demo.base.mvvm.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DViewModel @Inject constructor(private val mRepository: DRepository) :
    BaseViewModel() {

}
