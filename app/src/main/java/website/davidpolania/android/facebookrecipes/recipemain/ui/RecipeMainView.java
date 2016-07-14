package website.davidpolania.android.facebookrecipes.recipemain.ui;

import website.davidpolania.android.facebookrecipes.entities.Recipe;

/**
 * Created by DavidPolania.
 */
public interface RecipeMainView {
    void showProgress();
    void hideProgress();
    void showUIElements();
    void hideUIElements();
    void saveAnimation();
    void dismissAnimation();

    void onRecipeSaved();
    void setRecipe(Recipe recipe);
    void onGetRecipeError(String error);
}
