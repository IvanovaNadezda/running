package basic

import com.soywiz.kds.random.FastRandom
import com.soywiz.klock.timesPerSecond
import com.soywiz.korge.view.*
import com.soywiz.korio.async.launch
import constants.GameStatus
import kotlinx.coroutines.*

class GameWorld(private val game: GameManager): Container() {
    private var speedFactor: Double = 4.0
    private val onePosX = 1000.0
    private val prPosX = 10000.0
    private val xSpacer = 300.0
    private val startY = 270.0
    private val startY1 = 110.0
    private val speedIncrement = 0.0001
    private var obstacles: MutableList<Image> = mutableListOf()
    private var pr: MutableList<Image> = mutableListOf()

    suspend fun init() {
        initializeWorld()
        initializeUpdater()
    }

    private suspend fun initializeWorld() {
        var obstacleX = onePosX
        var prX=prPosX
        var i = 0
        while(i != 3){
            obstacles.add(Obstacle(obstacleX,startY).create())
            pr.add(Protivnik(prX,startY1).create())
            obstacleX += 500
            prX+=3000
            i++
        }
        obstacles.forEach {
            addChild(it)
        }
        pr.forEach { addChild(it) }
    }

    private fun initializeUpdater() {
        addFixedUpdater(60.timesPerSecond) {
            if(game.status == GameStatus.RESTARTED) {
                stopWorld()
                speedFactor = 4.0
            }
            if(game.status == GameStatus.RUNNING) {
                speedFactor += speedFactor*speedIncrement

                val obstacleIterator = obstacles.iterator()
                while(obstacleIterator.hasNext()) {
                    val obstacle = obstacleIterator.next()
                    val x = obstacle.x - (1 * speedFactor)
                    obstacle.position(x, obstacle.y)
                    if(x < 0) {
                        obstacleIterator.remove()
                        removeChild(obstacle)
                        addObstacle()
                    }
                }
                val prIterator = pr.iterator()
                while(prIterator.hasNext()) {
                    val pr = prIterator.next()
                    val x = pr.x - (2 * speedFactor+speedIncrement)
                    pr.position(x, pr.y)
                    if(x < 0) {
                        prIterator.remove()
                        removeChild(pr)
                        addPr()
                    }
                }
            }
        }
    }

    private fun addPr(){
        if (game.status == GameStatus.RUNNING){
            GlobalScope.launch {
                val newPr = Protivnik(prPosX+FastRandom.nextDouble() * (xSpacer - 0.0),startY1).create()
                addChild(newPr)
                pr.add(newPr)
            }
        }
    }


    private fun addObstacle() {
        if(game.status == GameStatus.RUNNING) {
            GlobalScope.launch {
                val newObstacle = Obstacle(onePosX + FastRandom.nextDouble() * (xSpacer - 0.0), startY).create()
                addChild(newObstacle)
                obstacles.add(newObstacle)
            }
        }
    }

    private fun stopWorld() {
        obstacles.forEach { removeChild(it) }
        pr.forEach { removeChild(it) }
        obstacles = mutableListOf()
        pr = mutableListOf()
        GlobalScope.launch {
            initializeWorld()
        }
    }
}