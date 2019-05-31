package dev.dowell.chess

data class Board(var pieces: List<Piece> = listOf()) {
    companion object {
        val emptyBoard: Board = Board()
        fun of(vararg pieces: Piece): Board = Board(pieces.asList())
    }
}

fun Board.movePiece(from: Position, to: Position): Boolean {
    if (this.pieces.any { it.position == to }) {
        return false
    }

    if (this.pieces.none { it.position == from}) {
        return false
    }

    this.pieces = pieces.map { if (it.position == from) it.copy(position = to) else it }

    return true
}

enum class Color {
    WHITE,
    BLACK;
}

enum class PieceType {
    PAWN;
}

data class Position(val x: Int, val y: Int)
data class MoveHistory(val from: String, val to: String)

data class Piece(val position: Position, val color: Color = Color.BLACK, val type: PieceType = PieceType.PAWN) {
    companion object {
        fun at(x: Int, y: Int): Piece = Piece(position = Position(x, y))
        fun at(position: Position): Piece = Piece(position = position)
    }
}