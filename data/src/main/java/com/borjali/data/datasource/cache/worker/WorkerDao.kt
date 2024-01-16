package com.borjali.data.datasource.cache.worker

import androidx.room.*

@Dao
interface WorkerDao {

    /**
     * Insert a user to the database.
     *
     * @param user the user to insert
     * @return the row number as long
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: WorkerEntity): Long

    /**
     * get list of user from database.
     *
     * @return the WorkerEntity
     */
    @Query("SELECT * FROM worker_table")
    suspend fun getUsers(): List<WorkerEntity>

    /**
     * update a user.
     */
    @Update
    fun update(workerEntity: WorkerEntity)

    /**
     * Delete a user table.
     */
    @Query("DELETE FROM worker_table")
    suspend fun deleteAll()

    /**
     * get a user from database.
     * @param workerId
     * @return the WorkerEntity
     */
    @Query("SELECT * FROM worker_table WHERE id = :workerId")
    fun getworkerProjects(workerId: String): WorkerEntity
}
