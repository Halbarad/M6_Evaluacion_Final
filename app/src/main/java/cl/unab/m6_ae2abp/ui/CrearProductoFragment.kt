package cl.unab.m6_ae2abp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import cl.unab.m6_ae2abp.R
import cl.unab.m6_ae2abp.databinding.FragmentCrearProductoBinding
import cl.unab.m6_ae2abp.viewmodel.ProductoViewModel

class CrearProductoFragment : Fragment() {

    private var _binding: FragmentCrearProductoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrearProductoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCancelar.setOnClickListener {
            binding.tietNombre.text?.clear()
            binding.tietDescripcion.text?.clear()
            binding.tietPrecio.text?.clear()
            binding.tietCantidad.text?.clear()
            findNavController().navigate(R.id.action_crearProductoFragment_to_leerProductosFragment)
        }

        // --- Inicia el nuevo flujo de creación ---

        // Paso 2: Observa el LiveData que recibirá el siguiente ID.
        // Esto se ejecutará DESPUÉS de que la llamada a la API en el ViewModel termine.
        viewModel.siguienteId.observe(viewLifecycleOwner) { idRecibido ->
            // Nos aseguramos de que el ID no sea nulo
            if (idRecibido != null) {
                val nombre = binding.tietNombre.text.toString()
                val descripcion = binding.tietDescripcion.text.toString()
                val precio = binding.tietPrecio.text.toString().toIntOrNull()
                val cantidad = binding.tietCantidad.text.toString().toIntOrNull()

                if (nombre.isNotEmpty() && descripcion.isNotEmpty() && precio != null && cantidad != null) {
                    // Ahora que tenemos el ID, llamamos a crearProducto
                    viewModel.crearProducto(idRecibido, nombre, descripcion, precio, cantidad)
                } else {
                    Toast.makeText(requireContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Esto se ejecutaría si la API falla al devolver un ID
                Toast.makeText(requireContext(), "No se pudo obtener un ID para el producto", Toast.LENGTH_SHORT).show()
            }
        }

        // Paso 1: Cuando el usuario hace click, simplemente pedimos que se obtenga el ID.
        // La lógica de creación se moverá al observador de arriba.
        binding.btnCrear.setOnClickListener {
            viewModel.obtenerSiguienteId()
        }

        viewModel.creacionExitosa.observe(viewLifecycleOwner) { exitoso ->
            if (exitoso) {
                Toast.makeText(requireContext(), "Producto creado con éxito", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_crearProductoFragment_to_leerProductosFragment)
            } else {
                Toast.makeText(requireContext(), "Error al crear el producto", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}