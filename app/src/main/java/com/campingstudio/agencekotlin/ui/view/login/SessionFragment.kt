package com.campingstudio.agencekotlin.ui.view.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.campingstudio.agencekotlin.core.AuthUserHelper
import com.campingstudio.agencekotlin.databinding.FragmentSlideshowBinding
import com.campingstudio.agencekotlin.ext.toastLong
import com.campingstudio.agencekotlin.ext.toastShort

class SessionFragment() : Fragment( ) {
    private lateinit var authUserHelper: AuthUserHelper
    private lateinit var sessionViewModel: SessionViewModel
    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sessionViewModel =
            ViewModelProvider(this)[SessionViewModel::class.java]
            authUserHelper = this.context?.let { AuthUserHelper(it) }!!
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSlideshow
        sessionViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        return root
    }

    private fun onLoad() {
        binding.apply {
            if(authUserHelper.user==null) {
                toastShort("authUserHelper.user==null")
                logout()
            }
        }
    }

    private fun logout() {
        try {
            toastLong( "Regresa pronto ${authUserHelper.user!!.userName}.")
            authUserHelper.logout()
            val intent = Intent(this.context, LoginActivity::class.java)
            startActivity(intent)
            // finishAffinity()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}