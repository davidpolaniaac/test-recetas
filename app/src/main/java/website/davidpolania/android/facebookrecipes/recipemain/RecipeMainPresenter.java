package website.davidpolania.android.facebookrecipes.recipemain;

import website.davidpolania.android.facebookrecipes.entities.Recipe;
import website.davidpolania.android.facebookrecipes.recipemain.events.RecipeMainEvent;
import website.davidpolania.android.facebookrecipes.recipemain.ui.RecipeMainView;

/**
 * Created by DavidPolania.
 */
public interface RecipeMainPresenter {
    void onCreate();
    void onDestroy();

    void dismissRecipe();
    void getNextRecipe();
    void saveRecipe(Recipe recipe);
    void onEventMainThread(RecipeMainEvent event);

    RecipeMainView getView();
}
