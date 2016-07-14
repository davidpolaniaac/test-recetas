package website.davidpolania.android.facebookrecipes.recipelist.ui.adapters;

import website.davidpolania.android.facebookrecipes.entities.Recipe;

/**
 * Created by DavidPolania.
 */
public interface OnItemClickListener {
    void onFavClick(Recipe recipe);
    void onItemClick(Recipe recipe);
    void onDeleteClick(Recipe recipe);
}
