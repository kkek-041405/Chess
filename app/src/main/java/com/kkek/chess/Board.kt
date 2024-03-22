package com.kkek.chess

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource


val data  = mutableListOf<String>();
val lightcolor = Color.White
val darkcolor = Color.Black
val chess = Chess()

private val img = mapOf<Peice,Int>(Peice.Rook to R.drawable.king_w,Peice.NONE to R.drawable.king_w)
@Composable
fun Board(){
    Column(
        Modifier.wrapContentSize(
            Alignment.Center
        )
    ) {
        Row (
            Modifier
                .wrapContentWidth(align = Alignment.Start)
                .fillMaxWidth()
        ){

        }
        for (i in 1..8){

            Row {
                for (j in 1..8){
                    val bg =  if ((i+j) % 2 == 0) lightcolor else darkcolor
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .background(bg)
                    ){
                        Text(text = ""+i+j)
                        val x:Int = img.get(chess.pos.get(""+i+j))!!
                        Image(painter = painterResource(id = x), contentDescription = "")
                    };
                }
            }
        }
    }

}


