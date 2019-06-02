package dev.dowell.chess

object MoveEngine {

    fun possibleMoves(board: Board, piece: Piece): List<Position> {
        val start = piece.position
        val moves: MutableList<Position> = mutableListOf()

        when(piece.type) {

            PieceType.PAWN -> {
                val hasNotMoved = piece.history.isEmpty()
                val verticalModifier = if (piece.color == Color.BLACK) -1 else 1

                moves += start moveVertically verticalModifier
                if (hasNotMoved) moves += start moveVertically verticalModifier * 2

                val diagonals = listOf(
                    start moveVertically verticalModifier left 1,
                    start moveVertically verticalModifier right 1)

                val enemies = board.pieces.filter { it.color != piece.color }
                moves += diagonals.filter { diagonal -> enemies.any { it.position == diagonal}}
            }

            PieceType.BISHOP -> {
                val topRight = (0..7).map { start up it right it }
                val topLeft = (0..7).map { start up it left it }
                val bottomRight = (0..7).map { start down it right it }
                val bottomLeft = (0..7).map { start down it left it }
                moves += (topRight + topLeft + bottomRight + bottomLeft).distinct()
            }

            PieceType.KNIGHT -> {
                val potentialMoves = listOf(
                    start up 2 left 1, start up 2 right 1,
                    start right 2 up 1, start right 2 down 1,
                    start down 2 right 1, start down 2 left 1,
                    start left 2 up 1, start left 2 down 1)
                    .filter { pos -> board.pieces.any { it.position == pos && it.color == piece.color } }

                moves += potentialMoves
            }

            PieceType.ROOK -> { }

            PieceType.QUEEN -> { }

            PieceType.KING -> { }
        }

        fun isFriendlyPiece(position: Position): Boolean = board.pieces
            .find { it.position == position }
            ?.color == piece.color

        return moves.filter { it.isOnBoard()  }
            .filter { isFriendlyPiece(it) }
    }

    private fun Position.isOnBoard(): Boolean = this.x in 0..7 && this.y in 0..7

    private infix fun Position.moveVertically(i: Int): Position = this up i
}
