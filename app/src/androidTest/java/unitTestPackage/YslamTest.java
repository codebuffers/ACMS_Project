package unitTestPackage;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;


import com.example.acms.MainActivity;
import com.example.acms.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class YslamTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);


    //JUnit local test to verify whether "Staff Login" button is activated (test passed)
    @Test
    public void staffLoginTest()
    {
        onView(withId(R.id.staffLogin)).check(matches(isClickable()));
        // .... related tests to be put here
    }

    //JUnit local test which returns passed test if the hint is correct (here it is not)
    @Test
    public void isCorrectAdminId() {
        onView(withId(R.id.AdminSuperIDId)).check(matches(withHint(" Super ID here")));
        // .... related tests here

    }


}
