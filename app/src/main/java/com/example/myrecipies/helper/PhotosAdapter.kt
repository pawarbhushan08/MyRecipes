package com.example.myrecipies.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipies.R
import com.example.myrecipies.presentation.PhotoActions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_photo.view.*

const val IMAGE_SIDE_PX = 300

class PhotosAdapter(
    val photos: ArrayList<String> = arrayListOf(),
    val isDeleteVisible: Boolean,
    val actions: PhotoActions?
) : RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {

    fun updatePhotos(newPhotos: List<String>?) {
        photos.clear()
        newPhotos?.let { photos.addAll(it) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        return PhotosViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_photo,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    inner class PhotosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val deleteButton = itemView.delete_image
        fun bind(photoUrl: String) {
            if (isDeleteVisible) {
                deleteButton.setOnClickListener { actions?.onDeletePhotoClicked(photoUrl) }
            } else {
                deleteButton.visibility = View.GONE
            }
            Picasso.get().load(photoUrl)
                .resize(IMAGE_SIDE_PX, IMAGE_SIDE_PX)
                .centerCrop()
                .into(itemView.imageView)
        }
    }
}