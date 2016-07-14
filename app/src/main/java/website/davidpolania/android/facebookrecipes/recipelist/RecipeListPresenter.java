package website.davidpolania.android.facebookrecipes.recipelist;

import website.davidpolania.android.facebookrecipes.entities.Recipe;
import website.davidpolania.android.facebookrecipes.recipelist.events.RecipeListEvent;
import website.davidpolania.android.facebookrecipes.recipelist.ui.RecipeListView;

/**
 * Created by DavidPolania.
 */
public interface RecipeListPresenter {
    void onCreate();
    void onDestroy();

    void getRecipes();
    void removeRecipe(Recipe recipe);
    void toggleFavorite(Recipe recipe);
    void onEventMainThread(RecipeListEvent event);

    RecipeListView getView();
}
