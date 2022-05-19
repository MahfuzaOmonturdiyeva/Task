package uz.gita.task.ui.screen

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.task.R
import uz.gita.task.data.model.response.ProductResponse
import uz.gita.task.databinding.ScreenMainBinding
import uz.gita.task.ui.adapter.ProductAdapter
import uz.gita.task.viewModel.MainViewModel
import uz.gita.task.viewModel.impl.MainViewModelImpl

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main) {
    private val binding by viewBinding(ScreenMainBinding::bind)
    private val viewModel: MainViewModel by viewModels<MainViewModelImpl>()
    private val adapter by lazy { ProductAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.errorLiveData.observe(this) {
            Log.d("Error", "$it")
        }
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.messageLiveData.observe(this, messageObserver)
        viewModel.openProductLiveData.observe(this, openProductObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.joinProductsLiveData.observe(viewLifecycleOwner, joinProductsObserver)

        adapter.setOnclickItemListener {
            viewModel.onClickItem(it)
        }
        binding.rvContainerProducts.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvContainerProducts.adapter = adapter
    }

    private val joinProductsObserver = Observer<List<ProductResponse>> {
        adapter.submitList(it)
    }
    private val messageObserver = Observer<String> {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Xatolik!")
            .setMessage("$it")
            .setPositiveButton(
                "ОК"
            ) { dialog, id ->
                dialog.cancel()
            }
        builder.create()
    }

    private val progressObserver = Observer<Boolean> {
        if (it) {
            binding.progress.root.visibility = View.VISIBLE
        } else {
            binding.progress.root.visibility = View.GONE
        }
    }

    private val openProductObserver = Observer<Int> {
        findNavController().navigate(MainScreenDirections.actionMainScreenToProductScreen(it))
    }
}