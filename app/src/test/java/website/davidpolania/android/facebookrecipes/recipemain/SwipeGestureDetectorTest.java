package website.davidpolania.android.facebookrecipes.recipemain;

import android.view.MotionEvent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import website.davidpolania.android.facebookrecipes.BaseTest;
import website.davidpolania.android.facebookrecipes.BuildConfig;
import website.davidpolania.android.facebookrecipes.recipemain.ui.SwipeGestureDetector;
import website.davidpolania.android.facebookrecipes.recipemain.ui.SwipeGestureListener;
import website.davidpolania.android.facebookrecipes.support.ShadowRecyclerView;
import website.davidpolania.android.facebookrecipes.support.ShadowRecyclerViewAdapter;

import static org.mockito.Mockito.verify;

/**
 * Created by DavidPolania.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21,
        shadows = {ShadowRecyclerView.class, ShadowRecyclerViewAdapter.class})
public class SwipeGestureDetectorTest extends BaseTest {
    @Mock
    private SwipeGestureListener listener;

    private SwipeGestureDetector detector;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        detector = new SwipeGestureDetector(listener);
    }

    @Test
    public void swipeRightShouldCallKeepOnListener(){
        long downTime = 0;
        long moveTime = downTime + 500;
        long upTime = downTime + 100;
        float xStart = 200;
        float yStart = 200;
        float xEnd = 500;
        float yEnd = 250;

        MotionEvent e1 = MotionEvent.obtain(downTime, moveTime, MotionEvent.ACTION_MOVE, xStart, yStart, 0);
        MotionEvent e2 = MotionEvent.obtain(downTime, upTime, MotionEvent.ACTION_UP, xEnd, yEnd, 0);

        //greater than treshold
        float velocityX = 120;

        detector.onFling(e1, e2, velocityX, 0);
        verify(listener).onKeep();
    }

    @Test
    public void swipeLeftShouldCallDismissOnListener(){
        long downTime = 0;
        long moveTime = downTime + 500;
        long upTime = downTime + 100;
        float xStart = 200;
        float yStart = 200;
        float xEnd = -500;
        float yEnd = 250;

        MotionEvent e1 = MotionEvent.obtain(downTime, moveTime, MotionEvent.ACTION_MOVE, xStart, yStart, 0);
        MotionEvent e2 = MotionEvent.obtain(downTime, upTime, MotionEvent.ACTION_UP, xEnd, yEnd, 0);

        //greater than treshold
        float velocityX = 120;

        detector.onFling(e1, e2, velocityX, 0);
        verify(listener).onDismiss();
    }

}
