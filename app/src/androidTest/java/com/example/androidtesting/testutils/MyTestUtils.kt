import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Checks
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import kotlinx.android.synthetic.main.item_model.view.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class MyTestUtils {


    /*
    // select any name from the list,
    // the name should be showed on the detail activity
        @Test
        fun selectedNameIsDisplayedOnDetailTest() {
            // click with position, to scroll the RecyclerView to the desired item and perform a ViewAction on it
            // in this case, the list is showed as sorted list, so followings doesn't work.
            //        onView(withId(R.id.recyclerView))
            //            .perform(RecyclerViewActions.actionOnItemAtPosition<ModelAdapter.ModelHolder>(position, click()))

            // use as below if the order of the list is not the same as displayed list.

            // GIVEN
            val name = testModel[randIdx].name.toString()

            // WHEN

            // THEN
            // click with text
            Thread.sleep(1000)
            onView(allOf(withId(R.id.recyclerView)))
                .perform(
                    RecyclerViewActions.actionOnHolderItem(
                        myWithItemSubject<ModelAdapter.ModelHolder>(name), click()
                    )
                )

            onView(allOf(withText(name)))
        }

     */


    private inline fun <reified T : RecyclerView.ViewHolder?> myWithItemSubject(subject: String): Matcher<RecyclerView.ViewHolder> {
        Checks.checkNotNull(subject)
        return object : BoundedMatcher<RecyclerView.ViewHolder, T>(
            T::class.java
        ) {
            override fun matchesSafely(viewHolder: T): Boolean {
                val subjectTextView =
                    viewHolder?.itemView?.tvModelName as TextView
                return subject == subjectTextView.text
                    .toString() && subjectTextView.visibility == View.VISIBLE
            }

            override fun describeTo(description: org.hamcrest.Description?) {
                description?.appendText("item with subject: $subject")
            }
        }
    }

    /* return Matcher<View?> according to the order on the screen.
        https://stackoverflow.com/questions/29378552/in-espresso-how-to-avoid-ambiguousviewmatcherexception-when-multiple-views-matc
        usage : onView(withIndex(withId(R.id.my_view), 2)).perform(click());
                > It will perform a click action on the third instance of R.id.my_view.
     */
    fun withIndex(
        matcher: Matcher<View?>,
        index: Int
    ): Matcher<View?>? {
        return object : TypeSafeMatcher<View?>() {
            var currentIndex = 0
            override fun describeTo(description: Description) {
                description.appendText("with index: ")
                description.appendValue(index)
                matcher.describeTo(description)
            }

            override fun matchesSafely(view: View?): Boolean {
                return matcher.matches(view) && currentIndex++ == index
            }
        }
    }


    fun ViewInteraction.isDisplayed(): Boolean {
        try {
            check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            return true
        } catch (e: NoMatchingViewException) {
            return false
        }
    }
}