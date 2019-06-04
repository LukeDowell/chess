package dev.dowell.chess

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class GameTests {

    private lateinit var game: Game
    private lateinit var board: Board

    @BeforeTest
    fun setup() {
        board = Board(pieces = allPieces)
        game = Game(board = board)
    }

    @Test
    fun game_tiles_contain_piece_information() {
        val gameTiles = game.tiles()
        val gameTilePieces = gameTiles.mapNotNull { it.piece }

        assertTrue(gameTilePieces.containsAll(board.pieces))
    }
}