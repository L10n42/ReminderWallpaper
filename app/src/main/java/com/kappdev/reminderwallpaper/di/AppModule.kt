package com.kappdev.reminderwallpaper.di

import android.app.Application
import androidx.room.Room
import com.kappdev.reminderwallpaper.wallpapers_feature.data.data_source.WallpaperDatabase
import com.kappdev.reminderwallpaper.wallpapers_feature.data.repository.WallpapersRepositoryImpl
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.repository.WallpapersRepository
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case.GetWallpaper
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case.GetWallpapers
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case.InsertWallpaper
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case.MoveToImages
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case.QuotesPainter
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case.RemoveWallpaper
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case.SetWallpaper
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case.ShareImage
import com.kappdev.reminderwallpaper.wallpapers_feature.domain.use_case.TextPainter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWallpaperDatabase(app: Application): WallpaperDatabase {
        return Room.databaseBuilder(
            app,
            WallpaperDatabase::class.java,
            WallpaperDatabase.NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideWallpaperRepository(database: WallpaperDatabase): WallpapersRepository {
        return WallpapersRepositoryImpl(database.wallpaperDao)
    }

    @Provides
    @Singleton
    fun provideQuotesPainter(app: Application): QuotesPainter {
        return QuotesPainter(app)
    }

    @Provides
    @Singleton
    fun provideTextPainter(app: Application): TextPainter {
        return TextPainter(app)
    }

    @Provides
    @Singleton
    fun provideMoveToImages(app: Application): MoveToImages {
        return MoveToImages(app)
    }

    @Provides
    @Singleton
    fun provideInsertWallpaper(repository: WallpapersRepository): InsertWallpaper {
        return InsertWallpaper(repository)
    }

    @Provides
    @Singleton
    fun provideGetWallpapers(repository: WallpapersRepository): GetWallpapers {
        return GetWallpapers(repository)
    }

    @Provides
    @Singleton
    fun provideGetWallpaper(repository: WallpapersRepository): GetWallpaper {
        return GetWallpaper(repository)
    }

    @Provides
    @Singleton
    fun provideRemoveWallpaper(repository: WallpapersRepository): RemoveWallpaper {
        return RemoveWallpaper(repository)
    }

    @Provides
    @Singleton
    fun provideSetWallpaper(app: Application): SetWallpaper {
        return SetWallpaper(app)
    }

    @Provides
    @Singleton
    fun provideShareImage(app: Application): ShareImage {
        return ShareImage(app)
    }
}