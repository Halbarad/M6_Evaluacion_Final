package cl.unab.m6_ae2abp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.util.TreeIterables
import androidx.test.espresso.PerformException
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditarProductoTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testEditarProducto() {
        val originalName = "ProductoEditar_" + System.currentTimeMillis()
        val editedName = "ProductoEditado_" + System.currentTimeMillis()

        // 1) Crear un producto base desde la pantalla de creación
        onView(withId(R.id.btnCrearProducto)).perform(click())

        onView(withId(R.id.tietNombre)).perform(replaceText(originalName), closeSoftKeyboard())
        onView(withId(R.id.tietDescripcion)).perform(replaceText("descripcion"), closeSoftKeyboard())
        onView(withId(R.id.tietPrecio)).perform(replaceText("5"), closeSoftKeyboard())
        onView(withId(R.id.tietCantidad)).perform(replaceText("2"), closeSoftKeyboard())
        onView(withId(R.id.btnCrear)).perform(click())

        // 2) Asegurar que estamos en el listado y esperar a que aparezca el item
        ensureRecyclerViewReady()
        waitForTextInHierarchy(originalName)
        onView(withText(originalName)).check(matches(isDisplayed()))

        // 3) Abrir el elemento para editar: usar el botón editar del item en el RecyclerView
        onView(isAssignableFrom(RecyclerView::class.java)).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText(originalName)),
                clickChildViewWithId(R.id.btnEditar)
            )
        )

        // Esperar a que se muestre la pantalla de edición (campo de nombre visible)
        onView(isRoot()).perform(waitForViewMatch(allOf(withId(R.id.tietNombre), isDisplayed()), 8000))
        // Forzar layout si la vista aun no tiene tamaño válido
        onView(withId(R.id.tietNombre)).perform(forceLayoutAndWait())

        // 4) Reemplazar el nombre y guardar (se asume mismos ids de input/botón)
        onView(withId(R.id.tietNombre)).perform(click(), replaceText(editedName), closeSoftKeyboard())
        // Intentar guardar con btnActualizar; si no existe, usar btnCrear
        try {
            onView(withId(R.id.btnActualizar)).perform(click())
        } catch (_: Exception) {
            onView(withId(R.id.btnCrear)).perform(click())
        }

        // 5) Volver/esperar listado y comprobar que el nombre fue actualizado
        ensureRecyclerViewReady()
        waitForTextInHierarchy(editedName)
        onView(withText(editedName)).check(matches(isDisplayed()))
    }
}

// Helpers
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

// Fuerza un layout pass sobre la vista objetivo y espera al hilo principal
private fun forceLayoutAndWait(): ViewAction = object : ViewAction {
    override fun getConstraints(): Matcher<View> = isDisplayed()
    override fun getDescription(): String = "force layout and wait for idle"
    override fun perform(uiController: UiController, view: View) {
        view.requestLayout()
        view.invalidate()
        uiController.loopMainThreadUntilIdle()
    }
}
