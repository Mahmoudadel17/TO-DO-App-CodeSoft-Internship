package com.example.to_do_list.data.repository

import com.example.to_do_list.data.local.Task
import com.example.to_do_list.data.local.TaskDao
import com.example.to_do_list.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(private val dao:TaskDao) : TasksRepository{
    override suspend fun getAllTasks() : Flow<List<Task>> {
        return dao.getAllTasks()
    }

    override suspend fun getTask(taskId: Int): Task {
        return dao.getTaskByID(taskId)
    }

    override suspend fun addTask(task: Task) {
        dao.addTask(task)
    }

    override suspend fun removeTask(taskId: Int) {
        dao.deleteTaskByID(taskId)
    }

    override suspend fun updateTask(task: Task) {
        dao.updateTask(task)
    }

}