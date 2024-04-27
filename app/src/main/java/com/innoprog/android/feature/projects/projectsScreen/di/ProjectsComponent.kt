import com.innoprog.android.di.ScreenComponent
import com.innoprog.android.feature.projects.projectsScreen.di.ProjectsModule
import dagger.Component

@Component(
    modules = [ProjectsModule::class]
)
interface ProjectsComponent : ScreenComponent
