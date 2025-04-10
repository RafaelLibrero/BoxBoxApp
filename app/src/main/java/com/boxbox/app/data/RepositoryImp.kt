package com.boxbox.app.data

import com.boxbox.app.data.network.ApiService
import com.boxbox.app.domain.Repository
import javax.inject.Inject

class RepositoryImp @Inject constructor(private val apiService: ApiService): Repository {

}