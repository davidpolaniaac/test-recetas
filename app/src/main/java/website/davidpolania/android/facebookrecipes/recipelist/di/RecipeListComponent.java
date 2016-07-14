package website.davidpolania.android.facebookrecipes.recipelist.di;

import javax.inject.Singleton;

import dagger.Component;
import website.davidpolania.android.facebookrecipes.lib.di.LibsModule;
import website.davidpolania.android.facebookrecipes.recipelist.RecipeListPresenter;
import website.davidpolania.android.facebookrecipes.recipelist.ui.adapters.RecipesAdapter;

/**
 * Created by DavidPolania.
 */
@Singleton
@Component(modules = {RecipeListModule.class, LibsModule.class})
public interface RecipeListComponent {
    //void inject(RecipeListActivity activity);
    RecipeListPresenter getPresenter();
    RecipesAdapter getAdapter();
}
