package com.kalanasarange.starwarsplanets.di

import com.kalanasarange.starwarsplanets.api.StarWarsAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    /**
     * Retrofit provider provide response along with headers.
     * GSON has used as JSON to Obj convertor
     */
    @Provides
    fun provideRetrofit(): Retrofit {
        // Implementing Logging interceptor for http logging
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
        return Retrofit.Builder()
            .baseUrl(StarWarsAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientBuilder.build())
            .build()
    }

    /**
     * Main API interface provider. This will inject the APIs to
     * all the repositories to full fill the api needs.
     */
    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): StarWarsAPI =
        retrofit.create(StarWarsAPI::class.java)
}