package cl.unab.m6_ae2abp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cl.unab.m6_ae2abp.databinding.FragmentActualizarRoomBinding
import cl.unab.m6_ae2abp.modelo.ProductoRoom
import cl.unab.m6_ae2abp.viewmodel.ProductoRoomViewModel

class ActualizarRoomFragment : Fragment() {

    private var _binding: FragmentActualizarRoomBinding? = null
    private val binding get() = _binding!!

    // Instancia del ViewModel
    private val productoViewModel: ProductoRoomViewModel by viewModels()

    // Argumentos de navegación (el producto que se va a editar)
    private val args: ActualizarRoomFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActualizarRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Rellenar los campos con los datos del producto recibido
        binding.tietNombre.setText(args.producto.nombre)
        binding.tietDescripcion.setText(args.producto.descripcion)
        binding.tietPrecio.setText(args.producto.precio.toString())
        binding.tietCantidad.setText(args.producto.cantidad.toString())

        binding.btnActualizar.setOnClickListener {
            actualizarProducto()
        }

        binding.btnCancelar.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun actualizarProducto() {
        val nombre = binding.tietNombre.text.toString()
        val descripcion = binding.tietDescripcion.text.toString()
        val precioStr = binding.tietPrecio.text.toString()
        val cantidadStr = binding.tietCantidad.text.toString()

        if (nombre.isNotEmpty() && descripcion.isNotEmpty() && precioStr.isNotEmpty() && cantidadStr.isNotEmpty()) {
            try {
                val precio = precioStr.toInt()
                val cantidad = cantidadStr.toInt()

                // Creamos el objeto ProductoRoom actualizado
                val productoActualizado = ProductoRoom(
                    id = args.producto.id, // IMPORTANTE: Mantenemos el ID original
                    nombre = nombre,
                    descripcion = descripcion,
                    precio = precio,
                    cantidad = cantidad
                )

                // Usamos el ViewModel para actualizar el producto
                productoViewModel.update(productoActualizado)

                Toast.makeText(context, "Producto actualizado correctamente", Toast.LENGTH_SHORT).show()
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