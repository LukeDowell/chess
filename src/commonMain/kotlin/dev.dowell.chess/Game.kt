package dev.dowell.chess

data class ChessTile(val position: Position, val piece: Piece? = null, val isSelected: Boolean = false)
data class Game(val board: Board = Board()) {
    fun tiles(): List<ChessTile> = (0..7).map { y ->
        (0..7).map { x -> Position(x = x, y = y)}
            .map { ChessTile(position = it, piece = board.pieceAt(it)) }
    }.flatten()
}
