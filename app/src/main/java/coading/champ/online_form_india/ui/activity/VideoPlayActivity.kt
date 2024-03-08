package coading.champ.online_form_india.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coading.champ.online_form_india.databinding.ActivityVideoPlayBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener

class VideoPlayActivity : AppCompatActivity() {

    lateinit var binding : ActivityVideoPlayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val videoId = intent.getStringExtra("youtubeLink")


        binding.vpToolbar.setOnClickListener{
            finish()
        }


        binding.vpYoutubePlayerView.addYouTubePlayerListener(object :YouTubePlayerListener{
            override fun onApiChange(youTubePlayer: YouTubePlayer) {
                
            }

            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                
            }

            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                
            }

            override fun onPlaybackQualityChange(
                youTubePlayer: YouTubePlayer,
                playbackQuality: PlayerConstants.PlaybackQuality
            ) {
                
            }

            override fun onPlaybackRateChange(
                youTubePlayer: YouTubePlayer,
                playbackRate: PlayerConstants.PlaybackRate
            ) {
                
            }

            override fun onReady(youTubePlayer: YouTubePlayer) {
                if (videoId != null) {
                    val f :Float = 0.3F
                    youTubePlayer.cueVideo(videoId,f)
                }
            }

            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
                
            }

            override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
                
            }

            override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
                
            }

            override fun onVideoLoadedFraction(
                youTubePlayer: YouTubePlayer,
                loadedFraction: Float
            ) {
                
            }
        })


    }
}