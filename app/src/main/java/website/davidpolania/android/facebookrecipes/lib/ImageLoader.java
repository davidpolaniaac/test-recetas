package website.davidpolania.android.facebookrecipes.lib;

import android.widget.ImageView;

/**
 * Created by DavidPolania.
 */
public interface ImageLoader {
    void load(ImageView imageView, String URL);
    void setOnFinishedImageLoadingListener(Object object);
}
