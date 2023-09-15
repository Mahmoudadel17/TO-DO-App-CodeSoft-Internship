package com.example.to_do_list.domain.repository

import com.example.to_do_list.data.local.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {

    suspend fun getAllTasks(): Flow<List<Task>>

    suspend fun getTask(taskId: Int):Task
    suspend fun addTask(task:Task)

    suspend fun removeTask(taskId:Int)

    suspend fun updateTask(task: Task)
}