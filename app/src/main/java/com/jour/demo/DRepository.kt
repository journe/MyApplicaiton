package com.jour.demo

import com.jour.demo.base.mvvm.m.BaseRepository
import com.jour.demo.common.retrofit.ApiService
import javax.inject.Inject

class DRepository @Inject constructor() : BaseRepository() {
    @Inject
    lateinit var apiService: ApiService
}

