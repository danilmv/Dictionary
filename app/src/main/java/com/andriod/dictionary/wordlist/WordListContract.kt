package com.andriod.dictionary.wordlist

import com.andriod.dictionary.entity.Word
import moxy.MvpPresenter
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

class WordListContract {
    enum class ViewState {
        IDLE, LOADING
    }

    interface View : MvpView {
        @AddToEndSingle
        fun setState(state: ViewState)

        @AddToEndSingle
        fun setData(words: List<Word>)

        @Skip
        fun showError(throwable: Throwable)
    }

    abstract class Presenter : MvpPresenter<View>() {
        abstract fun onItemCLick(word: Word)
        abstract fun onSearch(query: String)
    }
}