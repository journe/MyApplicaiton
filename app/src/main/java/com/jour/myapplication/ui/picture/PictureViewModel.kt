package com.jour.myapplication.ui.picture

import com.jour.myapplication.base.mvvm.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PictureViewModel @Inject constructor(private val mRepository: PictureRepository) :
    BaseViewModel() {

}
