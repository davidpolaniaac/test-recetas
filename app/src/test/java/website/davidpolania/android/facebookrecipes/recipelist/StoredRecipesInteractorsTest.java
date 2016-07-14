package website.davidpolania.android.facebookrecipes.recipelist;

import org.junit.Test;
import org.mockito.Mock;

import website.davidpolania.android.facebookrecipes.BaseTest;
import website.davidpolania.android.facebookrecipes.entities.Recipe;

import static org.mockito.Mockito.verify;

/**
 * Created by DavidPolania.
 */
public class StoredRecipesInteractorsTest extends BaseTest {
    @Mock
    private RecipeListRepository repository;
    private Recipe recipe = new Recipe();

    private StoredRecipesInteractor storedInteractor;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        storedInteractor = new StoredRecipesInteractorImpl(repository);
    }

    @Test
    public void storedRecipesExecuteUpdate_callsRepository() {
        storedInteractor.executeUpdate(recipe);
        verify(repository).updateRecipe(recipe);
    }

    @Test
    public void storedRecipesExecuteDelete_callsRepository() {
        storedInteractor.executeDelete(recipe);
        verify(repository).removeRecipe(recipe);
    }
}
