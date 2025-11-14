package cl.unab.m6_ae2abp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.util.TreeIterables
import androidx.test.espresso.PerformException
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EliminarProductoTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testEliminarProducto() {
        val uniqueName = "ProductoEliminar_" + System.currentTimeMillis()

        // 1. Crear un producto base
        onView(withId(R.id.btnCrearProducto)).perform(click())

        onView(withId(R.id.tietNombre)).perform(typeText(uniqueName), closeSoftKeyboard())
        onView(withId(R.id.tietDescripcion)).perform(typeText("descripcion"), closeSoftKeyboard())
        onView(withId(R.id.tietPrecio)).perform(typeText("3"), closeSoftKeyboard())
        onView(withId(R.id.tietCantidad)).perform(typeText("1"), closeSoftKeyboard())
        onView(withId(R.id.btnCrear)).perform(click())

        // 2. Esperar a que se muestre el listado (RecyclerView)
        ensureRecyclerViewReady()

        // 3. Esperar activamente a que el item aparezca
        waitForTextInRecyclerView(uniqueName)

        // 4. Eliminar el item (actionOnItem)
        onView(isAssignableFrom(RecyclerView::class.java)).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText(uniqueName)),
                clickChildViewWithId(R.id.btnEliminar)
            )
        )

        // 5. Esperar activamente a que el item desaparezca
        waitForTextGone(uniqueName)

        // 6. Verificar desaparición final
        onView(withText(uniqueName)).check(doesNotExist())
    }
}

// Helper: garantizar que el RecyclerView está listo (volver atrás si necesario)
private fun ensureRecyclerViewReady() {
    try {
        onView(isRoot()).perform(waitForView(isAssignableFrom(RecyclerView::class.java), 2000))
    } catch (_: Exception) {
        androidx.test.espresso.Espresso.pressBack()
        onView(isRoot()).perform(waitForView(isAssignableFrom(RecyclerView::class.java), 8000))
    }
}

// Espera a que un texto aparezca en algún descendiente del RecyclerView
private fun waitForTextInRecyclerView(text: String) {
    val matcher = withText(text)
    onView(isRoot()).perform(waitForCondition {
        TreeIterables.breadthFirstViewTraversal(it).any { v -> matcher.matches(v) }
    })
}

// Espera a que un texto desaparezca de la jerarquía
private fun waitForTextGone(text: String) {
    val matcher = withText(text)
    onView(isRoot()).perform(waitForCondition {
        TreeIterables.breadthFirstViewTraversal(it).none { v -> matcher.matches(v) }
    })
}

// Generic condition wait
private fun waitForCondition(condition: (root: View) -> Boolean): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> = isRoot()
        override fun getDescription(): String = "waiting for custom condition"
        override fun perform(uiController: UiController, view: View) {
            val timeout = 8000L
            val end = System.currentTimeMillis() + timeout
            while (System.currentTimeMillis() < end) {
                if (condition(view)) return
                uiController.loopMainThreadForAtLeast(100)
            }
            throw PerformException.Builder()
                .withActionDescription(description)
                .withViewDescription(view.toString())
                .withCause(AssertionError("Condition not met within ${timeout}ms"))
                .build()
        }
    }
}

// Click en vista hija dentro de item
fun clickChildViewWithId(id: Int): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> = isDisplayed()
        override fun getDescription(): String = "Click child view with id $id"
        override fun perform(uiController: UiController?, view: View) {
            view.findViewById<View>(id)?.performClick()
            uiController?.loopMainThreadUntilIdle()
        }
    }
}

// Espera una vista según matcher
fun waitForView(matcher: Matcher<View>, timeout: Long): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> = isRoot()
        override fun getDescription(): String = "wait for view $matcher"
        override fun perform(uiController: UiController, view: View) {
            val end = System.currentTimeMillis() + timeout
            while (System.currentTimeMillis() < end) {
                if (TreeIterables.breadthFirstViewTraversal(view).any { matcher.matches(it) }) return
                uiController.loopMainThreadForAtLeast(50)
            }
            throw PerformException.Builder()
                .withActionDescription(description)
                .withViewDescription(view.toString())
                .withCause(AssertionError("View not found within ${timeout}ms"))
                .build()
        }
    }
}
