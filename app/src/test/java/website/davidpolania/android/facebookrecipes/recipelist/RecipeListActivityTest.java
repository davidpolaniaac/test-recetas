package website.davidpolania.android.facebookrecipes.recipelist;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.facebook.FacebookActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.internal.ShadowExtractor;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import java.util.Arrays;
import java.util.List;

import website.davidpolania.android.facebookrecipes.BaseTest;
import website.davidpolania.android.facebookrecipes.BuildConfig;
import website.davidpolania.android.facebookrecipes.R;
import website.davidpolania.android.facebookrecipes.entities.Recipe;
import website.davidpolania.android.facebookrecipes.lib.ImageLoader;
import website.davidpolania.android.facebookrecipes.login.ui.LoginActivity;
import website.davidpolania.android.facebookrecipes.recipelist.ui.RecipeListActivity;
import website.davidpolania.android.facebookrecipes.recipelist.ui.RecipeListView;
import website.davidpolania.android.facebookrecipes.recipelist.ui.adapters.OnItemClickListener;
import website.davidpolania.android.facebookrecipes.recipelist.ui.adapters.RecipesAdapter;
import website.davidpolania.android.facebookrecipes.recipemain.ui.RecipeMainActivity;
import website.davidpolania.android.facebookrecipes.support.ShadowRecyclerView;
import website.davidpolania.android.facebookrecipes.support.ShadowRecyclerViewAdapter;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by DavidPolania.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21,
        shadows = {ShadowRecyclerView.class, ShadowRecyclerViewAdapter.class})
public class RecipeListActivityTest extends BaseTest {
    @Mock
    private RecipesAdapter adapter;

    @Mock
    private RecipeListPresenter presenter;

    @Mock
    private ImageLoader imageLoader;

    private RecipeListView view;
    private RecipeListActivity activity;
    private OnItemClickListener onItemClickListener;

    private ShadowActivity shadowActivity;
    private ActivityController<RecipeListActivity> controller;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        RecipeListActivity recipeListActivity = new RecipeListActivity(){
            @Override
            public void setTheme(int resid) {
                super.setTheme(R.style.AppTheme_NoActionBar);
            }

            @Override
            public RecipesAdapter getAdapter() {
                return adapter;
            }

            @Override
            public RecipeListPresenter getPresenter() {
                return presenter;
            }
        };

        controller = ActivityController.of(Robolectric.getShadowsAdapter(), recipeListActivity).create().visible();
        activity = controller.get();
        shadowActivity = shadowOf(activity);

        view = (RecipeListView)activity;
        onItemClickListener  = (OnItemClickListener)activity;
    }

    @Test
    public void onActivityCreated_getsRecipesFromDataSource() {
        verify(presenter).onCreate();
        verify(presenter).getRecipes();
    }

    @Test
    public void onActivityDestroyed_destroyPresenter() {
        controller.destroy();
        verify(presenter).onDestroy();
    }

    @Test
    public void getRecipesFromPresenter_setInAdapter() {
        List<Recipe> recipeList = Arrays.asList(new Recipe[]{
                new Recipe(),
                new Recipe(),
                new Recipe()});

        ArgumentCaptor<List> argumentCaptor = ArgumentCaptor.forClass(List.class);
        view.setRecipes(recipeList);
        verify(adapter).setRecipes(argumentCaptor.capture());
        assertEquals(recipeList.size(), argumentCaptor.getValue().size());
    }

    @Test
    public void recipeUpdatedFromPresenter_adapterUpdated() {
        view.recipeUpdated();
        verify(adapter).notifyDataSetChanged();
    }

    @Test
    public void recipeDeletedFromPresenter_adapterUpdated() {
        Recipe recipe = new Recipe();
        view.recipeDeleted(recipe);
        verify(adapter).removeRecipe(recipe);
    }

    @Test
    public void logoutMenuClicked_launchLoginActivity() {
        shadowActivity.clickMenuItem(R.id.action_logout);
        Intent intent = shadowActivity.peekNextStartedActivityForResult().intent;
        assertEquals(intent.getComponent(), new ComponentName(controller.get(), LoginActivity.class));
    }

    @Test
    public void mainMenuClick_launchRecipeMainActivity() {
        shadowActivity.clickMenuItem(R.id.action_main);
        Intent intent = shadowActivity.peekNextStartedActivityForResult().intent;
        assertEquals(intent.getComponent(), new ComponentName(controller.get(), RecipeMainActivity.class));
    }

    @Test
    public void recyclerViewScrollToTop_OnToolbarClick() {
        int scrollPosition = 1;
        int topscrollPosition = 0;
        Toolbar toolbar = (Toolbar)activity.findViewById(R.id.toolbar);
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerView);
        ShadowRecyclerView shadowRecyclerView = (ShadowRecyclerView) ShadowExtractor.extract(recyclerView);

        recyclerView.smoothScrollToPosition(scrollPosition);
        assertEquals(scrollPosition, shadowRecyclerView.getSmoothScrollToPosition());

        toolbar.performClick();
        assertEquals(topscrollPosition, shadowRecyclerView.getSmoothScrollToPosition());
    }

    @Test
    public void recyclerView_itemClicked() {
        List<Recipe> recipeList = getRecipesToPopulateAdapter();
        RecipesAdapter adapterPopulated = new RecipesAdapter(recipeList, imageLoader, onItemClickListener);
        RecyclerView recyclerView = new RecyclerView(activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapterPopulated);

        int positionToClick = 0;
        ShadowRecyclerViewAdapter shadowAdapter = (ShadowRecyclerViewAdapter) ShadowExtractor.extract(recyclerView.getAdapter());
        shadowAdapter.itemVisible(positionToClick);
        shadowAdapter.performItemClick(positionToClick);

        Intent intent = shadowActivity.peekNextStartedActivityForResult().intent;
        assertEquals(intent.getAction(), Intent.ACTION_VIEW);
        assertEquals(intent.getDataString(), recipeList.get(positionToClick).getSourceURL());
    }

    @Test
    public void recyclerView_favoriteClicked() {
        List<Recipe> recipeList = getRecipesToPopulateAdapter();
        RecipesAdapter adapterPopulated = new RecipesAdapter(recipeList, imageLoader, onItemClickListener);
        RecyclerView recyclerView = new RecyclerView(activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapterPopulated);

        int positionToClick = 0;
        ShadowRecyclerViewAdapter shadowAdapter = (ShadowRecyclerViewAdapter) ShadowExtractor.extract(recyclerView.getAdapter());
        shadowAdapter.itemVisible(positionToClick);
        shadowAdapter.performClickOverViewInViewHolder(positionToClick, R.id.imgFav);

        verify(presenter).toggleFavorite(recipeList.get(positionToClick));
    }

    @Test
    public void recyclerView_removeClicked() {
        List<Recipe> recipeList = getRecipesToPopulateAdapter();
        RecipesAdapter adapterPopulated = new RecipesAdapter(recipeList, imageLoader, onItemClickListener);
        RecyclerView recyclerView = new RecyclerView(activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapterPopulated);

        int positionToClick = 0;
        ShadowRecyclerViewAdapter shadowAdapter = (ShadowRecyclerViewAdapter) ShadowExtractor.extract(recyclerView.getAdapter());
        shadowAdapter.itemVisible(positionToClick);
        shadowAdapter.performClickOverViewInViewHolder(positionToClick, R.id.imgDelete);

        verify(presenter).removeRecipe(recipeList.get(positionToClick));
    }

    @Test
    public void recyclerView_fbShareClicked() {


        List<Recipe> recipeList = getRecipesToPopulateAdapter();
        RecipesAdapter adapterPopulated = new RecipesAdapter(recipeList, imageLoader, onItemClickListener);
        RecyclerView recyclerView = new RecyclerView(activity);
        recyclerView.setAdapter(adapterPopulated);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        int positionToClick = 0;
        ShadowRecyclerViewAdapter shadowAdapter = (ShadowRecyclerViewAdapter) ShadowExtractor.extract(recyclerView.getAdapter());
        shadowAdapter.itemVisible(positionToClick);
        shadowAdapter.performClickOverViewInViewHolder(positionToClick, R.id.fbShare);

        Intent intent = shadowActivity.peekNextStartedActivityForResult().intent;
        assertEquals(intent.getComponent(), new ComponentName(RuntimeEnvironment.application, FacebookActivity.class));
    }

    @Test
    public void recyclerView_fbSendClicked() {
        List<Recipe> recipeList = getRecipesToPopulateAdapter();
        RecipesAdapter adapterPopulated = new RecipesAdapter(recipeList, imageLoader, onItemClickListener);
        RecyclerView recyclerView = new RecyclerView(activity);
        recyclerView.setAdapter(adapterPopulated);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        int positionToClick = 0;
        ShadowRecyclerViewAdapter shadowAdapter = (ShadowRecyclerViewAdapter) ShadowExtractor.extract(recyclerView.getAdapter());
        shadowAdapter.itemVisible(positionToClick);
        shadowAdapter.performClickOverViewInViewHolder(positionToClick, R.id.fbSend);

        Intent intent = shadowActivity.peekNextStartedActivityForResult().intent;
        assertEquals(intent.getComponent(), new ComponentName(RuntimeEnvironment.application, FacebookActivity.class));
    }

    private List<Recipe> getRecipesToPopulateAdapter(){
        Recipe recipe = new Recipe();
        recipe.setSourceURL("http://google.com");
        return Arrays.asList(new Recipe[]{recipe});
    }

}
