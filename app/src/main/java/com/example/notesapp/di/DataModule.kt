package com.example.notesapp.di

import android.content.Context
import androidx.room.Room
import com.example.notesapp.data.NotesDao
import com.example.notesapp.data.NotesDatabase
import com.example.notesapp.data.NotesRepositoryImpl
import com.example.notesapp.domain.NotesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

interface DataModule {

    @Singleton
    @Binds
    fun bindNotesRepository(impl: NotesRepositoryImpl): NotesRepository

    companion object{

        @Singleton
        @Provides
        fun provideDatabase(
            @ApplicationContext context: Context
        ): NotesDatabase{
            return Room.databaseBuilder(
                context = context,
                klass = NotesDatabase::class.java,
                name = "notes.db"
            ).build()
        }


        @Singleton
        @Provides
        fun provideNotesDao(database: NotesDatabase): NotesDao{
            return database.notesDao()
        }
    }
}