package cl.unab.m6_ae2abp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import cl.unab.m6_ae2abp.databinding.FragmentCrearRoomBinding
import cl.unab.m6_ae2abp.modelo.ProductoRoom
import cl.unab.m6_ae2abp.viewmodel.ProductoRoomViewModel

class CrearRoomFragment : Fragment() {

    private var _binding: FragmentCrearRoomBinding? = null
    private val binding get() = _binding!!

    // Instancia del ViewModel
    private val productoViewModel: ProductoRoomViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrearRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCrear.setOnClickListener {
            insertarProducto()
        }

        binding.btnCancelar.setOnClickListener {
            findNavController().navigateUp() // Vuelve al fragment anterior
        }
    }

    private fun insertarProducto() {
        val nombre = binding.tietNombre.text.toString()
        val descripcion = binding.tietDescripcion.text.toString()
        val precioStr = binding.tietPrecio.text.toString()
        val cantidadStr = binding.tietCantidad.text.toString()

        // Validación simple de campos
        if (nombre.isNotEmpty() && descripcion.isNotEmpty() && precioStr.isNotEmpty() && cantidadStr.isNotEmpty()) {
            try {
                val precio = precioStr.toInt()
                val cantidad = cantidadStr.toInt()

                // Creamos el objeto ProductoRoom
                val producto = ProductoRoom(
                    nombre = nombre,
                    descripcion = descripcion,
                    precio = precio,
                    cantidad = cantidad
                )

                // Usamos el ViewModel para insertar el producto
                productoViewModel.insert(producto)

                // Mostramos un mensaje y volvemos a la lista
                Toast.makeText(context, "Producto creado exitosamente", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()

            } catch (e: NumberFormatException) {
                Toast.makeText(context, "Precio y Cantidad deben ser números", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}