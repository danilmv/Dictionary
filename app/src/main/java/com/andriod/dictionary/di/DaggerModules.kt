package com.andriod.dictionary.di

import android.content.Context
import androidx.room.Room
import com.andriod.dictionary.MainActivity
import com.andriod.dictionary.model.CombineDataProvider
import com.andriod.dictionary.model.DataProvider
import com.andriod.dictionary.model.retrofit.RetrofitDataProvider
import com.andriod.dictionary.model.retrofit.SkyengApi
import com.andriod.dictionary.model.room.DictionaryDatabase
import com.andriod.dictionary.model.room.RoomDataProvider
import com.andriod.dictionary.wordlist.WordListFragment
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class RouterModule {
    @Provides
    @Singleton
    fun provideNavigationHolder(cicerone: Cicerone<Router>): NavigatorHolder =
        cicerone.getNavigatorHolder()

    @Provides
    @Singleton
    fun provideRouter(cicerone: Cicerone<Router>): Router = cicerone.router

    @Provides
    @Singleton
    fun provideCicerone(): Cicerone<Router> = Cicerone.create()
}

@Module
class RetrofitModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply { level = HttpLoggingInterceptor.Level.BODY }
            ).build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://dictionary.skyeng.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideGithubApi(retrofit: Retrofit): SkyengApi = retrofit.create(SkyengApi::class.java)

    @Provides
    @Singleton
    @Named("web")
    fun provideDataProvider(api: SkyengApi): DataProvider = RetrofitDataProvider(api)
}

@Module
class RoomModule(private val context: Context) {
    @Provides
    @Singleton
    fun provideGithubDatabase(context: Context): DictionaryDatabase =
        Room.databaseBuilder(
            context,
            DictionaryDatabase::class.java,
            "dictionary.db"
        ).build()

    @Provides
    @Singleton
    @Named("local")
    fun provideDataProvider(githubDatabase: DictionaryDatabase): DataProvider =
        RoomDataProvider(githubDatabase)

    @Provides
    fun provideContext(): Context = context
}

@Module
class CombineModule {
    @Provides
    @Singleton
    fun provideDataProvider(
        @Named("web") webDataProvider: DataProvider,
        @Named("local") localDataProvider: DataProvider
    ): DataProvider =
        CombineDataProvider(webDataProvider, localDataProvider)
}


@Component(modules = [RouterModule::class, RetrofitModule::class, RoomModule::class, CombineModule::class])
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: WordListFragment)

    fun getNavigationHolder(): NavigatorHolder
}
