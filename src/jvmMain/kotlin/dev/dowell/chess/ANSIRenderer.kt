package dev.dowell.chess

import java.lang.StringBuilder

val ANSI_RESET = "\u001B[0m"
val ANSI_BLACK = "\u001B[30m"
val ANSI_RED = "\u001B[31m"
val ANSI_GREEN = "\u001B[32m"
val ANSI_YELLOW = "\u001B[33m"
val ANSI_BLUE = "\u001B[34m"
val ANSI_PURPLE = "\u001B[35m"
val ANSI_CYAN = "\u001B[36m"
val ANSI_WHITE = "\u001B[37m"

val ANSI_BLACK_BACKGROUND = "\u001B[40m"
val ANSI_RED_BACKGROUND = "\u001B[41m"
val ANSI_GREEN_BACKGROUND = "\u001B[42m"
val ANSI_YELLOW_BACKGROUND = "\u001B[43m"
val ANSI_BLUE_BACKGROUND = "\u001B[44m"
val ANSI_PURPLE_BACKGROUND = "\u001B[45m"
val ANSI_CYAN_BACKGROUND = "\u001B[46m"
val ANSI_WHITE_BACKGROUND = "\u001B[47m"

fun boardToANSI(board: Board): String {
    val sb = StringBuilder()
    (0..7).forEach { y ->
        (0..7).forEach { x ->
            val pieceMaybe = board.pieces.find { it.position == Position(x = x, y = y) }
            sb.append(pieceMaybe?.toANSI() ?: "$ANSI_BLUE_BACKGROUND -$ANSI_RESET")
        }
        sb.append("\n")
    }

    return sb.toString()
}

private fun Piece.toANSI(): String {
    val color =
        if (this.color == Color.WHITE) ANSI_WHITE
        else ANSI_BLACK

    val symbol = when(this.type) {
        PieceType.BISHOP -> "B"
        PieceType.KNIGHT -> "K"
        PieceType.PAWN -> "P"
        PieceType.ROOK -> "R"
    }

    return "$color$symbol"
}

data class Tile(val pos: Position, val content: String)

fun renderGame(board: Board, selected: Position? = null) {
    val defaultBg = ANSI_BLUE_BACKGROUND
    val selectedBg = ANSI_RED_BACKGROUND

    val selectedTiles = selected?.let { selectedPosition ->
        val pieceMaybe = board.pieces.find { it.position == selectedPosition }
        val moves = pieceMaybe?.let { MoveEngine.possibleMoves(piece = pieceMaybe, board = board) }.orEmpty()
        moves + selectedPosition
    }
    // TODO

    val sb = StringBuilder()
    (0..7).forEach { y ->
        (0..7).forEach { x ->
            val pieceAt: Piece? = board.pieces.find { it.position == Position(x = x, y = y) }
            val tileContent = pieceAt?.let { "$defaultBg${it.toANSI()} $ANSI_RESET" } ?: "$defaultBg- $ANSI_RESET"
            sb.append(tileContent)
         }
        sb.append("\n")
    }

    System.out.println(sb.toString())
}
