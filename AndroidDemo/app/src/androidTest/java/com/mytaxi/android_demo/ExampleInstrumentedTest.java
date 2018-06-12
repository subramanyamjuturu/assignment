 package com.mytaxi.android_demo;

 import android.support.test.espresso.Espresso;
 import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import com.mytaxi.android_demo.activities.MainActivity;

import junit.framework.AssertionFailedError;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;



/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {


 /*   @Test
   public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.mytaxi.android_demo", appContext.getPackageName());
    }*/

    //Launching App
    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);


    //This piece of code is for accepting and providing permission for installing the APP at very first time.
    public static void allowCurrentPermission(UiDevice device) {
        try {
            UiObject acessButton = device.findObject(new UiSelector().text("ALLOW"));
            acessButton.click();
        }
        catch(UiObjectNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
    }
    //Verifying alert is having allow and Deny buttons
    public static void assertViewWithTextIsVisible(UiDevice device, String text) {
        UiObject allowButton = device.findObject(new UiSelector().text(text));
        if (!allowButton.exists()) {
            throw new AssertionError("View with text <" + text + "> not found!");
        }


    }

        //calling methods for accepting and providing permission for installing the APP at very first time.
        @Test
        public void a_shouldDisplayPermissionRequestDialogAtStartup () {

            try

            {
                UiDevice device = UiDevice.getInstance();
                assertViewWithTextIsVisible(device, "ALLOW");
                assertViewWithTextIsVisible(device, "DENY");

                // cleanup for the next test
                allowCurrentPermission(device);

            }catch(Exception e)
            {
                System.out.println("just ignore");

                //Lanuching the app incase permission pop-up is absence from second time execution and if it throws exception
                 activityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

            }
    }

    //Home Assignment Starting from here
    @Test
    public void homeTakeAssignment() throws Exception
    {
        //Calling login method
        login();

        try {

            //wait concept used because in my lap Emulator performance is little poor so to avoid from failing I just used waits
            Thread.sleep(200);

            //cheching after login whether user is navigating to search screen.
            onView(withId(R.id.textSearch)).check(matches(isDisplayed()));

            //after sucessfull login calling driver operations
            calToDriver();


        } catch (AssertionFailedError e) {

            //login fails navigating back
            Espresso.pressBack();

            Thread.sleep(200);

            //Lanuching the app incase login operation fails due to any latencecy issues.
             activityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

             //calling login method
            login();
            //after sucessfull login calling driver operations
            calToDriver();
        }


    }

    public void calToDriver() throws Exception {

        //cheching after login whether user is navigating to search screen.
        Thread.sleep(500);
        onView(withId(R.id.textSearch)).check(matches(isDisplayed()));

        //searchign with two letter in the search page
        onView(withId(R.id.textSearch)).perform(typeText("sa"));
        Thread.sleep(500);

        //selecting Sarah Friedrich in the runtime dropdown list
        onView(withText("Sarah Friedrich")).inRoot(RootMatchers.isPlatformPopup()).perform(click());
        Thread.sleep(500);

        //calling to the user
        onView(withId(R.id.fab)).perform(click());

        Thread.sleep(5000);
    }

    public void login() throws Exception {

        //Entering the User name
        Thread.sleep(500);
        onView(withId(R.id.edt_username)).perform(typeText("whiteelephant261"), closeSoftKeyboard());

        //Entering the password
        onView(withId(R.id.edt_password)).perform(typeText("video1"), closeSoftKeyboard());
        //Thread.sleep(200);

        //clicking on lgin button
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(500);
    }


}
