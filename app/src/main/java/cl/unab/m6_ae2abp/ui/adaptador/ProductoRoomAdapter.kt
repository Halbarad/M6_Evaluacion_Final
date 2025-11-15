package cl.unab.m6_ae2abp.ui.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cl.unab.m6_ae2abp.databinding.ItemProductoBinding
import cl.unab.m6_ae2abp.modelo.ProductoRoom

class ProductoRoomAdapter(
    private val onItemClicked: (ProductoRoom) -> Unit,
    private val onDeleteClicked: (ProductoRoom) -> Unit
) : ListAdapter<ProductoRoom, ProductoRoomAdapter.ProductoViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = ItemProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, onItemClicked, onDeleteClicked)
    }

    class ProductoViewHolder(private val binding: ItemProductoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            producto: ProductoRoom,
            onItemClicked: (ProductoRoom) -> Unit,
            onDeleteClicked: (ProductoRoom) -> Unit
        ) {
            binding.tvId.text = producto.id.toString()
            binding.tvNombre.text = producto.nombre
            binding.tvDescripcion.text = producto.descripcion
            binding.tvPrecio.text = producto.precio.toString()
            binding.tvCantidad.text = producto.cantidad.toString()

            binding.btnEditar.setOnClickListener {
                onItemClicked(producto)
            }
            binding.btnEliminar.setOnClickListener {
                onDeleteClicked(producto)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ProductoRoom>() {
            override fun areItemsTheSame(oldItem: ProductoRoom, newItem: ProductoRoom): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ProductoRoom, newItem: ProductoRoom): Boolean {
                return oldItem == newItem
            }
        }
    }
}