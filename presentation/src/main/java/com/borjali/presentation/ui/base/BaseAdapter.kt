package com.borjali.presentation.ui.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator

abstract class BaseAdapter<T, B : ViewDataBinding>(
    private val layoutInflate: Inflate<B>,
    private val diffUtil: DiffCallback<T, B> = DiffCallback()
) :
//    RecyclerView.Adapter<BaseAdapter.ViewHolder<B>>() {
        ListAdapter<T, BaseAdapter.ViewHolder<B>>(diffUtil) {

        init {
            this.also { diffUtil.adapter = it }
        }

        open fun areItemsTheSame(oldItem: T, newItem: T): Boolean = false
        open fun areContentTheSame(oldItem: T, newItem: T): Boolean = false

        @Suppress("WRONG_TYPE_PARAMETER_NULLABILITY_FOR_JAVA_OVERRIDE")
        class DiffCallback<T, B : ViewDataBinding> : DiffUtil.ItemCallback<T>() {
            lateinit var adapter: BaseAdapter<T, B>
            override fun areItemsTheSame(oldItem: T, newItem: T) = adapter.areItemsTheSame(oldItem, newItem)
            override fun areContentsTheSame(oldItem: T, newItem: T) = adapter.areContentTheSame(oldItem, newItem)
        }

        private lateinit var _binding: B
        val binding get() = _binding

        open class ViewHolder<V : ViewDataBinding>(val binding: V) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<B> {

            val inflater = parent.context.getSystemService(
                Context
                    .LAYOUT_INFLATER_SERVICE
            ) as LayoutInflater

            return ViewHolder(
                layoutInflate.invoke(inflater, parent, false).apply {
                    _binding = this
                }
            )
        }

        override fun getItemCount() = currentList.size

        override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {

            super.onAttachedToRecyclerView(recyclerView)
            (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }
