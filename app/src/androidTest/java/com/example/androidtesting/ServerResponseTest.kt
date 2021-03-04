package com.example.androidtesting

import RepoModel
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.BufferedReader
import kotlin.random.Random.Default.nextInt


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ServerResponseTest {

    @get:Rule
    var rule = ActivityScenarioRule(MainActivity::class.java)
    lateinit var jsonString:String
    private var testModel = listOf<RepoModel>()

    @Before
    fun setUp(){
        rule.scenario.onActivity {
            val file = it.applicationContext.assets.open("server_data.json")
            val br:BufferedReader = file.bufferedReader()
            jsonString = br.readText()
        }
        testModel = Gson().fromJson(jsonString, Array<RepoModel>::class.java).asList()
    }

    @Test
    fun responseListTest_fixedPosition() {

        // GIVEN

        // WHEN

        // THEN
        // check fixed positions
        onView(allOf(withId(R.id.recyclerView), withText(testModel[0].name)))
        onView(allOf(withId(R.id.recyclerView), withText(testModel[testModel.size - 1].name)))

    }
    @Test
    fun responseListTest_randomPosition() {

        // GIVEN

        // WHEN

        // THEN

        // check random positions
        onView(allOf(withId(R.id.recyclerView), withText(testModel[nextInt(testModel.size)].name)))
        onView(allOf(withId(R.id.recyclerView), withText(testModel[nextInt(testModel.size)].name)))
        onView(allOf(withId(R.id.recyclerView), withText(testModel[nextInt(testModel.size)].name)))


    }
}
