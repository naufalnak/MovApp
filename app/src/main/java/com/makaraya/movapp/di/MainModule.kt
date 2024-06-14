package com.makaraya.movapp.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.auth.FirebaseAuth
import com.makaraya.movapp.data.firebase.AuthRepository
import com.makaraya.movapp.data.firebase.AuthRepositoryImpl
import com.makaraya.movapp.data.local.DataStoreRepository
import com.makaraya.movapp.data.local.room.UserDatabase
import com.makaraya.movapp.data.network.ApiKeyInterceptor
import com.makaraya.movapp.data.network.MoviesApiService
import com.makaraya.movapp.data.repository.LocalUserRepository
import com.makaraya.movapp.data.repository.NetworkMoviesRepository
import com.makaraya.movapp.di.MainModule.settingsDataStore
import com.makaraya.movapp.domain.repository.MoviesRepository
import com.makaraya.movapp.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    @Provides
    @Singleton
    fun provideApiKey(): String {
        return "c83fbf6abf03722da16db0d3a73d1fd0"
    }

    @Provides
    @Singleton
    fun provideMoviesApiService(): MoviesApiService {
        val baseUrl = "https://api.themoviedb.org/3/"
        val apiKey = provideApiKey()
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(apiKey))
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(moviesApiService: MoviesApiService): MoviesRepository =
        NetworkMoviesRepository(moviesApiService)

    @Provides
    @Singleton
    fun provideUserRepository(context: Application): UserRepository = LocalUserRepository(
        UserDatabase.getDatabase(context).userDao(),
        context.settingsDataStore
    )

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ) = DataStoreRepository(context = context)

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepositoryImpl(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

}