package website.davidpolania.android.facebookrecipes.recipemain;

import org.junit.Test;
import org.mockito.Mock;

import website.davidpolania.android.facebookrecipes.BaseTest;
import website.davidpolania.android.facebookrecipes.entities.Recipe;

import static org.mockito.Mockito.verify;

/**
 * Created by DavidPolania.
 */
public class SaveRecipeInteractorTest extends BaseTest {
    @Mock
    private RecipeMainRepository repository;

    private SaveRecipeInteractor interactor;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        interactor = new SaveRecipeInteractorImpl(repository);
    }

    @Test
    public void testExecute_callsRepository() {
        Recipe recipe = new Recipe();

        interactor.execute(recipe);
        verify(repository).saveRecipe(recipe);
    }
}
