package cl.unab.m6_ae2abp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cl.unab.m6_ae2abp.R
import cl.unab.m6_ae2abp.databinding.FragmentLeerProductosBinding
import cl.unab.m6_ae2abp.ui.adaptador.ProductoAdapter
import cl.unab.m6_ae2abp.viewmodel.ProductoViewModel

class LeerProductosFragment : Fragment() {

    private var _binding: FragmentLeerProductosBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductoViewModel by viewModels()
    private lateinit var productoAdapter: ProductoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLeerProductosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productoAdapter = ProductoAdapter(emptyList(), viewModel)
        binding.rvProductos.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProductos.adapter = productoAdapter

        viewModel.productos.observe(viewLifecycleOwner) { producto ->
            productoAdapter.updateProductos(producto)
        }

        viewModel.eliminacionExitosa.observe(viewLifecycleOwner) { exitoso ->
            if (exitoso) {
                Toast.makeText(requireContext(), "Producto eliminado con éxito", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCrearProducto.setOnClickListener {
            findNavController().navigate(R.id.action_leerProductosFragment_to_crearProductoFragment)
        }

        binding.btnRoom.setOnClickListener {
            findNavController().navigate(R.id.action_leerProductosFragment_to_leerRoomFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
