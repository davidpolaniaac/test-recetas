package website.davidpolania.android.facebookrecipes.recipelist;

import com.raizlabs.android.dbflow.list.FlowCursorList;

import java.util.Arrays;
import java.util.List;

import website.davidpolania.android.facebookrecipes.entities.Recipe;
import website.davidpolania.android.facebookrecipes.lib.EventBus;
import website.davidpolania.android.facebookrecipes.recipelist.events.RecipeListEvent;

/**
 * Created by DavidPolania.
 */
public class RecipeListRepositoryImpl implements RecipeListRepository {
    private EventBus eventBus;

    public RecipeListRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void getSavedRecipes() {
        FlowCursorList<Recipe> storedRecipes = new FlowCursorList<Recipe>(true, Recipe.class);
        post(RecipeListEvent.READ_EVENT, storedRecipes.getAll());
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        recipe.update();
        post(RecipeListEvent.UPDATE_EVENT);
    }

    @Override
    public void removeRecipe(Recipe recipe) {
        recipe.delete();
        post(RecipeListEvent.DELETE_EVENT, Arrays.asList(recipe));
    }

    private void post(int type, List<Recipe> recipes) {
        RecipeListEvent event = new RecipeListEvent();
        event.setRecipes(recipes);
        event.setType(type);
        eventBus.post(event);
    }

    private void post(int type) {
        post(type, null);
    }

}
