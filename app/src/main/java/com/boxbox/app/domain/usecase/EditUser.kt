package com.boxbox.app.domain.usecase

import com.boxbox.app.domain.model.User
import com.boxbox.app.domain.repository.Repository
import javax.inject.Inject

class EditUser @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(user: User): Result<Unit> {
        return repository.putUser(user)
    }
}