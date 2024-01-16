package com.borjali.presentation.ui.worker.workerlist

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.borjali.domain.model.event.EventListIsEmptyResult
import com.borjali.domain.model.worker.WorkerDto
import com.borjali.domain.utils.EventOfCleanApp
import com.borjali.presentation.R
import com.borjali.presentation.databinding.ItemOfUserBinding
import com.borjali.presentation.extensions.setTin
import com.borjali.presentation.ui.worker.workerlist.ClickState.EditUserAvatarClicked
import com.borjali.presentation.ui.worker.workerlist.ClickState.ItemClicked
import com.borjali.presentation.ui.worker.workerlist.ClickState.ShowLogClicked
import com.bumptech.glide.Glide


/**
 * adapter class for worker ist
 */
class WorkerListAdapter(
    private val itemList: List<WorkerDto>,
    private var onClick: ((ClickState) -> Unit)
) : RecyclerView.Adapter<WorkerListAdapter.ItemViewHolder>() {
    private var popupMenu: PopupWindow? = null
    private var filteredItemList: List<WorkerDto> = itemList
    lateinit var bounceAnimation: Animation
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOfUserBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = filteredItemList[position]
        holder.bind(currentItem, position)
    }

    override fun getItemCount(): Int {
        return filteredItemList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        filteredItemList = if (query.isEmpty()) {
            itemList
        } else {
            itemList.filter { it.fullName.contains(query, ignoreCase = true) }
        }
        notifyDataSetChanged()
        EventOfCleanApp.send(EventListIsEmptyResult(filteredItemList.isEmpty()))
    }

    inner class ItemViewHolder(private val binding: ItemOfUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WorkerDto, position: Int) {
            binding.fullNameText.text = item.fullName
            binding.role.text = item.role.toLowerCase()
            Glide.with(binding.rootItem.context)
                .load(item.attachment?.cover)
                .circleCrop()
                .placeholder(R.drawable.ic_place_holder)
                .into(binding.userAvatar)
            binding.rootItem.setOnClickListener {
                onClick(ItemClicked(position))
            }
            binding.menu.setOnClickListener {
                val originalPos = IntArray(2)
                it.getLocationOnScreen(originalPos)
                // Timber.e("X Position is ${originalPos[0]} AND Y position is ${originalPos[1]}")
                popupMenu = showAlertFilter(position, binding)
                popupMenu?.showAsDropDown(
                    binding.menu,
                    -370,
                    if (originalPos[1] > 2400) -200 else -170,
                    Gravity.END
                )
            }
            if (item.activeWorkLog){
                bounceAnimation = AnimationUtils.loadAnimation(binding.icActiveWorkLog.context,R.anim.bounce)
                binding.icActiveWorkLog.startAnimation(bounceAnimation)
                binding.icActiveWorkLog.visibility =  View.VISIBLE
                if (item.activeRestTime)
                    binding.icActiveWorkLog.setTin(R.color.orange)
            }else{
                binding.icActiveWorkLog.visibility = View.INVISIBLE
            }


            binding.executePendingBindings()
        }
    }


    @SuppressLint("InflateParams")
    private fun showAlertFilter(
        position: Int,
        holder: ItemOfUserBinding
    ): PopupWindow {
        val inflater =
            holder.root.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.layout_popup_view, null)

        view.findViewById<TextView>(R.id.edit_user_avatar).apply {

            setOnClickListener {
                onClick(EditUserAvatarClicked(position))
                dismiss()
            }

        }

        view.findViewById<TextView>(R.id.show_log).apply {

            setOnClickListener {
                onClick(ShowLogClicked(position))
                dismiss()
            }


        }


        return PopupWindow(
            view,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )
    }

    private fun dismiss() {
        if (popupMenu?.isShowing == true) {
            popupMenu?.dismiss()
        }
    }
}
