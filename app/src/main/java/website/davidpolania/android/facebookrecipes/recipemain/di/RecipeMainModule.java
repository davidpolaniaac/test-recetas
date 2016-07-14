package website.davidpolania.android.facebookrecipes.recipemain.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import website.davidpolania.android.facebookrecipes.api.RecipeClient;
import website.davidpolania.android.facebookrecipes.api.RecipeService;
import website.davidpolania.android.facebookrecipes.lib.EventBus;
import website.davidpolania.android.facebookrecipes.recipemain.GetNextRecipeInteractor;
import website.davidpolania.android.facebookrecipes.recipemain.GetNextRecipeInteractorImpl;
import website.davidpolania.android.facebookrecipes.recipemain.RecipeMainPresenter;
import website.davidpolania.android.facebookrecipes.recipemain.RecipeMainPresenterImpl;
import website.davidpolania.android.facebookrecipes.recipemain.RecipeMainRepository;
import website.davidpolania.android.facebookrecipes.recipemain.RecipeMainRepositoryImpl;
import website.davidpolania.android.facebookrecipes.recipemain.SaveRecipeInteractor;
import website.davidpolania.android.facebookrecipes.recipemain.SaveRecipeInteractorImpl;
import website.davidpolania.android.facebookrecipes.recipemain.ui.RecipeMainView;

/**
 * Created by DavidPolania.
 */
@Module
public class RecipeMainModule {
    RecipeMainView view;

    public RecipeMainModule(RecipeMainView view) {
        this.view = view;
    }

    @Provides @Singleton
    RecipeMainView provideRecipeMainView() {
        return this.view;
    }

    @Provides @Singleton
    RecipeMainPresenter provideRecipeMainPresenter(EventBus eventBus, RecipeMainView view, SaveRecipeInteractor save, GetNextRecipeInteractor getNext) {
        return new RecipeMainPresenterImpl(eventBus, view, save, getNext);
    }

    @Provides @Singleton
    SaveRecipeInteractor provideSaveRecipeInteractor(RecipeMainRepository repository) {
        return new SaveRecipeInteractorImpl(repository);
    }

    @Provides @Singleton
    GetNextRecipeInteractor provideGetNextRecipeInteractor(RecipeMainRepository repository) {
        return new GetNextRecipeInteractorImpl(repository);
    }

    @Provides @Singleton
    RecipeMainRepository provideRecipeMainRepository(EventBus eventBus, RecipeService service) {
        return new RecipeMainRepositoryImpl(eventBus, service);
    }

    @Provides
    @Singleton
    RecipeService provideRecipeService() {
        RecipeClient client = new RecipeClient();
        return client.getRecipeService();
    }
}
