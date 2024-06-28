import com.innoprog.android.di.AppComponent
import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.projects.projectsScreen.di.ProjectsFragmentModule
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [ProjectsFragmentModule::class]
)
interface ProjectsFragmentComponent : ScreenComponent {
    @Component.Builder
    interface Builder {
        fun build(): ProjectsFragmentComponent
        fun appComponent(appComponent: AppComponent): Builder
    }
}
