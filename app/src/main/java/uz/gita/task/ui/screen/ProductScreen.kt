package uz.gita.task.ui.screen

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.task.R
import uz.gita.task.data.model.response.ProductResponse
import uz.gita.task.databinding.ScreenProductBinding
import uz.gita.task.viewModel.ProductViewModel
import uz.gita.task.viewModel.impl.ProductViewModelImpl

@AndroidEntryPoint
class ProductScreen : Fragment(R.layout.screen_product) {
    private val binding by viewBinding(ScreenProductBinding::bind)
    private val viewModel: ProductViewModel by viewModels<ProductViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.errorLiveData.observe(this) {
            Log.d("Error", "$it")
        }
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.messageLiveData.observe(this, messageObserver)
        viewModel.backLiveData.observe(this, backObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.joinProductLiveData.observe(viewLifecycleOwner, joinProductObserver)
        val args: ProductScreenArgs by navArgs()
        viewModel.setProduct(args.id)
        binding.imgBack.setOnClickListener {
            viewModel.onBack()
        }
    }

    private val joinProductObserver = Observer<ProductResponse> {
        binding.apply {
            Glide.with(imgvProduct)
                .load(it.photoUrl)
                .placeholder(R.drawable.phone)
                .into(imgvProduct)
            tvNameProduct.text = it.name
            tvPriceProduct.text = (it.price.toInt()).toString()
            tvDescription.text = it.description
        }
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

    private val backObserver = Observer<Unit> {
        findNavController().navigate(ProductScreenDirections.actionProductScreenToMainScreen())
    }
}