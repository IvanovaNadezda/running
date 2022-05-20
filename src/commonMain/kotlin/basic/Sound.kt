package basic

import com.soywiz.korau.sound.Sound
import com.soywiz.korau.sound.readSound
import com.soywiz.korge.view.Image
import com.soywiz.korge.view.image
import com.soywiz.korge.view.xy
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs

class Sound {
    suspend fun play(): Sound {
        val sound = resourcesVfs["coin.mp3"].readSound()
        return sound
    }
}