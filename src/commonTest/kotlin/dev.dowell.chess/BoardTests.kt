import dev.dowell.chess.*
import kotlin.test.*

class BoardTests {

    private lateinit var board: Board

    @BeforeTest
    fun setup() {
        board = Board()
    }

    @Test
    fun placed_piece_should_exist() {
        board = Board() with Piece.at(x = 0, y = 0)

        assertEquals(1, board.pieces.size)
    }

    @Test
    fun can_move_piece() {
        val start = Position(x = 0, y = 0)
        val dest = Position(x = 0, y = 1)
        board = board with Piece.at(start)
        board.movePiece(start, dest)

        board shouldBe (Board() with Piece.at(dest))
    }

    @Test
    fun cannot_move_nonexistent_piece() {
        board = Board.emptyBoard
        assertFalse(board.movePiece(Position(x = 0, y = 0), Position(x = 1, y = 1)))
    }

    @Test
    fun cannot_move_piece_to_occupied_location() {
        val posOne = Position(x = 0, y = 0)
        val posTwo = Position(x = 1, y = 1)
        board = Board.of(Piece.at(posOne), Piece.at(posTwo))
        assertFalse(board.movePiece(posOne, posTwo))
    }

    private infix fun Board.shouldBe(other: Board): Unit = assertEquals(other, this)
    private infix fun Board.with(piece: Piece): Board {
        this.pieces += piece
        return this
    }
}