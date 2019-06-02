package dev.dowell.chess

fun main() {
    val board = Board(pieces = whitePieces + blackPieces)

    renderGame(board = board)
}