package com.example.practicafinal.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.practicafinal.OnFragmentActionListener
import com.example.practicafinal.R

class MenuFragment : Fragment() {

    private var listener: OnFragmentActionListener? = null
    private val listaImagenesView = arrayOf(R.id.iv_bethesda, R.id.iv_mojang, R.id.iv_riot)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var imageView: ImageView

        for (i in listaImagenesView.indices) {
            imageView = view.findViewById(listaImagenesView[i])
            imageView.setOnClickListener {
                listener?.onClickImagenMenu(i)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentActionListener) listener = context
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
