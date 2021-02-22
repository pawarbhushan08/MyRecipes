package com.example.myrecipies.presentation

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myrecipies.R
import com.example.myrecipies.databinding.FragmentRecipeListBinding
import com.example.myrecipies.framework.RecipeListViewModel
import com.example.myrecipies.helper.RecipeListAdapter
import kotlinx.android.synthetic.main.fragment_recipe_list.*
import org.koin.android.viewmodel.ext.android.viewModel


class RecipeListFragment : Fragment(), RecipeListAction {

    private val recipeListAdapter = RecipeListAdapter(arrayListOf(), this)

    private val viewModel by viewModel<RecipeListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = DataBindingUtil.inflate<FragmentRecipeListBinding>(
            inflater,
            R.layout.fragment_recipe_list,
            container,
            false
        )
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipesListView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recipeListAdapter
        }

        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getRecipes()
    }

    private fun observeViewModel() {
        viewModel.recipes.observe(viewLifecycleOwner, {
            loadingView.visibility = View.GONE
            recipeListAdapter.updateRecipes(it)
        })
    }

    private fun goToRecipeDetails(id: Long = 0L) {
        val action: NavDirections = RecipeListFragmentDirections.actionGoToRecipe(id)
        Navigation.findNavController(recipesListView).navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.item_add, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_recipe -> goToRecipeDetails()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(id: Long) {
        goToRecipeDetails(id)
    }
}