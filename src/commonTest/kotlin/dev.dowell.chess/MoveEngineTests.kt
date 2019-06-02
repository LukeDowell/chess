package dev.dowell.chess

import kotlin.test.*

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
        val whitePawn = Piece.at(x = 0, y = 5).copy(color = Color.WHITE)
        board = Board() with blackPawn and whitePawn

        assertTrue(MoveEngine.possibleMoves(board, blackPawn).contains(Position(x = 0, y = 5)))
    }

    @Test
    fun bishop_moves_diagonally() {
        val start = Position(x = 3, y = 3)
        val bishop = Piece(position = start, type = PieceType.BISHOP)

        board = Board() with bishop

        val expectedMoves = (1..3).flatMap { listOf(
            start up it left it,
            start up it right it,
            start down it left it,
            start down it right it
        ) }

        assertTrue(MoveEngine.possibleMoves(board, bishop).containsAll(expectedMoves))
    }

    @Test
    fun bishop_cant_move_through_pieces() {
        val start = Position(x = 3, y = 3)
        val bishop = Piece(position = start, type = PieceType.BISHOP)
        val blockingPiece = Piece(position = start up 1 left 1)

        board = Board() with bishop
        val blockedBoard = Board() with bishop and blockingPiece

        val unblockedCalculatedMoves = MoveEngine.possibleMoves(board, bishop)
        val blockedCalculatedMoves = MoveEngine.possibleMoves(blockedBoard, bishop)

        val blockedMoves = (1..3).map { start up it left it}

        assertTrue(unblockedCalculatedMoves.containsAll(blockedMoves))
        assertFalse(blockedCalculatedMoves.containsAll(blockedMoves))
    }

    @Test
    fun knight_can_move() {
        val start = Position(x = 3, y = 3)
        val enemyPosition = start up 2 left 1
        val friendPosition = start up 2 right 1
        val knight = Piece(position = start, type = PieceType.KNIGHT, color = Color.WHITE)
        val enemy = Piece(position = enemyPosition, color = Color.BLACK)
        val friend = Piece(position = friendPosition, color = Color.WHITE)

        board = Board() with knight and enemy and friend
        assertFalse(true)
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