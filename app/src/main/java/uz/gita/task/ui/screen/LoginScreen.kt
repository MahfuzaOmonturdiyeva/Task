package uz.gita.task.ui.screen


import android.app.AlertDialog
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.task.R
import uz.gita.task.data.model.request.LoginRequest
import uz.gita.task.databinding.ScreenLoginBinding
import uz.gita.task.viewModel.LoginViewModel
import uz.gita.task.viewModel.impl.LoginViewModelImpl


@AndroidEntryPoint
class LoginScreen : Fragment(R.layout.screen_login), View.OnKeyListener {
    private val binding by viewBinding(ScreenLoginBinding::bind)
    private val viewModel: LoginViewModel by viewModels<LoginViewModelImpl>()
    private var isVisiblePassWord=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.errorLiveData.observe(this) {
            Log.d("Error", "$it")
        }
        viewModel.progressLiveData.observe(this, progressObserver)
        viewModel.messageLiveData.observe(this, messageObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.openMainLiveData.observe(viewLifecycleOwner, openMainObserver)
        viewModel.isVisiblePasswordLiveData.observe(viewLifecycleOwner, isVisiblePasswordObserver)
        binding.btnLogin.setOnClickListener {
            onClickLogin()
        }

        binding.btnLogin.setOnClickListener {
            onClickLogin()
        }

        binding.imgPasswordOnOrOff.setOnClickListener {
            viewModel.onVisiblePassword(isVisiblePassWord)
            isVisiblePassWord=!isVisiblePassWord
        }

        binding.etLogin.doOnTextChanged { text, start, before, count ->
            binding.tvErrorLogin.text = ""
        }

        binding.etPassword.doOnTextChanged { text, start, before, count ->
            binding.tvErrorPassword.text = ""
        }

        binding.etPassword.setOnKeyListener(this)
    }

    private val isVisiblePasswordObserver= Observer<Boolean> {
        if(it){
            binding.etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.imgPasswordOnOrOff.setImageResource(R.drawable.ic_visibility)
        } else{
            binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.imgPasswordOnOrOff.setImageResource(R.drawable.ic_visibility_off)
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
        builder.show()
    }

    private val openMainObserver = Observer<Unit> {
        findNavController().navigate(LoginScreenDirections.actionLoginScreenToMainScreen())
    }

    private val progressObserver = Observer<Boolean> {
        if (it) {
            binding.progress.root.visibility = View.VISIBLE
        } else {
            binding.progress.root.visibility = View.GONE
        }
    }

    private fun onClickLogin() {
        val login = binding.etLogin.text.toString()
        val password = binding.etPassword.text.toString()
        if (login.length >= 3 && password.length >= 6) {
            val data = LoginRequest(login, password)
            viewModel.onLogin(data)
        } else {
            if (login.isEmpty()) {
                binding.tvErrorLogin.text = getString(R.string.loginIsEmpty)
            }else if (login.length < 3 ) {
                binding.tvErrorLogin.text = getString(R.string.loginError)
            }
            if (password.isEmpty()) {
                binding.tvErrorPassword.text = getString(R.string.passwordIsEmpty)
            } else if (password.length < 6) {
                binding.tvErrorPassword.text = getString(R.string.passwordError)
            }
        }
    }

    override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
        p2?.let {
            if (it.action == KeyEvent.ACTION_DOWN && p1 == KeyEvent.KEYCODE_ENTER) {
                onClickLogin()
                return true
            }
        }
        return false
    }
}