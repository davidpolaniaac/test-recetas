package website.davidpolania.android.facebookrecipes.recipelist;

/**
 * Created by DavidPolania.
 */
public class RecipeListInteractorImpl implements RecipeListInteractor {
    private RecipeListRepository repository;

    public RecipeListInteractorImpl(RecipeListRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute() {
        repository.getSavedRecipes();
    }
}
