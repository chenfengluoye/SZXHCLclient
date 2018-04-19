package com.qqjyb.szxhcl;

import android.content.Context;


/**
 * Instrumentation tests, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under tests.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.qqjyb.szxhcl", appContext.getPackageName());
    }
}
