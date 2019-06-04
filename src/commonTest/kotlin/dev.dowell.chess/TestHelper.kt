import dev.dowell.chess.Board
import kotlin.test.assertEquals

infix fun Board.shouldBe(other: Board): Unit = assertEquals(other, this)