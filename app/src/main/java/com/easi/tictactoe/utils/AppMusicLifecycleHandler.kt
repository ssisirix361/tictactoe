package com.easi.tictactoe.utils

import AudioManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner

object AppMusicLifecycleHandler : DefaultLifecycleObserver {

    fun register() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStart(owner: LifecycleOwner) {
        AudioManager.playBackgroundMusic()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        AudioManager.resumeBackgroundMusic()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        AudioManager.pauseBackgroundMusic()
    }

    override fun onStop(owner: LifecycleOwner) {
        // App passe en arrière-plan
        AudioManager.pauseBackgroundMusic()
    }
}
