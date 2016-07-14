package website.davidpolania.android.facebookrecipes.recipemain;

import java.util.Random;

import website.davidpolania.android.facebookrecipes.BuildConfig;
import website.davidpolania.android.facebookrecipes.api.RecipeSearchResponse;
import website.davidpolania.android.facebookrecipes.api.RecipeService;
import website.davidpolania.android.facebookrecipes.entities.Recipe;
import website.davidpolania.android.facebookrecipes.lib.EventBus;
import website.davidpolania.android.facebookrecipes.recipemain.events.RecipeMainEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DavidPolania.
 */
public class RecipeMainRepositoryImpl implements RecipeMainRepository {
    private int recipePage;
    private EventBus eventBus;
    private RecipeService service;

    public RecipeMainRepositoryImpl(EventBus eventBus, RecipeService service) {
        this.eventBus = eventBus;
        this.service = service;
        this.recipePage = new Random().nextInt(RECIPE_RANGE);
    }

    @Override
    public void setRecipePage(int recipePage) {
        this.recipePage = recipePage;
    }

    @Override
    public void getNextRecipe() {
        Call<RecipeSearchResponse> call = service.search(BuildConfig.FOOD_API_KEY, RECENT_SORT, COUNT, recipePage);
        call.enqueue(new Callback<RecipeSearchResponse>() {
            @Override
            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                if (response.isSuccess()) {
                    RecipeSearchResponse recipeSearchResponse = response.body();
                    if (recipeSearchResponse.getCount() == 0){
                        setRecipePage(new Random().nextInt(RECIPE_RANGE));
                        getNextRecipe();
                    } else {
                        Recipe recipe = recipeSearchResponse.getFirstRecipe();
                        if (recipe != null) {
                            post(recipe);
                        } else {
                            post(response.message(), RecipeMainEvent.NEXT_EVENT);
                        }

                    }
                } else {
                    post(response.message(), RecipeMainEvent.NEXT_EVENT);
                }
            }

            @Override
            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {
                post(t.getLocalizedMessage(), RecipeMainEvent.NEXT_EVENT);
            }
        });
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        recipe.save();
        post();
    }

    private void post() {
        RecipeMainEvent event = new RecipeMainEvent();
        event.setType(RecipeMainEvent.SAVE_EVENT);
        eventBus.post(event);
    }

    private void post(String error, int type) {
        RecipeMainEvent event = new RecipeMainEvent();
        event.setType(type);
        event.setError(error);
        eventBus.post(event);
    }

    private void post(Recipe recipe) {
        RecipeMainEvent event = new RecipeMainEvent();
        event.setType(RecipeMainEvent.NEXT_EVENT);
        event.setRecipe(recipe);
        eventBus.post(event);
    }
}
