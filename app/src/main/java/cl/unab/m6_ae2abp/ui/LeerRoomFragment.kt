package cl.unab.m6_ae2abp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import cl.unab.m6_ae2abp.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cl.unab.m6_ae2abp.databinding.FragmentLeerRoomBinding
import cl.unab.m6_ae2abp.modelo.ProductoRoom
import cl.unab.m6_ae2abp.ui.adaptador.ProductoRoomAdapter
import cl.unab.m6_ae2abp.viewmodel.ProductoRoomViewModel

class LeerRoomFragment : Fragment() {

    private var _binding: FragmentLeerRoomBinding? = null
    private val binding get() = _binding!!

    private val productoViewModel: ProductoRoomViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLeerRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Instanciar el adapter con los listeners
        val adapter = ProductoRoomAdapter(
            onItemClicked = { producto ->
                // Navegar a la pantalla de actualización con el producto
                val action =
                    LeerRoomFragmentDirections.actionLeerRoomFragmentToActualizarRoomFragment(
                        producto
                    )
                findNavController().navigate(action)
            },
            onDeleteClicked = { producto ->
                // Mostrar diálogo de confirmación para eliminar
                eliminarProductoDialog(producto)
            }
        )

        binding.rvProductos.adapter = adapter
        binding.rvProductos.layoutManager = LinearLayoutManager(context)

        productoViewModel.listadoProductos.observe(viewLifecycleOwner, Observer { productos ->
            productos?.let {
                adapter.submitList(it)
            }
        })

        binding.btnCrearProducto.setOnClickListener {
            findNavController().navigate(R.id.action_leerRoomFragment_to_crearRoomFragment)
        }

        binding.btnApi.setOnClickListener {
            findNavController().navigate(R.id.action_leerRoomFragment_to_leerProductosFragment)
        }
    }

    // 2. Nuevo método para el diálogo de confirmación
    private fun eliminarProductoDialog(producto: ProductoRoom) {
        AlertDialog.Builder(requireContext())
            .setTitle("Eliminar Producto")
            .setMessage("¿Estás seguro de que quieres eliminar '${producto.nombre}'?")
            .setPositiveButton("Eliminar") { _, _ ->
                // Si el usuario confirma, llamar al ViewModel para eliminar
                productoViewModel.delete(producto)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
