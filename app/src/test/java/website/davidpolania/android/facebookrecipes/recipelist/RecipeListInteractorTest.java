package website.davidpolania.android.facebookrecipes.recipelist;

import org.junit.Test;
import org.mockito.Mock;

import website.davidpolania.android.facebookrecipes.BaseTest;

import static org.mockito.Mockito.verify;

/**
 * Created by DavidPolania.
 */
public class RecipeListInteractorTest extends BaseTest {
    @Mock
    private RecipeListRepository repository;

    private RecipeListInteractor interactor;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        interactor = new RecipeListInteractorImpl(repository);
    }

    @Test
    public void testExecute_callsRepository() {
        interactor.execute();
        verify(repository).getSavedRecipes();
    }
}
