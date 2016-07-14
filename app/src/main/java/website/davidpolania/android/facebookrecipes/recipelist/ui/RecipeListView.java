package website.davidpolania.android.facebookrecipes.recipelist.ui;

import java.util.List;

import website.davidpolania.android.facebookrecipes.entities.Recipe;

/**
 * Created by DavidPolania.
 */
public interface RecipeListView {
    void setRecipes(List<Recipe> data);
    void recipeUpdated();
    void recipeDeleted(Recipe recipe);
}
