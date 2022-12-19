package com.geoactio.sae.hilt

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.geoactio.data.repository.ApiRepository
import com.geoactio.data.source.ApiDataSource
import com.geoactio.sae.BuildConfig
import com.geoactio.sae.data.server.restServices.ApiService
import com.geoactio.sae.data.server.restServices.datasource.RestDataSource
import com.geoactio.usecases.GetLineas
import com.geoactio.usecases.GetTrayectos
import com.google.android.gms.common.api.Api
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {
    private val retrofitTimeout = 20.toLong()
    private var service: ApiService? = null

    @Provides
    @Singleton
    fun getRetrofitService(internetStatus: Boolean) : ApiService {
        if (service == null) {
            val okHttpClient = HttpLoggingInterceptor().run {
                level = HttpLoggingInterceptor.Level.HEADERS
                OkHttpClient.Builder()
                    .readTimeout(retrofitTimeout, TimeUnit.SECONDS)
                    .connectTimeout(retrofitTimeout, TimeUnit.SECONDS)
                    .addInterceptor{ chain ->
                        if (internetStatus) {
                            chain.proceed(chain.request())
                        }
                        else {
                            throw NoNetworkException()
                        }
                    }
                    .build()
            }

            service = Retrofit.Builder().baseUrl(BuildConfig.SERVER_API_URL).client(okHttpClient).addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()))
                .build()
                .run {create(ApiService::class.java)
                }
        }

        return service!!
    }

    @Provides
    @Singleton
    fun internetProvider(@ApplicationContext context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    @HiltQualifiers.retrofitService
    @Provides
    fun apiServiceProvider(@ApplicationContext context: Context) : ApiService = getRetrofitService(internetProvider(context))

    @Provides
    fun getLineasProvider(@HiltQualifiers.retrofitService retrofitService: ApiService) : GetLineas = GetLineas(
        ApiRepository(RestDataSource(retrofitService)))

    @Provides
    fun getTrayectosProvider(@HiltQualifiers.retrofitService retrofitService: ApiService) : GetTrayectos = GetTrayectos(
        ApiRepository(RestDataSource(retrofitService)))
}

class NoNetworkException : IOException()
