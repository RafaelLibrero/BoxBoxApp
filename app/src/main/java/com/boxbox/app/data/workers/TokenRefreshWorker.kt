package com.boxbox.app.data.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.boxbox.app.domain.repository.AuthRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class TokenRefreshWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val authRepository: AuthRepository
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        val token = authRepository.getToken()

        if (token != null && authRepository.isTokenExpiringSoon(token)) {
            val result = authRepository.refreshToken()
            if (result.isFailure) {
                authRepository.logout()
                return Result.failure()
            }
        }

        return Result.success()
    }
}
