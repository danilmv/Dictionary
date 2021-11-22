package com.andriod.dictionary.wordlist

import com.andriod.dictionary.entity.Word
import com.andriod.dictionary.model.DataProvider
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class WordListPresenter(
    private val dataProvider: DataProvider,
    private val router: Router
) : WordListContract.Presenter() {

    private var disposable: Disposable? = null
        set(value) {
            field?.takeIf { !it.isDisposed }?.dispose()
            field = value
        }

    override fun onItemCLick(word: Word) {
//        router.navigateTo(Screens.WordDetails(word))
    }

    override fun onSearch(query: String) {
        viewState.setState(WordListContract.ViewState.LOADING)
        disposable = dataProvider.search(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ words ->
                viewState.setState(WordListContract.ViewState.IDLE)
                viewState.setData(words)
            }, { throwable ->
                viewState.setState(WordListContract.ViewState.IDLE)
                viewState.showError(throwable)
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable = null
    }
}