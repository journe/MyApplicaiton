package com.jour.demo.ui.picture

import com.jour.demo.base.mvvm.m.BaseRepository
import com.jour.demo.common.retrofit.ApiService
import javax.inject.Inject

class PictureRepository @Inject constructor() : BaseRepository() {
    @Inject
    lateinit var apiService: ApiService
}

