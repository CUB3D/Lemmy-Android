package pw.cub3d.lemmy.core.dagger

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import pw.cub3d.lemmy.core.networking.LemmyApiInterface
import pw.cub3d.lemmy.core.networking.adapter.ZonedDateTimeAdapter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {

    @Singleton
    @Provides
    @Named("apiUrl")
    fun provideApiUrl() = "https://dev.lemmy.ml/api/v1/"

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(ZonedDateTimeAdapter())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        @Named("apiUrl") apiUrl: String,
        moshi: Moshi
    ) = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(apiUrl)
        .build()

    @Singleton
    @Provides
    fun provideApiClient(retrofit: Retrofit) = retrofit.create(LemmyApiInterface::class.java)
}