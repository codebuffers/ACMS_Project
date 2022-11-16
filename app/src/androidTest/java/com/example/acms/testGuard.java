package com.example.acms;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class testGuard {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testGuard() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.staffLogin), withText("Staff Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        materialButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.goGuardLogin), withText("Login Guard "),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        materialButton2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.GuardSuperIDId),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                3)));
        appCompatEditText.perform(scrollTo(), click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.GuardSuperIDId),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                3)));
        appCompatEditText2.perform(scrollTo(), replaceText("0011guard"), closeSoftKeyboard());

        pressBack();

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.LoginBtnId), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                5)));
        materialButton3.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.btnDTU), withText("Did not turn up"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        13),
                                0),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.btnConfirmNoShow), withText("Confirm No Show"),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.orhanobut.dialogplus.R.id.dialogplus_view_container),
                                        0),
                                2),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction viewGroup = onView(
                allOf(withParent(allOf(withId(android.R.id.content),
                                withParent(withId(androidx.constraintlayout.widget.R.id.decor_content_parent)))),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
