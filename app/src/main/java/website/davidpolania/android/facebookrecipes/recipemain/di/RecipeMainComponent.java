package website.davidpolania.android.facebookrecipes.recipemain.di;

import javax.inject.Singleton;

import dagger.Component;
import website.davidpolania.android.facebookrecipes.lib.ImageLoader;
import website.davidpolania.android.facebookrecipes.lib.di.LibsModule;
import website.davidpolania.android.facebookrecipes.recipemain.RecipeMainPresenter;

/**
 * Created by DavidPolania.
 */
@Singleton
@Component(modules = {RecipeMainModule.class, LibsModule.class})
public interface RecipeMainComponent {
    //void inject(RecipeMainActivity activity);
    ImageLoader getImageLoader();
    RecipeMainPresenter getPresenter();
}
