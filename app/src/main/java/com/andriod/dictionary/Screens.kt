package com.andriod.dictionary

import com.andriod.dictionary.wordlist.WordListFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun wordList() = FragmentScreen { WordListFragment() }
}