package cl.unab.m6_ae2abp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import cl.unab.m6_ae2abp.R
import cl.unab.m6_ae2abp.databinding.ItemProductoBinding
import cl.unab.m6_ae2abp.modelo.Producto
import cl.unab.m6_ae2abp.viewmodel.ProductoViewModel

class ProductoAdapter(private var productos: List<Producto>, private val viewModel: ProductoViewModel) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(private val binding: ItemProductoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(producto: Producto) {
            binding.tvId.text = producto.id.toString()
            binding.tvNombre.text = producto.nombre
            binding.tvDescripcion.text = producto.descripcion
            binding.tvPrecio.text = producto.precio.toString()
            binding.tvCantidad.text = producto.cantidad.toString()

            binding.btnEditar.setOnClickListener {
                it.findNavController().navigate(R.id.action_leerProductosFragment_to_actualizarProductoFragment)
            }

            binding.btnEliminar.setOnClickListener {
                viewModel.eliminarProducto(producto.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = ItemProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        holder.bind(productos[position])
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    fun updateProductos(productos: List<Producto>){
        this.productos = productos
        notifyDataSetChanged()
    }
}