package ge.george.openweather.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import ge.george.openweather.data.network.OpenWeatherAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun getRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(OpenWeatherAPI.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getClient())
        .build()

    @Singleton
    @Provides
    fun getApi(retrofit: Retrofit): OpenWeatherAPI = retrofit.create(OpenWeatherAPI::class.java)

    private fun getClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(httpLoggingInterceptor)
        return client.build()
    }
}