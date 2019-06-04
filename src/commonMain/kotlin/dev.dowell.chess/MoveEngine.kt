package dev.dowell.chess

object MoveEngine {

    fun possibleMoves(board: Board, piece: Piece): List<Position> {
        val start = piece.position

        tailrec fun moveUntilBlocked(
            xMod: Int,
            yMod: Int,
            steps: Int = 0,
            acc: List<Position> = listOf()
        ): List<Position> {
            val from: Position = acc.lastOrNull() ?: start
            val nextTile = from up yMod right xMod

            if (!nextTile.isOnBoard()) return acc
            else if (xMod == 0 && yMod == 0) return acc
            else if (steps != 0 && acc.size >= steps) return acc
            else board.pieces.find { it.position == nextTile }?.let {
                return if (it.color != piece.color) acc + nextTile
                else acc
            }

            return moveUntilBlocked(xMod = xMod, yMod = yMod, steps = steps, acc = acc + nextTile)
        }

        return when (piece.type) {

            PieceType.PAWN -> {
                val verticalModifier = if (piece.color == Color.BLACK) -1 else 1
                val numSteps = if (piece.history.isEmpty()) 2 else 1

                val baseMoves = moveUntilBlocked(xMod = 0, yMod = verticalModifier, steps = numSteps)
                val diagonals = listOf(start up verticalModifier right -1, start up verticalModifier right 1)
                val enemyOccupiedDiagonals = board.pieces.filter { diagonals.contains(it.position) }.map { it.position }

                baseMoves + enemyOccupiedDiagonals
            }

            PieceType.KNIGHT -> listOf(
                moveUntilBlocked(xMod = -2, yMod = -1, steps = 1),
                moveUntilBlocked(xMod = -2, yMod = 1, steps = 1),
                moveUntilBlocked(xMod = -1, yMod = 2, steps = 1),
                moveUntilBlocked(xMod = 1, yMod = 2, steps = 1),
                moveUntilBlocked(xMod = 2, yMod = 1, steps = 1),
                moveUntilBlocked(xMod = 2, yMod = -1, steps = 1),
                moveUntilBlocked(xMod = -1, yMod = -2, steps = 1),
                moveUntilBlocked(xMod = 1, yMod = -2, steps = 1)
            ).flatten()

            PieceType.BISHOP -> listOf(
                moveUntilBlocked(xMod = -1, yMod = 1),
                moveUntilBlocked(xMod = 1, yMod = 1),
                moveUntilBlocked(xMod = -1, yMod = -1),
                moveUntilBlocked(xMod = 1, yMod = -1)
            ).flatten()

            PieceType.ROOK -> listOf(
                moveUntilBlocked(xMod = -1, yMod = 0),
                moveUntilBlocked(xMod = 0, yMod = 1),
                moveUntilBlocked(xMod = 1, yMod = 0),
                moveUntilBlocked(xMod = 0, yMod = -1)
            ).flatten()

            PieceType.QUEEN -> listOf(
                moveUntilBlocked(xMod = -1, yMod = 0),
                moveUntilBlocked(xMod = 0, yMod = 1),
                moveUntilBlocked(xMod = 1, yMod = 0),
                moveUntilBlocked(xMod = 0, yMod = -1),
                moveUntilBlocked(xMod = -1, yMod = 1),
                moveUntilBlocked(xMod = 1, yMod = 1),
                moveUntilBlocked(xMod = -1, yMod = -1),
                moveUntilBlocked(xMod = 1, yMod = -1)
            ).flatten()

            PieceType.KING -> listOf(
                moveUntilBlocked(xMod = -1, yMod = 0, steps = 1),
                moveUntilBlocked(xMod = 0, yMod = 1, steps = 1),
                moveUntilBlocked(xMod = 1, yMod = 0, steps = 1),
                moveUntilBlocked(xMod = 0, yMod = -1, steps = 1),
                moveUntilBlocked(xMod = -1, yMod = 1, steps = 1),
                moveUntilBlocked(xMod = 1, yMod = 1, steps = 1),
                moveUntilBlocked(xMod = -1, yMod = -1, steps = 1),
                moveUntilBlocked(xMod = 1, yMod = -1, steps = 1)
            ).flatten()
        }
    }

    private fun Position.isOnBoard(): Boolean = this.x in 0..7 && this.y in 0..7
}
