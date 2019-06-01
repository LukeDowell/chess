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
            }

            PieceType.BISHOP -> {}

            PieceType.KNIGHT -> {}

            PieceType.ROOK -> {}
        }


        return moves.filter { it.isOnBoard()  }
    }

    private fun Position.isOnBoard(): Boolean = this.x in 0..7 && this.y in 0..7

    private infix fun Position.moveVertically(i: Int): Position = this up i
}
