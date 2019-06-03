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
                tailrec fun moveDiagonally(xMod: Int, yMod: Int, acc: List<Position> = listOf()): List<Position> {
                    val nextTile: Position =
                        if (acc.isEmpty()) start moveVertically yMod moveHorizontally xMod
                        else acc.last() moveVertically yMod moveHorizontally xMod

                    if (!nextTile.isOnBoard() || nextTile.isOccupied(board)) {
                        return acc
                    }

                    return moveDiagonally(xMod = xMod, yMod = yMod, acc = acc + nextTile)
                }

                val topLeft = moveDiagonally(xMod = -1, yMod = 1)
                val topRight = moveDiagonally(xMod = 1, yMod = 1)
                val bottomLeft = moveDiagonally(xMod = -1, yMod = -1)
                val bottomRight = moveDiagonally(xMod = 1, yMod = -1)

                moves += topLeft + topRight + bottomLeft + bottomRight
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
            .filterNot { isFriendlyPiece(it) }
    }

    private fun Position.isOnBoard(): Boolean = this.x in 0..7 && this.y in 0..7
    private fun Position.isOccupied(board: Board): Boolean = board.pieces.any { it.position == this }

    private infix fun Position.moveVertically(i: Int): Position = this up i
    private infix fun Position.moveHorizontally(i: Int): Position = this right i
}
