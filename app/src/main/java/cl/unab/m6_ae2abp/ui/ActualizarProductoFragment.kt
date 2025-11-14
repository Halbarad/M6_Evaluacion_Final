package cl.unab.m6_ae2abp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import cl.unab.m6_ae2abp.R
import cl.unab.m6_ae2abp.databinding.FragmentActualizarProductoBinding
import cl.unab.m6_ae2abp.modelo.Producto
import cl.unab.m6_ae2abp.viewmodel.ProductoViewModel

class ActualizarProductoFragment : Fragment() {

    private var _binding: FragmentActualizarProductoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActualizarProductoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        binding.btnBuscar.setOnClickListener {
            val id = binding.tidtBuscarId.text.toString().toIntOrNull()
            if (id != null) {
                viewModel.buscarProductoPorId(id)
            } else {
                Toast.makeText(requireContext(), "Por favor, ingrese un ID válido", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnActualizar.setOnClickListener {
            val id = binding.tidtId.text.toString().toIntOrNull()
            val nombre = binding.tietNombre.text.toString()
            val descripcion = binding.tietDescripcion.text.toString()
            val precio = binding.tietPrecio.text.toString().toIntOrNull()
            val cantidad = binding.tietCantidad.text.toString().toIntOrNull()

            if (id != null && nombre.isNotEmpty() && descripcion.isNotEmpty() && precio != null && cantidad != null) {
                val producto = Producto(id, nombre, descripcion, precio, cantidad)
                viewModel.actualizarProducto(id, producto)
            } else {
                Toast.makeText(requireContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCancelar.setOnClickListener {
            limpiarFormulario()
            binding.cvFormulario.isVisible = false
            findNavController().navigate(R.id.action_actualizarProductoFragment_to_leerProductosFragment)
        }
    }

    private fun setupObservers() {
        viewModel.productoEncontrada.observe(viewLifecycleOwner) { producto ->
            if (producto != null) {
                binding.cvFormulario.isVisible = true
                binding.tidtId.setText(producto.id.toString())
                binding.tietNombre.setText(producto.nombre)
                binding.tietDescripcion.setText(producto.descripcion)
                binding.tietPrecio.setText(producto.precio.toString())
                binding.tietCantidad.setText(producto.cantidad.toString())
            } else {
                binding.cvFormulario.isVisible = false
                Toast.makeText(requireContext(), "Producto no encontrado", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.actualizacionExitosa.observe(viewLifecycleOwner) { exitoso ->
            if (exitoso) {
                Toast.makeText(requireContext(), "Producto actualizado con éxito", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_actualizarProductoFragment_to_leerProductosFragment)
            } else {
                Toast.makeText(requireContext(), "Error al actualizar el producto", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun limpiarFormulario() {
        binding.tidtBuscarId.text?.clear()
        binding.tidtId.text?.clear()
        binding.tietNombre.text?.clear()
        binding.tietDescripcion.text?.clear()
        binding.tietPrecio.text?.clear()
        binding.tietCantidad.text?.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}