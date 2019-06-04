package dev.dowell.chess

val whiteRooks = listOf(
    Piece(position = Position(x = 0, y = 0), color = Color.WHITE, type = PieceType.ROOK),
    Piece(position = Position(x = 7, y = 0), color = Color.WHITE, type = PieceType.ROOK)
)
val whiteKnights = listOf(
    Piece(position = Position(x = 1, y = 0), color = Color.WHITE, type = PieceType.KNIGHT),
    Piece(position = Position(x = 6, y = 0), color = Color.WHITE, type = PieceType.KNIGHT)
)
val whiteBishops = listOf(
    Piece(position = Position(x = 2, y = 0), color = Color.WHITE, type = PieceType.BISHOP),
    Piece(position = Position(x = 5, y = 0), color = Color.WHITE, type = PieceType.BISHOP)
)
val whitePawns = (0..7).map { Piece(position = Position(x = it, y = 1), color = Color.WHITE, type = PieceType.PAWN) }
val whiteQueen = Piece(position = Position(x = 4, y = 0), color = Color.WHITE, type = PieceType.QUEEN)
val whiteKing = Piece(position = Position(x = 3, y = 0), color = Color.WHITE, type = PieceType.KING)
val whitePieces = whiteRooks + whiteKnights + whiteBishops + whitePawns + whiteQueen + whiteKing

val blackRooks = whiteRooks.map { it.copy(position = Position(x = it.position.x, y = 7), color = Color.BLACK) }
val blackKnights = whiteKnights.map { it.copy(position = Position(x = it.position.x, y = 7), color = Color.BLACK) }
val blackBishops = whiteBishops.map { it.copy(position = Position(x = it.position.x, y = 7), color = Color.BLACK) }
val blackPawns = whitePawns.map { it.copy(position = Position(x = it.position.x, y = 6), color = Color.BLACK) }
val blackQueen = Piece(position = Position(x = 4, y = 7), color = Color.BLACK, type = PieceType.QUEEN)
val blackKing = Piece(position = Position(x = 3, y = 7), color = Color.BLACK, type = PieceType.KING)
val blackPieces = blackRooks + blackKnights + blackBishops + blackPawns + blackQueen + blackKing

val allPieces = whitePieces + blackPieces