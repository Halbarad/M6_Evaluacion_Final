package cl.unab.m6_ae2abp.ui.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import cl.unab.m6_ae2abp.databinding.ItemProductoBinding
import cl.unab.m6_ae2abp.modelo.Producto
import cl.unab.m6_ae2abp.ui.LeerProductosFragmentDirections
import cl.unab.m6_ae2abp.viewmodel.ProductoViewModel

class ProductoAdapter(private var productos: List<Producto>, private val viewModel: ProductoViewModel) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(val binding: ItemProductoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = ItemProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]

        holder.binding.apply {
            tvId.text = producto.id.toString()
            tvNombre.text = producto.nombre
            tvDescripcion.text = producto.descripcion
            tvPrecio.text = producto.precio.toString()
            tvCantidad.text = producto.cantidad.toString()

            btnEditar.setOnClickListener {
                val action = LeerProductosFragmentDirections
                    .actionLeerProductosFragmentToActualizarProductoFragment(producto)
                it.findNavController().navigate(action)
            }

            btnEliminar.setOnClickListener {
                producto.id?.let {
                    viewModel.eliminarProducto(it)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    fun updateProductos(productos: List<Producto>){
        this.productos = productos
        notifyDataSetChanged()
    }
}