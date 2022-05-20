import basic.*
import com.soywiz.klogger.AnsiEscape
import com.soywiz.korge.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors



suspend fun main() = Korge(width = 1000, height = 500, bgcolor = Colors["#6495ed"]){
	val game = GameManager(this)
	val player = Player(game)
	val world = GameWorld(game)
	val scoreBoard = ScoreBoard(game)
	addChild(scoreBoard)
	scoreBoard.doDay()
	addChild(world)
	addChild(player)
	world.init()
	player.init()
}

