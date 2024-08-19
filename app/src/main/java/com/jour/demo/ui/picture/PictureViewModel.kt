package com.jour.demo.ui.picture

import com.jour.demo.base.mvvm.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PictureViewModel @Inject constructor(private val mRepository: PictureRepository) :
    BaseViewModel() {

}
