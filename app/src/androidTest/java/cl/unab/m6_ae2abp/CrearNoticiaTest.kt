package cl.unab.m6_ae2abp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CrearNoticiaTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testCrearNoticia() {
        // 1. Navegar a la pantalla de creación
        onView(withId(R.id.btnCrearNoticia)).perform(click())

        // 2. Rellenar el formulario
        onView(withId(R.id.tidtId)).perform(typeText("12345"), closeSoftKeyboard())
        onView(withId(R.id.tietTitulo)).perform(typeText("Título de prueba"), closeSoftKeyboard())
        onView(withId(R.id.tietDescripcion)).perform(typeText("Descripción de prueba"), closeSoftKeyboard())
        onView(withId(R.id.tietFuenteURL)).perform(typeText("www.prueba.com"), closeSoftKeyboard())

        // 3. Hacer clic en el botón "Crear"
        onView(withId(R.id.btnCrear)).perform(click())

        // 4. (Opcional) Verificar que se ha vuelto a la pantalla principal
        //    Podemos comprobar si el RecyclerView de noticias está visible.
        onView(withId(R.id.rvNoticias)).check(matches(isDisplayed()))
    }
}