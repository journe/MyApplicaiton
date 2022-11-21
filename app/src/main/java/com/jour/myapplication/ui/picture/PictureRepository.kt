package com.jour.myapplication

import com.jour.myapplication.base.mvvm.m.BaseRepository
import com.jour.myapplication.common.retrofit.ApiService
import javax.inject.Inject

class DRepository @Inject constructor() : BaseRepository() {
    @Inject
    lateinit var apiService: ApiService
}

