package com.yelpapp.stevenwu.app;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.yelpapp.stevenwu.app.models.SearchResponse;
import com.yelpapp.stevenwu.app.service.YelpService;

import org.junit.Test;
import org.junit.runner.RunWith;

import retrofit2.Call;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.yelpapp.stevenwu.app", appContext.getPackageName());
    }

}
