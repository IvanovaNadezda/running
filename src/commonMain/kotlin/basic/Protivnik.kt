package basic

import com.soywiz.kds.random.FastRandom
import com.soywiz.klock.TimeSpan
import com.soywiz.korge.view.*
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korge.view.image
import kotlin.random.Random

class Protivnik(private val startX: Double,private val startY: Double ): Container() {

    val r=(0..2).random()

    suspend fun create(): Image {
        if(r==0){
            val bitmap = resourcesVfs["arrow1.png"].readBitmap()
            val img = image(bitmap).xy(startX, startY)
            img.name("obstacle").isContainer
            return img.scale(0.2) //1.15 // для файла tree1 0.3 // 1.15 for treee and 1.1 for elll
        }
        else if(r==1){
            val bitmap = resourcesVfs["arrow2.png"].readBitmap()
            val img = image(bitmap).xy(startX, startY)
            img.name("obstacle").isContainer
            return img.scale(0.2)
        }
        else {
            val bitmap = resourcesVfs["arrow3.png"].readBitmap()
            val img = image(bitmap).xy(startX, startY)
            img.name("obstacle").isContainer
            return img.scale(0.2)
        }

    }


    /*suspend fun create(): Image {
        val bitmap = resourcesVfs["cactus.png"].readBitmap()
        val img = image(bitmap).xy(startX, startY)
        img.name("obstacle").isContainer
        return img.scale(2.0)
    }*/
}