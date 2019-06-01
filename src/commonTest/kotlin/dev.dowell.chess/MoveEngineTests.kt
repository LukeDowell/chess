package dev.dowell.chess

import and
import with
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MoveEngineTests {

    private lateinit var board: Board

    @BeforeTest
    fun setup() {
        board = Board()
    }

    @Test
    fun black_pawn_moves() {
        val pawn = Piece.at(x = 1, y = 6)
        board = Board() with pawn

        assertTrue(MoveEngine.possibleMoves(board, pawn).contains(Position(x = 1, y = 5)))
    }

    @Test
    fun white_pawn_moves() {
        val pawn = Piece.at(x = 1, y = 1).copy(color = Color.WHITE)
        board = Board() with pawn

        assertTrue(MoveEngine.possibleMoves(board, pawn).contains(Position(x = 1, y = 2)))
    }

    @Test
    fun pawn_can_move_twice_on_first_turn() {
        val blackPawn = Piece.at(x = 1, y = 6)
        val whitePawn = Piece.at(x = 1, y = 1).copy(color = Color.WHITE)

        board = Board() with blackPawn and whitePawn

        assertTrue(MoveEngine.possibleMoves(board, blackPawn).contains(Position(x = 1, y = 4)))
        assertTrue(MoveEngine.possibleMoves(board, whitePawn).contains(Position(x = 1, y = 3)))
    }

    @Test
    fun pawn_can_only_move_once_after_first_turn() {
        val start = Position(x = 1, y = 6)
        val blackPawn = Piece.at(start)
        blackPawn.history += Move(start, start down 1)

        board = Board() with blackPawn

        assertFalse(MoveEngine.possibleMoves(board, blackPawn).contains(Position(x = 1, y = 3)))
    }

    @Test
    fun pawn_can_attack_diagonally() {
        val blackPawn = Piece.at(x = 1, y = 6)
        val whitePawn = Piece.at(x = 0, y = 5)
        board = Board() with blackPawn and whitePawn

        assertTrue(MoveEngine.possibleMoves(board, blackPawn).contains(Position(x = 0, y = 5)))
    }

    @Test
    fun rook_can_move_laterally_and_vertically() {
        val start = Position(x = 3, y = 3)
        val rook = Piece(position = start, type = PieceType.ROOK)
        board = Board() with rook

        val lateralMoves = (0 until 8).map { Position(x = it, y = start.y) }.filter { it == start }
        val verticalMoves = (0 until 8).map { Position(x = start.x, y = it) }.filter { it == start }

        assertTrue(MoveEngine.possibleMoves(board, rook).containsAll(lateralMoves + verticalMoves))
    }
}