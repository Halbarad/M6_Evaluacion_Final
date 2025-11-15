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
import cl.unab.m6_ae2abp.R
import cl.unab.m6_ae2abp.databinding.FragmentActualizarProductoBinding
import cl.unab.m6_ae2abp.modelo.Producto
import cl.unab.m6_ae2abp.viewmodel.ProductoViewModel

class ActualizarProductoFragment : Fragment() {

    private var _binding: FragmentActualizarProductoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductoViewModel by viewModels()
    private val args: ActualizarProductoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActualizarProductoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mostrarDatosProducto()
        setupListeners()
        setupObservers()
    }

    private fun mostrarDatosProducto() {
        val producto = args.producto

        binding.tietNombre.setText(producto.nombre)
        binding.tietDescripcion.setText(producto.descripcion)
        binding.tietPrecio.setText(producto.precio.toString())
        binding.tietCantidad.setText(producto.cantidad.toString())
    }

    private fun setupListeners() {
        binding.btnActualizar.setOnClickListener {
            val id = args.producto.id
            val nombre = binding.tietNombre.text.toString()
            val descripcion = binding.tietDescripcion.text.toString()
            val precio = binding.tietPrecio.text.toString().toIntOrNull()
            val cantidad = binding.tietCantidad.text.toString().toIntOrNull()

            if (nombre.isNotEmpty() && descripcion.isNotEmpty() && precio != null && cantidad != null) {
                val productoActualizado = Producto(id, nombre, descripcion, precio, cantidad)
                viewModel.actualizarProducto(id, productoActualizado)
            } else {
                Toast.makeText(requireContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCancelar.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupObservers() {
        viewModel.actualizacionExitosa.observe(viewLifecycleOwner) { exitoso ->
            if (exitoso) {
                Toast.makeText(requireContext(), "Producto actualizado con éxito", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_actualizarProductoFragment_to_leerProductosFragment)
            } else {
                Toast.makeText(requireContext(), "Error al actualizar el producto", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
