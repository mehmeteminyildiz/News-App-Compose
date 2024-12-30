package com.mey.newsapp.domain.usecases

import com.mey.newsapp.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val localUserManager: LocalUserManager
) {
     operator fun invoke(): Flow<Boolean>{
        return localUserManager.readAppEntry()
    }
}