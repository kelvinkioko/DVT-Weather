package dvt.weatherapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dvt.weatherapp.data.location.DefaultLocationTracker
import dvt.weatherapp.domain.location.LocationTracker
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocationModule {

    @Binds
    @Singleton
    fun bindLocationTracker(defaultLocationTracker: DefaultLocationTracker): LocationTracker
}
