package com.rakibofc.lifeplustask.di

import android.content.Context
import androidx.room.Room
import com.rakibofc.lifeplustask.data.local.LifePlusDao
import com.rakibofc.lifeplustask.data.local.LifePlusDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DB_NAME = "life_plus.db"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext mContext: Context): LifePlusDatabase {
        return Room.databaseBuilder(
            mContext,
            LifePlusDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideLifePlusDao(mDatabase: LifePlusDatabase): LifePlusDao {
        return mDatabase.lifePlusDao()
    }
}