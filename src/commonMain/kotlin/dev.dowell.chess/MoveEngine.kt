package dev.dowell.chess

object MoveEngine {

    fun possibleMoves(board: Board, piece: Piece): List<Position> {
        val start = piece.position
        val potentialMoves = when(piece.type) {

            PieceType.PAWN -> when(piece.color) {
                Color.BLACK -> listOf(start down 1)
                Color.WHITE -> listOf(start up 1)
            }

            PieceType.BISHOP -> listOf()

            PieceType.KNIGHT -> listOf()

            PieceType.ROOK -> listOf()
        }


        return potentialMoves.filter { it.isOnBoard()  }
    }

    private fun Position.isOnBoard(): Boolean = this.x in 1..7 && this.y in 1..7

    private infix fun Position.up(i: Int): Position = Position(this.x, this.y + i)
    private infix fun Position.down(i: Int): Position = Position(this.x, this.y - i)
    private infix fun Position.left(i: Int): Position = Position(this.x - i, this.y)
    private infix fun Position.right(i: Int): Position = Position(this.x + i, this.y)

}
