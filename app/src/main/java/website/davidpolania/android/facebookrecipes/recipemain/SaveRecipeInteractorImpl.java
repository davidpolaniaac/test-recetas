package website.davidpolania.android.facebookrecipes.recipemain;

import website.davidpolania.android.facebookrecipes.entities.Recipe;

/**
 * Created by DavidPolania.
 */
public class SaveRecipeInteractorImpl implements SaveRecipeInteractor {
    RecipeMainRepository repository;

    public SaveRecipeInteractorImpl(RecipeMainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(Recipe recipe) {
        repository.saveRecipe(recipe);
    }
}
