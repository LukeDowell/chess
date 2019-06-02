import dev.dowell.chess.Board
import dev.dowell.chess.Piece
import kotlin.test.assertEquals

infix fun Board.shouldBe(other: Board): Unit = assertEquals(other, this)