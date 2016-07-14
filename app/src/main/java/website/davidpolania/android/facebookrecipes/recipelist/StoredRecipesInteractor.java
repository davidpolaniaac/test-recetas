package website.davidpolania.android.facebookrecipes.recipelist;

import website.davidpolania.android.facebookrecipes.entities.Recipe;

/**
 * Created by DavidPolania.
 */
public interface StoredRecipesInteractor {
    void executeUpdate(Recipe recipe);
    void executeDelete(Recipe recipe);
}
