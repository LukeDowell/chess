package dev.dowell.chess

object MoveEngine {

    fun possibleMoves(board: Board, piece: Piece): List<Position> {
        val start = piece.position
        val moves: MutableList<Position> = mutableListOf()

        tailrec fun moveUntilBlocked(xMod: Int, yMod: Int, distance: Int = 0, acc: List<Position> = listOf()): List<Position> {
            val from: Position = acc.lastOrNull() ?: start
            val nextTile = from moveVertically yMod moveHorizontally xMod

            if (!nextTile.isOnBoard()) return acc
            else if (xMod == 0 && yMod == 0) return acc
            else if (distance != 0 && acc.size >= distance) return acc
            else board.pieces.find { it.position == nextTile }?.let {
                return if (it.color != piece.color) acc + nextTile
                else acc
            }

            return moveUntilBlocked(xMod = xMod, yMod = yMod, acc = acc + nextTile)
        }

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
                moves += diagonals.filter { diagonal -> enemies.any { it.position == diagonal} }
            }

            PieceType.BISHOP -> {
                val topLeft = moveUntilBlocked(xMod = -1, yMod = 1)
                val topRight = moveUntilBlocked(xMod = 1, yMod = 1)
                val bottomLeft = moveUntilBlocked(xMod = -1, yMod = -1)
                val bottomRight = moveUntilBlocked(xMod = 1, yMod = -1)

                moves += topLeft + topRight + bottomLeft + bottomRight
            }

            PieceType.KNIGHT -> { }

            PieceType.ROOK -> {
                val left = moveUntilBlocked(xMod = -1, yMod = 0)
                val up = moveUntilBlocked(xMod = 0, yMod = 1)
                val right = moveUntilBlocked(xMod = 1, yMod = 0)
                val down = moveUntilBlocked(xMod = 0, yMod = -1)

                moves += left + up + right + down
            }

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
