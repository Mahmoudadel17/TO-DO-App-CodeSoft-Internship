package com.example.to_do_list.di

import android.content.Context
import androidx.room.Room
import com.example.to_do_list.data.local.MyDatabase
import com.example.to_do_list.data.repository.TasksRepositoryImpl
import com.example.to_do_list.domain.repository.TasksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDatabase (@ApplicationContext context: Context) =
        Room.databaseBuilder(context,MyDatabase::class.java,"MyDatabase").build()

    @Provides
    @Singleton
    fun providesDatabaseDao(database: MyDatabase) = database.getTaskDao()


    @Provides
    @Singleton
    fun provideTasksRepository(@ApplicationContext context:Context):TasksRepository =
        TasksRepositoryImpl(providesDatabaseDao(providesDatabase(context)))



}