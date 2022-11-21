package com.jour.myapplication.ui.picture

import com.jour.myapplication.base.mvvm.m.BaseRepository
import com.jour.myapplication.common.retrofit.ApiService
import javax.inject.Inject

class PictureRepository @Inject constructor() : BaseRepository() {
    @Inject
    lateinit var apiService: ApiService
}

