package uz.gita.task.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.lottie.LottieAnimationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.task.R
import uz.gita.task.databinding.ScreenSplashBinding
import uz.gita.task.viewModel.SplashViewModel
import uz.gita.task.viewModel.impl.SplashViewModelImpl

@AndroidEntryPoint
class SplashScreen : Fragment(R.layout.screen_splash) {
    private val binding by viewBinding(ScreenSplashBinding::bind)
    private val viewModel: SplashViewModel by viewModels<SplashViewModelImpl>()
    private lateinit var lottie: LottieAnimationView

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lottie = binding.lottie
        viewModel.openMainLiveData.observe(viewLifecycleOwner, openMainObserver)
        viewModel.openLoginLiveData.observe(viewLifecycleOwner, openLoginObserver)
        lottie.animate().duration = 800
        lifecycleScope.launch {
            delay(800)
            viewModel.init()
        }
    }

    private val openMainObserver = Observer<Unit> {
        lottie.cancelAnimation()
        findNavController().navigate(SplashScreenDirections.actionSplashScreenToMainScreen())
    }
    private val openLoginObserver = Observer<Unit> {
        lottie.cancelAnimation()
        findNavController().navigate(SplashScreenDirections.actionSplashScreenToLoginScreen())
    }
}