package cl.unab.m6_ae2abp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.util.TreeIterables
import androidx.test.espresso.PerformException
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CrearProductoTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testCrearProducto() {
        val uniqueName = "Manzana_" + System.currentTimeMillis()

        // 1. Navegar a la pantalla de creación
        onView(withId(R.id.btnCrearProducto)).perform(click())

        // 2. Rellenar el formulario (replaceText para evitar problemas con IME y caracteres acentuados)
        onView(withId(R.id.tietNombre)).perform(replaceText(uniqueName), closeSoftKeyboard())
        onView(withId(R.id.tietDescripcion)).perform(replaceText("De Exportación"), closeSoftKeyboard())
        onView(withId(R.id.tietPrecio)).perform(replaceText("10"), closeSoftKeyboard())
        onView(withId(R.id.tietCantidad)).perform(replaceText("1"), closeSoftKeyboard())

        // 3. Hacer clic en el botón "Crear"
        onView(withId(R.id.btnCrear)).perform(click())

        // 4. Asegurar retorno/visualización del RecyclerView (si sigue en la pantalla de creación hacer back)
        ensureRecyclerViewReady()

        // 5. Esperar a que el item aparezca en el listado
        waitForTextInHierarchy(uniqueName)

        // 6. (Opcional) interactuar con el item para confirmar que está en el RecyclerView
        onView(isAssignableFrom(RecyclerView::class.java)).perform(
            RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(hasDescendant(withText(uniqueName)))
        )
    }
}

private fun ensureRecyclerViewReady() {
    try {
        onView(isRoot()).perform(waitForViewMatch(isAssignableFrom(RecyclerView::class.java), 2000))
    } catch (_: Exception) {
        androidx.test.espresso.Espresso.pressBack()
        onView(isRoot()).perform(waitForViewMatch(isAssignableFrom(RecyclerView::class.java), 8000))
    }
}

private fun waitForTextInHierarchy(text: String) {
    val matcher = withText(text)
    onView(isRoot()).perform(waitForCondition {
        TreeIterables.breadthFirstViewTraversal(it).any { v -> matcher.matches(v) }
    })
}

private fun waitForCondition(condition: (root: View) -> Boolean): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> = isRoot()
        override fun getDescription(): String = "waiting for condition"
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

private fun waitForViewMatch(matcher: Matcher<View>, timeout: Long): ViewAction {
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