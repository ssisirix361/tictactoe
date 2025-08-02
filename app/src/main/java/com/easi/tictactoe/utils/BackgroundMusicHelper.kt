import android.content.Context
import android.media.MediaPlayer
import com.easi.tictactoe.R

object BackgroundMusicHelper {
    private var mediaPlayer: MediaPlayer? = null

    fun init(context: Context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context.applicationContext, R.raw.arcade_music)
            mediaPlayer?.setVolume(0.3f, 0.3f)
            mediaPlayer?.isLooping = true
        }

    }

    fun playLoop() {
        mediaPlayer?.start()
    }

    fun pause() {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
        }
    }

    fun resume() {
        if (mediaPlayer != null && !mediaPlayer!!.isPlaying) {
            mediaPlayer?.start()
        }
    }

    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
