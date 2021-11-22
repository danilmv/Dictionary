package com.andriod.dictionary.wordlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.andriod.dictionary.DictionaryApp
import com.andriod.dictionary.databinding.FragmentWordListBinding
import com.andriod.dictionary.entity.Word
import com.andriod.dictionary.model.DataProvider
import com.github.terrakok.cicerone.Router
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class WordListFragment : MvpAppCompatFragment(), WordListContract.View {
    private var _binding: FragmentWordListBinding? = null
    private val binding: FragmentWordListBinding get() = _binding!!

    init {
        DictionaryApp.instance.appComponent.inject(this)
    }

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var dataProvider: DataProvider

    private val adapter by lazy { WordListAdapter { user -> presenter.onItemCLick(user) } }
    private val presenter by moxyPresenter {
        WordListPresenter(
            dataProvider,
            router
        )
    }

    private val app by lazy { requireActivity().application as DictionaryApp }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(view.context)
            recyclerView.adapter = adapter

            searchButton.setOnClickListener {
                presenter.onSearch(queryEditText.text.toString())
                app.hideKeyboard(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setState(state: WordListContract.ViewState) {
        when (state) {
            WordListContract.ViewState.IDLE -> {
                binding.recyclerView.isVisible = true
                binding.progressBar.isVisible = false
            }
            WordListContract.ViewState.LOADING -> {
                binding.recyclerView.isVisible = false
                binding.progressBar.isVisible = true
            }
        }
    }

    override fun setData(words: List<Word>) {
        adapter.setData(words)
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(
            requireContext(),
            "We have an error: ${throwable.message}",
            Toast.LENGTH_LONG
        ).show()
    }
}