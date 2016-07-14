package website.davidpolania.android.facebookrecipes.recipelist.di;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import website.davidpolania.android.facebookrecipes.entities.Recipe;
import website.davidpolania.android.facebookrecipes.lib.EventBus;
import website.davidpolania.android.facebookrecipes.lib.ImageLoader;
import website.davidpolania.android.facebookrecipes.recipelist.RecipeListInteractor;
import website.davidpolania.android.facebookrecipes.recipelist.RecipeListInteractorImpl;
import website.davidpolania.android.facebookrecipes.recipelist.RecipeListPresenter;
import website.davidpolania.android.facebookrecipes.recipelist.RecipeListPresenterImpl;
import website.davidpolania.android.facebookrecipes.recipelist.RecipeListRepository;
import website.davidpolania.android.facebookrecipes.recipelist.RecipeListRepositoryImpl;
import website.davidpolania.android.facebookrecipes.recipelist.StoredRecipesInteractor;
import website.davidpolania.android.facebookrecipes.recipelist.StoredRecipesInteractorImpl;
import website.davidpolania.android.facebookrecipes.recipelist.ui.RecipeListView;
import website.davidpolania.android.facebookrecipes.recipelist.ui.adapters.OnItemClickListener;
import website.davidpolania.android.facebookrecipes.recipelist.ui.adapters.RecipesAdapter;

/**
 * Created by DavidPolania.
 */
@Module
public class RecipeListModule {
    RecipeListView view;
    OnItemClickListener onItemClickListener;

    public RecipeListModule(RecipeListView view, OnItemClickListener onItemClickListener) {
        this.view = view;
        this.onItemClickListener = onItemClickListener;
    }

    @Provides @Singleton
    RecipeListView provideRecipeListView() {
        return this.view;
    }

    @Provides @Singleton
    RecipeListPresenter provideRecipeListPresenter(EventBus eventBus, RecipeListView view, RecipeListInteractor listInteractor, StoredRecipesInteractor storedInteractor) {
        return new RecipeListPresenterImpl(eventBus, view, listInteractor, storedInteractor);
    }

    @Provides @Singleton
    RecipeListInteractor provideRecipeListInteractor(RecipeListRepository repository) {
        return new RecipeListInteractorImpl(repository);
    }

    @Provides @Singleton
    StoredRecipesInteractor provideStoredRecipesInteractor(RecipeListRepository repository) {
        return new StoredRecipesInteractorImpl(repository);
    }

    @Provides @Singleton
    RecipeListRepository provideRecipeListRepository(EventBus eventBus) {
        return new RecipeListRepositoryImpl(eventBus);
    }

    @Provides @Singleton
    RecipesAdapter provideRecipesAdapter(List<Recipe> recipes, ImageLoader imageLoader, OnItemClickListener onItemClickListener) {
        return new RecipesAdapter(recipes, imageLoader, onItemClickListener);
    }

    @Provides @Singleton
    OnItemClickListener provideOnItemClickListener() {
        return this.onItemClickListener;
    }

    @Provides @Singleton
    List<Recipe> provideRecipesList() {
        return new ArrayList<Recipe>();
    }

}
