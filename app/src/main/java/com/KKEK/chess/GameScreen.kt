package com.KKEK.chess

import android.annotation.SuppressLint
import android.icu.text.CaseMap.Title
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.KKEK.chess.ui.GameViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    game: GameViewModel = viewModel(),
){
    val gameUiState by game.uiState.collectAsState()
    val openinvitePlayerDialog = remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {AppBar()},
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (!gameUiState.isConnected) game.openinvitePlayerDialog.value = true
                else game.updateBoard()}) {
                Text(
                    text = if (gameUiState.isConnected) "Refresh" else "Invite Online",
                    modifier = Modifier.padding(16  .dp,0.dp))
            }
        }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (gameUiState.currentPlayer == 3){
                PlayerInfo(
                    Player = PieceColor.WHITE,
                    knockedpieces = game.knockedPieces_W.value,
                    invitePlayer = fun (player:String){ game.invitePlayer(player)}
                )
            }
            else{
                PlayerInfo(
                    Player = PieceColor.BLACK,
                    knockedpieces = game.knockedPieces_B.value,
                    invitePlayer = fun (player:String){ game.invitePlayer(player)}
                )
            }
            ChessBoard(game = game)
            if (gameUiState.currentPlayer != 3){
                PlayerInfo(
                    Player = PieceColor.WHITE,
                    knockedpieces = game.knockedPieces_W.value,
                    invitePlayer = fun (player:String){ game.invitePlayer(player)}
                )
            }
            else{
                PlayerInfo(
                    Player = PieceColor.BLACK,
                    knockedpieces = game.knockedPieces_B.value,
                    invitePlayer = fun (player:String){ game.invitePlayer(player)}
                )
            }

//            if (gameUiState.ConnectionCode != null && !gameUiState.isConnected){
//                BasicAlertDialog(onDismissRequest = { game.cancelConnection() }) {
//                    Surface(
//                        shape = MaterialTheme.shapes.large,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp)
//                            .aspectRatio(5f)
//                    ) {
//                        Column(
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                            verticalArrangement = Arrangement.Center
//                        ) {
//                            Text(text = "${gameUiState.ConnectionCode}")
//                        }
//                    }
//                }
//            }
            
            if (game.openinvitePlayerDialog.value && !gameUiState.isConnected){
                var selectedPlayer = remember {
                    mutableStateOf("White")
                }
                var tab = remember {
                    mutableStateOf(0)
                }
                val options = listOf("White","Black")
                var connectionCode = remember {
                    mutableStateOf("")
                }
                AlertDialog(
                    onDismissRequest = {
                        game.openinvitePlayerDialog.value = false
                    },
                    title = { Column {
                        PrimaryTabRow(selectedTabIndex = tab.value) {
                            listOf("invite","Accept").forEachIndexed{ index, title ->
                                Tab(selected = index == tab.value,
                                    onClick = { tab.value = index }
                                ) {
                                    Text(text = title)
                                }
                            }
                        }
                        HorizontalDivider(thickness = 2.dp)
                    } },
                    text = {
                        if (tab.value == 0){
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = "Invite Player", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    SingleChoiceSegmentedButtonRow {
                                        options.forEachIndexed{ index,player ->
                                            SegmentedButton(
                                                selected = player == selectedPlayer.value,
                                                onClick = {
                                                    selectedPlayer.value = player
                                                    if(gameUiState.ConnectionCode != null) game.invitePlayer(selectedPlayer.value[0].toString())},
                                                shape = SegmentedButtonDefaults.itemShape(
                                                    index = index,
                                                    count = options.size
                                                ),
                                                label = { Text(text = player)}
                                            )
                                        }
                                    }
                                }
                                if (gameUiState.ConnectionCode !=  null){
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Row {
                                        Text(text = "Invitation Code :", fontWeight = FontWeight.SemiBold)
                                        Spacer(modifier = Modifier.width(32.dp))
                                        Text(text = gameUiState.ConnectionCode.toString(), fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                        if (tab.value == 1){
                            TextField(
                                value = connectionCode.value, 
                                onValueChange = { connectionCode.value = it },
                                label = { Text(text = "Code")},
                                isError = game.isInvalidConnectionCode.value
                            )
                        }

                    },
                    confirmButton = {
                        TextButton(onClick = {
                            if (tab.value == 0) game.invitePlayer(selectedPlayer.value[0].toString())
                            else game.acceptInvite(connectionCode.value)
                            if (gameUiState.isConnected) game.openinvitePlayerDialog.value = false

                        }) {
                            if (tab.value == 0) Text(text = "invie")
                            else Text(text = "accept")
                        }
                    },

                    dismissButton = {
                        TextButton(onClick = {
                            game.openinvitePlayerDialog.value = false
                            game.isInvalidConnectionCode.value = false
                        }){
                            Text(text = "Dismiss")
                        }
                    },
                )
            }
        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    CenterAlignedTopAppBar(
        title = { Text(text = "KKEK") },
        navigationIcon = {
            var expand = remember {
                mutableStateOf(false)
            }
            Box(modifier = Modifier){
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "Menu",
                    modifier = Modifier.clickable(
                        enabled = true,
                        onClick = {
                            expand.value = true
                        }
                    ))
                DropdownMenu(expanded = expand.value, onDismissRequest = { expand.value = false }) {
                    DropdownMenuItem(text = { Text(text = "Coming Soon") }, onClick = { /*TODO*/ })
                }

            }
        },
        
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChessBoard(modifier: Modifier = Modifier,game: GameViewModel = viewModel()) {
//  val game = viewModel.board
    val gameUiState by game.uiState.collectAsState()
    val selectedSquare = remember { mutableStateOf<Square>(
        Square(
            row = -1,
            col = -1,
            piece = mutableStateOf(Piece(PieceType.None,PieceColor.None))
        )
    ) }
    val promote = remember { mutableStateOf(false) }
    val checkMate = remember { mutableStateOf(false) }
    val Stalemate = remember { mutableStateOf(false) }
    Surface(
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        content = {
//            PlayerInfo(orientation,game.currentPlayer,game.knockedPieces_B)
        },
        modifier = Modifier.padding(16.dp)
    )

    Box(modifier = modifier.aspectRatio(1f)){

        Column(
            modifier
                .aspectRatio(1f)
                .rotate(if (gameUiState.currentPlayer == 2) 180f else 0f)
        ) {
            for (row in 0..7) {
                Row(
                    modifier = Modifier.weight(1f)
                ) {
                    for (col in 0..7) {
                        val square = game.board[row][col]
                        var borderColor: Color
                        if (selectedSquare.value == square){
                            borderColor = Color.Green
                        }
                        else if (
                            selectedSquare.value.piece.value.type != PieceType.None &&
                            game.isValidMove(selectedSquare.value,square,currentPlayer = game.currentPlayer) && game.isCheckAfterMove(selectedSquare.value!!,square,game.currentPlayer)){
                            if (square.piece.value.type != PieceType.None){
                                borderColor = Color.Red
                            }
                            else{
                                borderColor = Color.Yellow
                            }
                        }
                        else if(square.piece.value.type == PieceType.KING){
                            if (game.isCheck(PieceColor.WHITE) && square.piece.value.color == PieceColor.WHITE){
                                borderColor = Color.Red
                            }
                            else if (game.isCheck(PieceColor.BLACK) && square.piece.value.color == PieceColor.BLACK){
                                borderColor = Color.Red
                            }
                            else borderColor = Color.Black
                        }
                        else{
                            borderColor = Color.Black
                        }
                        ChessboardSquare(
                            square = square,
                            modifier = modifier.rotate(
                                if (gameUiState.currentPlayer == 2 || (square.piece.value.color == PieceColor.BLACK && !gameUiState.isConnected)) 180f
                                else 0f
                            ),
                            onSquareClick = {
                                if (square.piece.value.type != PieceType.None && square.piece.value.color == game.currentPlayer) {
                                    if (game.currentPlayer == PieceColor.WHITE && gameUiState.currentPlayer % 3 == 0) {
                                        selectedSquare.value = square
                                    }
                                    if (game.currentPlayer == PieceColor.BLACK && gameUiState.currentPlayer % 2 == 0){
                                        selectedSquare.value = square
                                    }
                                } else if (selectedSquare.value.piece.value.type != PieceType.None) {
                                    if(game.movePiece(selectedSquare.value, square)) {
                                        promote.value = game.canPromote()
                                        checkMate.value = game.isCheckmate(game.currentPlayer)
                                        Stalemate.value = game.isStalemate(game.currentPlayer)
                                        selectedSquare.value =
                                            Square(
                                                row = -1,
                                                col = -1,
                                                piece = mutableStateOf(Piece(PieceType.None,PieceColor.None))
                                            )
                                        // Clear selection after move
                                    }

                                }
                            },
                            borderColor = borderColor
                        )
                    }
                }
            }

        }
        Column(
            Modifier.align(Alignment.TopStart)
        ) {
            for (col in 0..7){
                Box(modifier = Modifier
                    .width(10.dp)
                    .weight(1f)
                    .padding(2.dp, 2.dp)
                ){
                    val color = if (col % 2 == 0) Color.DarkGray else Color.LightGray
                    Text(text = "${ col+1}", color = color)
                }
            }
        }
        Row(
            Modifier.align(Alignment.BottomCenter)
        ) {
            for (row in 0..7){
                Box(modifier = Modifier
                    .height(22.dp)
                    .weight(1f)
                    .padding(2.dp, 1.dp)
                ){
                    val color = if (row % 2 != 0) Color.DarkGray else Color.LightGray
                    Text(text = "${Char(row+65)}",Modifier.align(Alignment.BottomEnd), color = color)
                }
            }
        }

    }



    Surface(
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        content = {
//            PlayerInfo(orientation,game.currentPlayer,game.knockedPieces_W)
        },
        modifier = Modifier.padding(16.dp)
    )
//    Text(
//        text = "Current Player: ${game.currentPlayer}"
//    )
//    Text(text = "isCheck: ${game.isCheck(game.currentPlayer)}")
//    Text(text = "isCheckMate: ${game.isCheckmate(game.currentPlayer)}")
//    Text(text = "can Promote: ${game.canPromote()}")
//    Text(text = "isStalemate: ${game.isStalemate(game.currentPlayer)}")
//    Button(onClick = {
//
//    }) {
//        androidx.compose.material3.Text(text = "reset")
//    }

    if(promote.value){
        BasicAlertDialog(
            onDismissRequest = { /*TODO*/ }
        ) {
            val canPromoteTO: List<PieceType> = listOf(
                PieceType.QUEEN,
                PieceType.ROOK,
                PieceType.KNIGHT,
                PieceType.BISHOP,
            )
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                shape = androidx.compose.material3.MaterialTheme.shapes.large
            ) {
                Column {
                    androidx.compose.material3.Text(
                        text = "Promote your pawn to",
                        modifier = Modifier
                            .align(alignment = Alignment.CenterHorizontally)
                            .padding(16.dp),
                        style = androidx.compose.material3.MaterialTheme.typography.headlineSmall
                    )
                    HorizontalDivider(thickness = 4.dp)
                    Row {
                        for (pieceType in canPromoteTO){
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                                    .padding(8.dp, 16.dp, 8.dp, 16.dp)){
                                Image(
                                    painter = painterResource(imageRes(pieceType)),
                                    contentDescription = "queen_b",
                                    modifier = Modifier.clickable(
                                        enabled = true,
                                        onClick = {
                                            game.promote(pieceType);
                                            promote.value = false

                                        }
                                    ))
                            }
                        }
                    }
                }
            }

        }
    }
    if(checkMate.value){
        GameOverMessage(
            onDismissRequest = {
                game.reset()
                selectedSquare.value =
                    Square(
                        row = -1,
                        col = -1,
                        piece = mutableStateOf(Piece(PieceType.None,PieceColor.None))
                    )

                checkMate.value = false
            },
            message = "Game Over"
        )
    }
    if (Stalemate.value){
        GameOverMessage (
            onDismissRequest = {
                game.reset()
                selectedSquare.value =
                    Square(
                        row = -1,
                        col = -1,
                        piece = mutableStateOf(Piece(PieceType.None,PieceColor.None))
                    )

                checkMate.value = false
            },
            message = "Draw"
        )
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameOverMessage(onDismissRequest:()->Unit,message: String) {
    BasicAlertDialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(36.dp)
                .clickable(
                    enabled = true,
                    onClick = onDismissRequest
                ),
            shape = MaterialTheme.shapes.large

        ) {
            Column(
                modifier = Modifier.padding(16.dp)) {
                Text(text = message, modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}

@Composable
fun ChessboardSquare(
    modifier: Modifier = Modifier,
    square: Square,
    onSquareClick: () -> Unit,
    borderColor: Color
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clickable { onSquareClick() }
            .background(
                if ((square.row + square.col) % 2 == 0) Color.LightGray else Color.DarkGray
            )
            .border(
                width = if (borderColor != Color.Black) 4.dp else 1.dp,
                color = borderColor
            )
    ){
        if (square.piece.value.type != PieceType.None) { PieceView(piece = square.piece.value) }
    }
}

fun imageRes(piece: PieceType): Int {
    return  when(piece) {
        PieceType.PAWN -> R.drawable.pawn_b
        PieceType.ROOK ->  R.drawable.rook_b
        PieceType.KNIGHT -> R.drawable.knight_b
        PieceType.BISHOP ->  R.drawable.bishop_b
        PieceType.QUEEN -> R.drawable.queen_b
        PieceType.KING ->  R.drawable.king_b
        else -> {R.drawable.pawn_b}
    }
}

@Composable
fun PieceView(piece: Piece) {
    val imageResource = when (piece.type) {
        PieceType.PAWN -> if (piece.color == PieceColor.WHITE) R.drawable.pawn_w else R.drawable.pawn_b
        PieceType.ROOK -> if (piece.color == PieceColor.WHITE) R.drawable.rook_w else R.drawable.rook_b
        PieceType.KNIGHT -> if (piece.color == PieceColor.WHITE) R.drawable.knight_w else R.drawable.knight_b
        PieceType.BISHOP -> if (piece.color == PieceColor.WHITE) R.drawable.bishop_w else R.drawable.bishop_b
        PieceType.QUEEN -> if (piece.color == PieceColor.WHITE) R.drawable.queen_w else R.drawable.queen_b
        PieceType.KING -> if (piece.color == PieceColor.WHITE) R.drawable.king_w else R.drawable.king_b
        PieceType.None -> R.drawable.ic_launcher_foreground
    }
    Image(painterResource(imageResource), contentDescription = null)
}


@Composable
fun PlayerInfo( Player: PieceColor, knockedpieces: List<Piece>,invitePlayer: (String) -> Unit) {
    var modifier = Modifier
        .fillMaxWidth(1f)
        .border(1.dp, Color.Black)
    Row(
        modifier
    ) {
        Box(
            modifier
                .weight(0.2f)
                .aspectRatio(1f)
                .clickable(
                    enabled = true,
                    onClick = { invitePlayer(Player.toString()[0].toString()) }
                )
        ){
            DisplayPicture(Player)
        }
        Column(
            modifier = Modifier
                .background(Color.DarkGray)
                .weight(0.8f),
        ) {
            Row {
                KnockedOutPieces(Player,knockedpieces,
                    modifier
                        .weight(1f)
                        .aspectRatio(1f),0,7)
            }
            Row {
                KnockedOutPieces(Player,knockedpieces,
                    modifier
                        .weight(1f)
                        .aspectRatio(1f),7,14)
            }
        }

    }


}

@Composable
fun KnockedOutPieces(color: PieceColor,knockedpieces: List<Piece>,modifier: Modifier,startIndex:Int,maxPiece: Int) {
    for (i in startIndex..maxPiece){
        if (i<=knockedpieces.lastIndex){
            println("${knockedpieces.lastIndex<i} ${i} ${knockedpieces.lastIndex} ")
            Box(modifier.background(MaterialTheme.colorScheme.primaryContainer)){
                Icon(painterResource(imageRes(piece = knockedpieces[i].type)), contentDescription = "${knockedpieces[i].type}")
            }
        }
        else{
            Box(modifier.background(MaterialTheme.colorScheme.primaryContainer)){

            }
        }
    }
}

@Composable
fun DisplayPicture(currentPlayer: PieceColor = PieceColor.WHITE,) {
    Surface(
        shape = MaterialTheme.shapes.extraLarge,
        color = if (currentPlayer == PieceColor.WHITE) MaterialTheme.colorScheme.primaryContainer
        else MaterialTheme.colorScheme.secondaryContainer,
    ) {
        Image(
            painter = painterResource(id = R.drawable.pawn_b),
            contentDescription = "$currentPlayer",
            colorFilter = ColorFilter.tint(
                if (currentPlayer == PieceColor.WHITE) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onSecondaryContainer
            )
        )
    }
}

