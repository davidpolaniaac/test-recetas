package website.davidpolania.android.facebookrecipes.recipelist;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import website.davidpolania.android.facebookrecipes.BaseTest;
import website.davidpolania.android.facebookrecipes.entities.Recipe;
import website.davidpolania.android.facebookrecipes.lib.EventBus;
import website.davidpolania.android.facebookrecipes.recipelist.events.RecipeListEvent;
import website.davidpolania.android.facebookrecipes.recipelist.ui.RecipeListView;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.verify;

/**
 * Created by DavidPolania.
 */
public class RecipeListPresenterTest extends BaseTest {
    @Mock
    private EventBus eventBus;
    @Mock
    private RecipeListView view;
    @Mock
    private RecipeListInteractor listInteractor;
    @Mock
    private StoredRecipesInteractor storedInteractor;

    private RecipeListPresenter presenter;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        presenter = new RecipeListPresenterImpl(eventBus, view, listInteractor, storedInteractor);
    }

    @Test
    public void onCreate_SubscribedToEventBus() {
        presenter.onCreate();
        verify(eventBus).register(presenter);
    }

    @Test
    public void onDestroy_UnsubscribedToEventBusAndViewDestroyed() throws NoSuchFieldException {
        presenter.onDestroy();

        assertNull(presenter.getView());
        verify(eventBus).unregister(presenter);
    }

    @Test
    public void getRecipes_executesInteractor() {
        presenter.getRecipes();
        verify(listInteractor).execute();
    }

    @Test
    public void toggleFavorite_true() {
        Recipe recipe = new Recipe();
        boolean favorite = true;
        recipe.setFavorite(favorite);

        ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        presenter.toggleFavorite(recipe);
        verify(storedInteractor).executeUpdate(recipeArgumentCaptor.capture());
        assertEquals(!favorite, recipeArgumentCaptor.getValue().getFavorite());
    }

    @Test
    public void toggleFavorite_false() {
        Recipe recipe = new Recipe();
        boolean favorite = false;
        recipe.setFavorite(favorite);

        ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        presenter.toggleFavorite(recipe);
        verify(storedInteractor).executeUpdate(recipeArgumentCaptor.capture());
        assertEquals(!favorite, recipeArgumentCaptor.getValue().getFavorite());
    }

    @Test
    public void removeRecipe_executesInteractor() {
        Recipe recipe = new Recipe();
        presenter.removeRecipe(recipe);
        verify(storedInteractor).executeDelete(recipe);
    }

    @Test
    public void onReadEvent_setRecipesOnView() {
        List<Recipe> recipeList = Arrays.asList(new Recipe[]{
                new Recipe(),
                new Recipe(),
                new Recipe()});

        RecipeListEvent listEvent = new RecipeListEvent();
        listEvent.setType(RecipeListEvent.READ_EVENT);
        listEvent.setRecipes(recipeList);

        presenter.onEventMainThread(listEvent);
        verify(view).setRecipes(recipeList);
    }

    @Test
    public void onUpdateEvent_callUpdateMethodOnView() {
        RecipeListEvent listEvent = new RecipeListEvent();
        listEvent.setType(RecipeListEvent.UPDATE_EVENT);

        presenter.onEventMainThread(listEvent);
        verify(view).recipeUpdated();
    }

    @Test
    public void onDeleteEvent_removesFromView() {
        Recipe recipe = new Recipe();
        RecipeListEvent listEvent = new RecipeListEvent();
        listEvent.setType(RecipeListEvent.DELETE_EVENT);
        listEvent.setRecipes(Arrays.asList(new Recipe[]{recipe}));

        presenter.onEventMainThread(listEvent);
        verify(view).recipeDeleted(recipe);
    }
}
