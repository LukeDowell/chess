import dev.dowell.chess.Board
import dev.dowell.chess.Piece
import kotlin.test.assertEquals

infix fun Board.shouldBe(other: Board): Unit = assertEquals(other, this)
infix fun Board.and(piece: Piece): Board {
    this.pieces += piece
    return this
}
infix fun Board.with(piece: Piece): Board {
    this.pieces += piece
    return this
}