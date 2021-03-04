
import android.content.res.Resources
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class RecyclerViewMatcher(recyclerViewId: Int) {
    /*
    @Test
    fun dataAccuracyTest() {

        // GIVEN
        val testModel = testModel[randIdx]
        val name = testModel[randIdx].name.toString()

        // WHEN
        Thread.sleep(1000)
        onView(allOf(withId(R.id.recyclerView)))
            .perform(
                RecyclerViewActions.actionOnHolderItem(
                    myWithItemSubject<ModelAdapter.ModelHolder>(name), click()
                )
            )

        // THEN
        onView(allOf(withId(R.id.detailRecyclerView), isDisplayed()))


        val recyclerViewItemCount = modelAdapter.itemCount


        val map = HashMap<String, String?>()
        map[testModel.getNameTitle()] = testModel.name
        map[testModel.getAbbreviationTitle()] = testModel.abbreviation
        map[testModel.getCapitalTitle()] = testModel.capital
        map[testModel.getLargest_cityTitle()] = testModel.largest_city
        map[testModel.getEstablished_dateTitle()] = testModel.established_date
        map[testModel.getPopulationTitle()] = testModel.population.toString()
        map[testModel.getTotal_area_km2Title()] = testModel.total_area_km2.toString()
        map[testModel.getLand_area_km2Title()] = testModel.land_area_km2.toString()
        map[testModel.getWater_area_km2Title()] = testModel.water_area_km2.toString()
        map[testModel.getNumber_of_repsTitle()] = testModel.number_of_reps.toString()

        val keys = map.keys

        keys.forEach{
            val value = map[it]
            onView(
                RecyclerViewMatcher(R.id.detailRecyclerView)
                    .atPositionOnView(
                        getIndex(recyclerViewItemCount, R.id.detailRecyclerView, R.id.attribute, it)
                        ,R.id.value)
            ).check(matches(withText(value)))
        }
    }

     */

    private val recyclerViewId: Int = recyclerViewId
    fun atPosition(position: Int): Matcher<View> {
        return atPositionOnView(position, -1)
    }

    fun atPositionOnView(
        position: Int,
        targetViewId: Int
    ): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            var resources: Resources? = null
            var childView: View? = null
            override fun describeTo(description: Description) {
                var idDescription = Integer.toString(recyclerViewId)
                if (resources != null) {
                    idDescription = try {
                        resources!!.getResourceName(recyclerViewId)
                    } catch (var4: Resources.NotFoundException) {
                        String.format(
                            "%s (resource name not found)",
                            *arrayOf<Any>(Integer.valueOf(recyclerViewId))
                        )
                    }
                }
                description.appendText("with id: $idDescription")
            }

            public override fun matchesSafely(view: View): Boolean {
                resources = view.resources
                if (childView == null) {
                    val recyclerView = view.rootView
                        .findViewById<View>(recyclerViewId) as RecyclerView
                    childView = if (recyclerView != null && recyclerView.id == recyclerViewId) {
                        recyclerView.findViewHolderForAdapterPosition(position)!!.itemView
                    } else {
                        return false
                    }
                }
                return if (targetViewId == -1) {
                    view === childView
                } else {
                    val targetView =
                        childView!!.findViewById<View>(targetViewId)
                    view === targetView
                }
            }
        }
    }


    // It returns the ordinal number of recyclerView that is targetText appears.
    private fun getIndex(
        recyclerViewItemCount: Int,
        targetRecyclerViewId: Int,
        targetViewId: Int,
        targetText: String
    ): Int {
        for (idx in 0 until recyclerViewItemCount) {

            var strAttribute = ""
            Espresso.onView(
                RecyclerViewMatcher(targetRecyclerViewId)
                    .atPosition(idx)
            ).check { view, noViewFoundException ->
                strAttribute = view.findViewById<TextView>(targetViewId).text.toString()
            }

            if (targetText.compareTo(strAttribute) == 0) {
                return idx
            }
        }
        return 0
    }

}