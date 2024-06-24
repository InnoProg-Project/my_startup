import com.innoprog.android.di.AppComponent
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.projects.projectsScreen.di.ProjectListDataModule
import com.innoprog.android.feature.projects.projectsScreen.di.ProjectListUseCaseModule
import com.innoprog.android.feature.projects.projectsScreen.di.ProjectsFragmentModule
import com.innoprog.android.network.data.NetworkModule
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [
        ProjectsFragmentModule::class,
        ProjectListDataModule::class,
        ProjectListUseCaseModule::class
    ]
)
interface ProjectsFragmentComponent : ScreenComponent {
    @Component.Builder
    interface Builder {
        fun build(): ProjectsFragmentComponent
        fun appComponent(appComponent: AppComponent): Builder
    }
}
