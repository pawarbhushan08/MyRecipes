package com.example.myrecipies.presentation

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.data.Recipe
import com.example.myrecipies.R
import com.example.myrecipies.databinding.FragmentAddRecipeBinding
import com.example.myrecipies.framework.AddRecipeViewModel
import com.example.myrecipies.helper.PhotosAdapter
import kotlinx.android.synthetic.main.fragment_add_recipe.*
import org.koin.android.viewmodel.ext.android.viewModel

class AddRecipeFragment : Fragment(), PhotoActions {

    private val viewModel by viewModel<AddRecipeViewModel>()

    var photosAdapter = PhotosAdapter(isDeleteVisible = true, actions = this)

    private var recipeId = 0L

    private var currentRecipe = Recipe(title = "", description = "", photosUrlList = arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = DataBindingUtil.inflate<FragmentAddRecipeBinding>(
            inflater,
            R.layout.fragment_add_recipe,
            container,
            false
        )
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.item_done, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            recipeId = AddRecipeFragmentArgs.fromBundle(it).recipeId
        }
        recyclerView.adapter = photosAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        recipeId.let {
            viewModel.getRecipe(it)
        }

        onUploadImageClicked()

        observeViewModel()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_recipe -> {
                if ((title_text_recipe.text?.isNotEmpty() == true || text_description_recipe.text?.isNotEmpty() == true) && photosAdapter.photos.size != 0) {
                    currentRecipe.title = title_text_recipe.text.toString()
                    currentRecipe.description = text_description_recipe.text.toString()
                    currentRecipe.photosUrlList = photosAdapter.photos
                    viewModel.saveRecipe(currentRecipe)
                } else {
                    Toast.makeText(
                        context,
                        "Must fill all fields!!",
                        Toast.LENGTH_SHORT
                    ).show()
                    view?.let { Navigation.findNavController(it).popBackStack() }
                    hideKeyboard()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            val uri = data?.data
            if (uri != null) {
                with(photosAdapter) {
                    photos.add(uri.toString())
                    notifyDataSetChanged()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun onUploadImageClicked() {
        upload_image.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_PICK_CODE)
        }
    }

    private fun observeViewModel() {
        viewModel.saved.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(context, "Recipe added !!", Toast.LENGTH_SHORT).show()
                hideKeyboard()
                Navigation.findNavController(title_recipe).popBackStack()
            } else {
                Toast.makeText(
                    context,
                    "Something went wrong, please try again !!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        viewModel.currentRecipe.observe(viewLifecycleOwner, { recipe ->
            recipe?.let {
                currentRecipe = it
                title_text_recipe.setText(it.title, TextView.BufferType.EDITABLE)
                text_description_recipe.setText(it.description, TextView.BufferType.EDITABLE)
                photosAdapter.updatePhotos(it.photosUrlList)
            }

        })
    }

    override fun onDeletePhotoClicked(uri: String) {
        with(photosAdapter) {
            photos.remove(uri)
            notifyDataSetChanged()
        }

    }


    private fun hideKeyboard() {
        val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(title_recipe.windowToken, 0)
    }

    companion object {
        private const val IMAGE_PICK_CODE = 999
    }
}