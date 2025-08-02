import android.content.Context
import android.media.MediaPlayer
import com.easi.tictactoe.R

enum class SoundType(val resId: Int) {
    CLICK(R.raw.click_sound),
    VICTORY(R.raw.victory_sound),
    DEFEAT(R.raw.defeat_sound)
}

object AudioManager {
    private var backgroundPlayer: MediaPlayer? = null

    fun playSound(context: Context, sound: SoundType) {
        val mp = MediaPlayer.create(context, sound.resId)
        mp.setOnCompletionListener { it.release() }
        mp.start()
    }

    fun initBackgroundMusic(context: Context) {
        if (backgroundPlayer == null) {
            backgroundPlayer = MediaPlayer.create(context, R.raw.arcade_music)
            backgroundPlayer?.isLooping = true
            backgroundPlayer?.setVolume(0.3f, 0.3f)
        }
    }

    fun playBackgroundMusic() = backgroundPlayer?.start()

    fun pauseBackgroundMusic() = backgroundPlayer?.takeIf { it.isPlaying }?.pause()

    fun resumeBackgroundMusic() = backgroundPlayer?.takeIf { !it.isPlaying }?.start()

    fun stopBackgroundMusic() {
        backgroundPlayer?.stop()
        backgroundPlayer?.release()
        backgroundPlayer = null
    }
}

