package website.davidpolania.android.facebookrecipes.recipelist;

import website.davidpolania.android.facebookrecipes.entities.Recipe;

/**
 * Created by DavidPolania.
 */
public class StoredRecipesInteractorImpl implements StoredRecipesInteractor {
    private RecipeListRepository repository;

    public StoredRecipesInteractorImpl(RecipeListRepository repository) {
        this.repository = repository;
    }

    @Override
    public void executeUpdate(Recipe recipe) {
        repository.updateRecipe(recipe);
    }

    @Override
    public void executeDelete(Recipe recipe) {
        repository.removeRecipe(recipe);
    }
}
