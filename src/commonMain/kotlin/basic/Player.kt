package basic

import com.soywiz.klock.TimeSpan
import com.soywiz.korau.sound.Sound
import com.soywiz.korau.sound.readSound
import com.soywiz.korev.Key
import constants.GameStatus
import constants.PlayerStatus
import com.soywiz.korge.input.*
import com.soywiz.korge.view.*
import com.soywiz.korge.view.addUpdater
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs


class Player(private val game: GameManager): Container() {

    private var status = PlayerStatus.RUNNING
    private var speedFactor = 6.0
    private val yLimit = 100.0
    private val initialY = 300.0
    //private lateinit var protivnik:Protivnik
    private lateinit var owl: Sprite


    suspend fun init() {
        val sky = SolidRect(10000,1000).xy(0,0).alpha(0.0)
        addChild(sky)
        owl = buildOwl()
        //protivnik.buildProtivnik()
        game.goStartMessage()

        sky.touch{
            onDown{
                if (game.status == GameStatus.NOT_STARTED) {
                    game.stopStartMessage()
                    game.start()
                }
                if (game.status == GameStatus.FINISHED) {
                    game.restart()
                }
                if(game.status == GameStatus.RUNNING) {
                    val owlY = owl.y
                    if (owlY >= 100) {
                        status = PlayerStatus.JUMPING_UP
                    }
                }
            }
        }

        owl.onCollision({ it.name === "obstacle" }) {
            game.finish()
            stopAnimation()
            //val finishGame = SolidRect(10000,1000, Colors.DARKORCHID).xy(0,0)
            //addChild(finishGame)
        }

        owl.addUpdater {
            if (game.isRunning == true) {
                owl.position(getCoordinates(x,y).x,getCoordinates(x,y).y)
            }
        }
    }


    private suspend fun buildOwl(): Sprite {
        val img = resourcesVfs["sovi_.png"].readBitmap()
        val fly = SpriteAnimation(img,140,90, columns = 2)
        val sprite = sprite(fly)
        sprite.xy(40.0,initialY)
        return sprite.scale(0.5)
        //return image(img).xy(40.0,initialY).scale(0.12)
    }



    private fun goAnimation() {
        owl.playAnimationLooped(spriteDisplayTime = TimeSpan(200.0))
        //protivnik.goAnimation()
    }

    private fun stopAnimation() {
        owl.stopAnimation()
        //protivnik.stopAnimation()
    }

    private fun getCoordinates(startingX: Double, startingY: Double): Coordinates {
        val x: Double = startingX
        var y: Double = startingY

        if (status == PlayerStatus.JUMPING_UP) {
            y -= 1 * speedFactor
            if (y <= yLimit) {
                status = PlayerStatus.JUMPING_DOWN
            }
        }
        else if(status == PlayerStatus.JUMPING_DOWN){
            y += 1 * speedFactor
            if (y >= initialY) {
                status = PlayerStatus.RUNNING
                goAnimation()
            }
        }

        return Coordinates(x, y)
    }
}

class Coordinates(val x: Double, val y: Double)



