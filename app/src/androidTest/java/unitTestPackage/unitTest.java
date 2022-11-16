package unitTestPackage;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.supportsInputMethods;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.endsWith;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.acms.ActivityRequestVisit;
import com.example.acms.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class unitTest {
    @Rule
    public ActivityScenarioRule<ActivityRequestVisit> activityRule =
            new ActivityScenarioRule<ActivityRequestVisit>(ActivityRequestVisit.class);


    @Test
    public void inputSupport() {
        onView(allOf(withId(R.id.VisitorNameId), supportsInputMethods()));
        onView(withClassName(endsWith("Name")));
    }

    @Test
    public void requestTests()
    {
        onView(withId(R.id.GenerateTicketBtnId)).check(matches(isClickable()));
    }



}