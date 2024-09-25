package com.KKEK.chess.ui

import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.KKEK.chess.Piece
import com.KKEK.chess.PieceColor
import com.KKEK.chess.PieceType
import com.KKEK.chess.Square
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class GameViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
    val auth = Firebase.auth
    val database =  Firebase.database
    val board: Array<Array<Square>> = Array(8){ row ->
        Array(8){
            col -> Square(row,col, mutableStateOf(Piece(PieceType.None,PieceColor.None)))
        }
    }
    val knockedPieces_W: MutableState<MutableList<Piece>> = mutableStateOf(mutableListOf())
    val knockedPieces_B: MutableState<MutableList<Piece>> = mutableStateOf(mutableListOf())
    var currentPlayer: PieceColor = PieceColor.WHITE
    var openinvitePlayerDialog = mutableStateOf(false)
    var isInvalidConnectionCode = mutableStateOf(false)
    val childEventListener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            if (snapshot.key.toString() in listOf("W","B") && snapshot.value.toString() != auth.currentUser?.uid){
                _uiState.update {
                    gameUiState -> gameUiState.copy(
                        isConnected = true,
                        currentPlayer = if(snapshot.key.toString() == "W") 2 else 3
                    )
                }
            }

            if (snapshot.key.toString() == "lastMove"){
                updateMove(snapshot.value.toString().split(","))
            }

            Log.d("Firebase", snapshot.key.toString())
            Log.d("Firebase", "childAdded")
        }

        override fun onChildChanged(
            snapshot: DataSnapshot,
            previousChildName: String?
        ) {
            if (snapshot.key.toString() == "lastMove"){
                updateMove(snapshot.value.toString().split(","))
            }
            if (snapshot.key.toString() == "promote"){
                updatePromotion(PieceType.valueOf(snapshot.value.toString()))
            }
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {


        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {


        }

        override fun onCancelled(error: DatabaseError) {


        }
    }



    init {
        for (i in 0..7) {
            board[1][i].piece = mutableStateOf(Piece(PieceType.PAWN, PieceColor.BLACK))
            board[6][i].piece = mutableStateOf(Piece(PieceType.PAWN, PieceColor.WHITE))
        }
        board[0][0].piece = mutableStateOf(Piece(PieceType.ROOK, PieceColor.BLACK))
        board[0][7].piece = mutableStateOf(Piece(PieceType.ROOK, PieceColor.BLACK))
        board[7][0].piece = mutableStateOf(Piece(PieceType.ROOK, PieceColor.WHITE))
        board[7][7].piece = mutableStateOf(Piece(PieceType.ROOK, PieceColor.WHITE))
        board[0][1].piece = mutableStateOf(Piece(PieceType.KNIGHT, PieceColor.BLACK))
        board[0][6].piece = mutableStateOf(Piece(PieceType.KNIGHT, PieceColor.BLACK))
        board[7][1].piece = mutableStateOf(Piece(PieceType.KNIGHT, PieceColor.WHITE))
        board[7][6].piece = mutableStateOf(Piece(PieceType.KNIGHT, PieceColor.WHITE))
        board[0][2].piece = mutableStateOf(Piece(PieceType.BISHOP, PieceColor.BLACK))
        board[0][5].piece = mutableStateOf(Piece(PieceType.BISHOP, PieceColor.BLACK))
        board[7][2].piece = mutableStateOf(Piece(PieceType.BISHOP, PieceColor.WHITE))
        board[7][5].piece = mutableStateOf(Piece(PieceType.BISHOP, PieceColor.WHITE))
        board[0][3].piece = mutableStateOf(Piece(PieceType.QUEEN, PieceColor.BLACK))
        board[7][3].piece = mutableStateOf(Piece(PieceType.QUEEN, PieceColor.WHITE))
        board[0][4].piece = mutableStateOf(Piece(PieceType.KING, PieceColor.BLACK))
        board[7][4].piece = mutableStateOf(Piece(PieceType.KING, PieceColor.WHITE))
    }

    fun isValidMove(start: Square, end: Square, currentPlayer: PieceColor, pieceType: PieceType = start.piece.value.type): Boolean {
        if (start.piece.value.type == PieceType.None || start.piece.value.color != currentPlayer) return false // No piece or not your turn

        // 1. Check if the end square is within bounds of the board.
        if (end.row !in 0..7 || end.col !in 0..7) return false

        // 2. Check if the end square is occupied by your own piece.
        if (end.piece.value.color == currentPlayer) return false

        // 3. Check if the piece type can make the move (e.g., pawn moves, knight's L-shape, etc.)
        when (pieceType) {
            PieceType.PAWN -> {
                if (currentPlayer == PieceColor.WHITE) {
                    if (end.row == start.row - 1 && end.col == start.col && end.piece.value.type == PieceType.None) {
                        return true
                    } else if (start.row == 6 && end.row == start.row - 2 && end.col == start.col &&
                        board[start.row - 1][start.col].piece.value.type == PieceType.None && end.piece.value.type == PieceType.None
                    ) {
                        return true
                    } else if (end.row == start.row - 1 && (end.col == start.col + 1 || end.col == start.col - 1) &&
                        end.piece.value.color == PieceColor.BLACK
                    ) {
                        return true
                    }
                } else {
                    if (end.row == start.row + 1 && end.col == start.col && end.piece.value.type == PieceType.None) {
                        return true
                    } else if (start.row == 1 && end.row == start.row + 2 && end.col == start.col &&
                        board[start.row + 1][start.col].piece.value.type == PieceType.None && end.piece.value.type == PieceType.None
                    ) {
                        return true
                    } else if (end.row == start.row + 1 && (end.col == start.col + 1 || end.col == start.col - 1) &&
                        end.piece.value.color == PieceColor.WHITE
                    ) {
                        return true
                    }
                }
            }

            PieceType.ROOK -> {
                print( "${start.row},${start.col}, ${end.row},${end.col}")
                if (start.row == end.row) {
                    // Horizontal move
                    val step = 1
                    if(start.col < end.col){
                        for (col in start.col + step until end.col) {
                            print( "Row: ${start.row},Col: ${col}, ${board[start.row][col].piece}")
                            if (board[start.row][col].piece.value.type != PieceType.None) return false
                        }
                    }
                    else{
                        for (col in start.col - step downTo end.col + step) {
                            print( "Row: ${start.row},Col: ${col}, ${board[start.row][col].piece}")
                            if (board[start.row][col].piece.value.type != PieceType.None) return false
                        }
                    }
                    return true
                } else if (start.col == end.col) {
                    // Vertical move
                    val step = 1
                    if (start.row < end.row){
                        for (row in start.row + step until end.row) {
                            print( "Row: $row,Col: ${start.col}, ${board[row][start.col].piece}")
                            if (board[row][start.col].piece.value.type != PieceType.None) return false
                        }
                    }

                    else{
                        for (row in start.row - step downTo end.row + step){
                            print( "Row: $row,Col: ${start.col}, ${board[row][start.col].piece}")
                            if (board[row][start.col].piece.value.type != PieceType.None) return false
                        }
                    }
                    return true
                }
            }

            PieceType.KNIGHT -> {
                val rowDiff = kotlin.math.abs(start.row - end.row)
                val colDiff = kotlin.math.abs(start.col - end.col)
                return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)
            }

            PieceType.BISHOP -> {
                if (kotlin.math.abs(start.row - end.row) == kotlin.math.abs(start.col - end.col)) {
                    val rowStep = if (end.row > start.row) 1 else -1
                    val colStep = if (end.col > start.col) 1 else -1
                    var row = start.row + rowStep
                    var col = start.col + colStep

                    while (row != end.row && col != end.col) {
                        if (board[row][col].piece.value.type != PieceType.None) return false
                        row += rowStep
                        col += colStep
                    }
                    return true
                }
            }

            PieceType.QUEEN -> {
                // Queen moves like a rook or a bishop
                return isValidMove(start, end, currentPlayer, PieceType.ROOK) ||
                        isValidMove(start, end, currentPlayer, PieceType.BISHOP)
            }

            PieceType.KING -> {
                return kotlin.math.abs(start.row - end.row) <= 1 && kotlin.math.abs(start.col - end.col) <= 1
            }

            PieceType.None -> {
                return false
            }
        }
        return false
    }
    fun movePiece(start: Square, end: Square): Boolean {
        print( "Moving piece from ${start.row},${start.col} to ${end.row},${end.col}")
        print( "Current player: $currentPlayer")
        print("${isValidMove(start,end,currentPlayer)}")
        if (isCheckAfterMove(start, end, currentPlayer)){
            if (end.piece.value.type != PieceType.None) {
                println("Captured piece sdf: ${end.piece.value.type}")
                if (end.piece.value.color == PieceColor.WHITE) {
                    knockedPieces_W.value.add(end.piece.value)
                }

                if(end.piece.value.color == PieceColor.BLACK){
                    knockedPieces_B.value.add(end.piece.value)
                }
                _uiState.update {
                    gameUiState -> gameUiState.copy(
                        knockedPieces_B = knockedPieces_B.value.filter { (Piece) -> true },
                        knockedPieces_W = knockedPieces_W.value.filter { (Piece) -> true }
                    )
                }
            }
            println("hrllo")
            println(knockedPieces_W.value+" "+knockedPieces_B.value)
            end.piece.value = start.piece.value // Move piece to new square
            start.piece.value = Piece(PieceType.None,PieceColor.None) // Remove piece from the starting square
            currentPlayer = if (currentPlayer == PieceColor.WHITE) PieceColor.BLACK else PieceColor.WHITE
            if (uiState.value.isConnected) updateMoveInDB(start,end)
            print( "Piece moved from ${start.row},${start.col} to ${end.row},${end.col}")
            return true
            // ... (you may need additional logic here for special moves like pawn promotion)
        } else {
            return false
            // Handle invalid moves
        }
    }
    private fun updateBoardInDB(){
        database.getReference("Match").child(_uiState.value.ConnectionCode.toString()).child("board").setValue(exportGame())
        database.getReference("Match").child(_uiState.value.ConnectionCode.toString()).child("currentPlayer").setValue(currentPlayer)
    }

    fun updateBoard() {
        database.getReference("Match").child(_uiState.value.ConnectionCode.toString()).get().addOnSuccessListener {
            importGame(it.child("board").value.toString())
            currentPlayer = PieceColor.valueOf(it.child("currentPlayer").value.toString())
        }
    }
    private fun updateMoveInDB(start: Square, end: Square) {
        updateBoardInDB()
        database.getReference("Match").child(_uiState.value.ConnectionCode.toString()).child("lastMove").setValue("${start.row},${start.col},${end.row},${end.col}")
    }
    private fun updatePromotionInDB(promoteTO: PieceType) {
        updateBoardInDB()
        database.getReference("Match").child(_uiState.value.ConnectionCode.toString()).child("promote").setValue(promoteTO.toString())
    }

    private fun updatePromotion(promoteTO: PieceType) { promote(promoteTO) }

    private fun updateMove(indexs: List<String>) {
        print("updateMove $indexs")
        if (indexs.size != 4) return
        if (board[indexs[0].toInt()][indexs[1].toInt()].piece.value.type == PieceType.None) return
        movePiece(board[indexs[0].toInt()][indexs[1].toInt()],board[indexs[2].toInt()][indexs[3].toInt()])
    }

    fun isCheckAfterMove(start: Square, end: Square, currentPlayer: PieceColor): Boolean {
        if (!isValidMove(start,end,currentPlayer)) return false
        val originalEndPiece = end.piece.value
        end.piece.value = start.piece.value
        start.piece.value = Piece(PieceType.None,PieceColor.None)

        val isMoveValid = if (isCheck(currentPlayer)) {
            false // Move would put own king in check
        } else {
            true  // Move is valid
        }

        // Undo the simulated move:
        start.piece.value = end.piece.value
        end.piece.value = originalEndPiece

        return isMoveValid
    }

    // ... These functions require more complex logic and are left as placeholders for now.
    fun isCheck(player: PieceColor): Boolean {
        val kingSquare = findKingSquare(player)

        for (row in 0..7) {
            for (col in 0..7) {
                val square = board[row][col]
                if (square.piece.value.type != PieceType.None && square.piece.value.color != player) {
                    if (isValidMove(square, kingSquare, square.piece.value.color)) {
                        return true // King is in check
                    }
                }
            }
        }
        return false
    }

    fun isCheckmate(player: PieceColor): Boolean {
        if (!isCheck(player)) return false // Not in check, so not checkmate

        for (row in 0..7) {
            for (col in 0..7) {
                val startSquare = board[row][col]
                if (startSquare.piece.value.type != PieceType.None && startSquare.piece.value.color == player) {
                    for (endRow in 0..7) {
                        for (endCol in 0..7) {
                            val endSquare = board[endRow][endCol]
                            if (isValidMove(startSquare, endSquare, player)) {
                                // Simulate the move
                                val originalEndPiece = endSquare.piece.value
                                endSquare.piece.value = startSquare.piece.value

                                startSquare.piece.value = Piece(PieceType.None,PieceColor.None)

                                val stillInCheck = isCheck(player)

                                // Undo the move
                                startSquare.piece.value = endSquare.piece.value
                                endSquare.piece.value = originalEndPiece

                                if (!stillInCheck) {
                                    return false // A move gets the king out of check
                                }
                            }
                        }
                    }
                }
            }
        }
        return true // No valid moves to get out of check
    }

    fun isStalemate(player: PieceColor): Boolean {
        if (isCheck(player)) return false // In check, so not stalemate

        for (row in 0..7) {
            for (col in 0..7) {
                val startSquare = board[row][col]
                if (startSquare.piece.value.type != PieceType.None && startSquare.piece.value.color == player) {
                    for (endRow in 0..7) {
                        for (endCol in 0..7) {
                            val endSquare = board[endRow][endCol]
                            if (isValidMove(startSquare, endSquare, player)) {
                                return false // There is at least one valid move
                            }
                        }
                    }
                }
            }
        }
        return true // No valid moves found
    }

    // Helper function to find the king's square
    private fun findKingSquare(player: PieceColor): Square {
        for (row in 0..7) {
            for (col in 0..7) {
                val square = board[row][col]
                if (square.piece.value.type == PieceType.KING && square.piece.value.color == player) {
                    return square
                }
            }
        }
        throw IllegalStateException("King not found on the board!") // This should ideally never happen
    }

    fun canPromote(): Boolean {
        for (row in intArrayOf(0,7)){
            for (col in 0..7){
                val square = board[row][col]
                if (square.piece.value.type == PieceType.PAWN){
                    return true
                }
            }
        }
        return false
    }

    fun promote(promoteTO: PieceType) {
        if (uiState.value.isConnected) updatePromotionInDB(promoteTO)
        print("${promoteTO}")
        for (row in intArrayOf(0,7)){
            for (col in 0..7){
                val square = board[row][col]
                if (square.piece.value.type == PieceType.PAWN){
                    square.piece.value = Piece(promoteTO,square.piece.value.color)
                }
                print("${square.piece.value.type}")
                print("${square.piece.value.type}")
                print("${square.piece.value.type}")
            }
        }


    }

    fun invitePlayer(player: String = "W") {
        if(!_uiState.value.isLoggedIn) loginAnonymously()
        if (!uiState.value.isConnected){
            var player = if (player == "W") "B" else "W"
            var connectionCode = Random.nextInt(1000000,9999999).toString()
            database.getReference("Match").get().addOnSuccessListener {
                while(it.child(connectionCode).exists()) connectionCode = Random.nextInt(1000000,9999999).toString()
            }
            database.getReference("Match").child(connectionCode).child(player).setValue(auth.currentUser?.uid)
            database.getReference("Match").child(connectionCode).child("board").setValue(exportGame())
            database.getReference("Match").child(connectionCode).child("currentPlayer").setValue(currentPlayer)
            database.getReference("Match").child(connectionCode).addChildEventListener(childEventListener)
            _uiState.update {
                gameUiState -> gameUiState.copy(
                    ConnectionCode = connectionCode,
                    // 3 is white and 2 is black
                )
            }
        }


    }

    private fun loginAnonymously() {
        if(auth.signInAnonymously().isSuccessful) {
            //update login Status as true
            _uiState.update { gameUiState ->
                gameUiState.copy(
                    isLoggedIn = true
                )
            }
        }
    }

    fun cancelConnection() {
        database.getReference("Match").child(uiState.value.ConnectionCode.toString()).removeEventListener(childEventListener)
        _uiState.update {
            gameUiState -> gameUiState.copy(
                ConnectionCode = null,
                currentPlayer = 6
            )
        }
    }

    fun acceptInvite(connectionCode:String) {
        if (auth.currentUser == null) loginAnonymously()
        database.getReference("Match").child(connectionCode).get().addOnSuccessListener {
            if (it.child("B").exists()) {
                database.getReference("Match").child(connectionCode).child("W")
                    .setValue(auth.currentUser?.uid)
                _uiState.update {
                    gameUiState -> gameUiState.copy(
                        ConnectionCode = connectionCode,
                        currentPlayer = 3,
                        isConnected = true
                    )
                }

            }
            if (it.child("W").exists()){
                database.getReference("Match").child(connectionCode).child("B")
                    .setValue(auth.currentUser?.uid)
                _uiState.update {
                    gameUiState -> gameUiState.copy(
                        ConnectionCode = connectionCode,
                        currentPlayer = 2,
                        isConnected = true
                    )
                }
            }
            if (!uiState.value.isConnected) {
                isInvalidConnectionCode.value = true
                return@addOnSuccessListener
            }
            database.getReference("Match").child(connectionCode).addChildEventListener(childEventListener)
            importGame(it.child("board").value.toString())
            currentPlayer = PieceColor.valueOf(it.child("currentPlayer").value.toString())
        }
    }

    fun exportGame(): String {
        var gameCode: String = ""
        for (row in board){
            for(col in row){
                val piece = col.piece.value
                var pieceCode = when(piece.type){
                    PieceType.PAWN -> "P"
                    PieceType.ROOK -> "R"
                    PieceType.KNIGHT -> "N"
                    PieceType.BISHOP -> "B"
                    PieceType.QUEEN -> "Q"
                    PieceType.KING -> "K"
                    PieceType.None -> "0"
                }
                if (piece.color == PieceColor.WHITE) gameCode += pieceCode.uppercase()
                if (piece.color == PieceColor.BLACK) gameCode += pieceCode.lowercase()
                if (piece.color == PieceColor.None) gameCode += pieceCode
            }

        }
        return gameCode
    }

    fun importGame(gameCode: String){
        var row = 0
        var col = 0
        for (piece in gameCode){
            when(piece){
                'p' -> board[row][col].piece.value = Piece(PieceType.PAWN,PieceColor.BLACK)
                'r' -> board[row][col].piece.value = Piece(PieceType.ROOK,PieceColor.BLACK)
                'n' -> board[row][col].piece.value = Piece(PieceType.KNIGHT,PieceColor.BLACK)
                'b' -> board[row][col].piece.value = Piece(PieceType.BISHOP,PieceColor.BLACK)
                'q' -> board[row][col].piece.value = Piece(PieceType.QUEEN,PieceColor.BLACK)
                'k' -> board[row][col].piece.value = Piece(PieceType.KING,PieceColor.BLACK)
                'P' -> board[row][col].piece.value = Piece(PieceType.PAWN,PieceColor.WHITE)
                'R' -> board[row][col].piece.value = Piece(PieceType.ROOK,PieceColor.WHITE)
                'N' -> board[row][col].piece.value = Piece(PieceType.KNIGHT,PieceColor.WHITE)
                'B' -> board[row][col].piece.value = Piece(PieceType.BISHOP,PieceColor.WHITE)
                'Q' -> board[row][col].piece.value = Piece(PieceType.QUEEN,PieceColor.WHITE)
                'K' -> board[row][col].piece.value = Piece(PieceType.KING,PieceColor.WHITE)
                else -> board[row][col].piece.value = Piece(PieceType.None,PieceColor.None)
            }
            col++
            if (col == 8){
                col = 0
                row++
            }
        }
    }

    fun reset() {
        for (i in 0..7) {
            board[1][i].piece = mutableStateOf(Piece(PieceType.PAWN, PieceColor.BLACK))
            board[6][i].piece = mutableStateOf(Piece(PieceType.PAWN, PieceColor.WHITE))
        }
        for (i in 2..5){
            for (j in 1..7){
                board[i][j].piece.value = Piece(PieceType.None,PieceColor.None)
            }
        }
        board[0][0].piece.value = Piece(PieceType.ROOK, PieceColor.BLACK)
        board[0][7].piece.value = Piece(PieceType.ROOK, PieceColor.BLACK)
        board[7][0].piece.value = Piece(PieceType.ROOK, PieceColor.WHITE)
        board[7][7].piece.value = Piece(PieceType.ROOK, PieceColor.WHITE)
        board[0][1].piece.value = Piece(PieceType.KNIGHT, PieceColor.BLACK)
        board[0][6].piece.value = Piece(PieceType.KNIGHT, PieceColor.BLACK)
        board[7][1].piece.value = Piece(PieceType.KNIGHT, PieceColor.WHITE)
        board[7][6].piece.value = Piece(PieceType.KNIGHT, PieceColor.WHITE)
        board[0][2].piece.value = Piece(PieceType.BISHOP, PieceColor.BLACK)
        board[0][5].piece.value = Piece(PieceType.BISHOP, PieceColor.BLACK)
        board[7][2].piece.value = Piece(PieceType.BISHOP, PieceColor.WHITE)
        board[7][5].piece.value = Piece(PieceType.BISHOP, PieceColor.WHITE)
        board[0][3].piece.value = Piece(PieceType.QUEEN, PieceColor.BLACK)
        board[7][3].piece.value = Piece(PieceType.QUEEN, PieceColor.WHITE)
        board[0][4].piece.value = Piece(PieceType.KING, PieceColor.BLACK)
        board[7][4].piece.value = Piece(PieceType.KING, PieceColor.WHITE)

        _uiState.update {
            gameUiState -> gameUiState.copy(
                ConnectionCode = null,
                isConnected = false,
                currentPlayer = 6,
                knockedPieces_W = mutableListOf(),
                knockedPieces_B = mutableListOf()
            )
        }

        knockedPieces_B.value = mutableListOf()
        knockedPieces_W.value = mutableListOf()
        openinvitePlayerDialog.value = false
        isInvalidConnectionCode.value = false
        currentPlayer = PieceColor.WHITE
    }


}
