package com.pruebita.kmmpcontrolsystem.ui.screens

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.pruebita.kmmpcontrolsystem.R
import com.pruebita.kmmpcontrolsystem.data.model.ServiceOrder
import com.pruebita.kmmpcontrolsystem.ui.theme.poppins
import com.pruebita.kmmpcontrolsystem.viewmodel.MainPanelViewModel

@Preview(showBackground = true)
@Composable
fun PreviewMainPanelScreen(){
    val navController = rememberNavController()
    val viewModel: MainPanelViewModel = MainPanelViewModel()
    MainPanelScreen(navController,viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPanelScreen(navController: NavHostController,viewModel: MainPanelViewModel){


    val toDoList: MutableList<ServiceOrder> by viewModel.toDoList.observeAsState(initial = mutableListOf())
    val inProcessList: MutableList<ServiceOrder> by viewModel.inProcessList.observeAsState(initial = mutableListOf())
    val doneList: MutableList<ServiceOrder> by viewModel.doneList.observeAsState(initial = mutableListOf())
    val cont: Int by viewModel.cont.observeAsState(initial = 0)

    var showMyDialog by remember {
        mutableStateOf(false)
    }

    MyDialog(
        showMyDialog,{
                op -> viewModel.initOs(op)
            showMyDialog=false
                     },
    ) { newValue ->
        showMyDialog = newValue
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F4FD)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        TopAppBar(
            title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(0.6f),
                            verticalArrangement = Arrangement.Center

                        ) {
                            Text(
                                text = "Panel Principal",
                                style = TextStyle(
                                    fontSize = 30.sp,
                                    lineHeight = 15.sp,
                                    fontFamily = poppins,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF000000),
                                )
                            )
                        }
                        Column(
                            modifier = Modifier.weight(0.4f)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo_kmmp),
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(40.dp)
                            )
                        }
                    }
                    },
            modifier = Modifier.height(120.dp)

        )
        ContentMainPanel(toDoList,inProcessList,doneList, viewModel){ newValue ->
            showMyDialog = newValue
        }
        Text(
            text = "$cont",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,

                )
        )


    }
}

@Composable
fun ContentMainPanel(
    toDoList:MutableList<ServiceOrder>,
    inProcessList:MutableList<ServiceOrder>,
    doneList:MutableList<ServiceOrder>,
    viewModel: MainPanelViewModel,
    onShowMyDialog: (Boolean) -> Unit,

) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(0.3f)
                .background(Color(0xFFFFDCDA))
                .fillMaxHeight()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth().height(65.dp)
                    .background(Color(0xFFF44336)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "TO DO",
                    style = TextStyle(
                        fontSize = 25.sp,
                        lineHeight = 15.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                    )
                )
                IconButton(
                    onClick = {viewModel.addOs() },
                    modifier = Modifier
                        .clip(CircleShape).padding(bottom = 5.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "refresh",
                        tint = Color.White,
                    )

                }

            }
            LazyColumn(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .height(800.dp)
                    .background(Color.Transparent)
            ){
                item {
                    for (i in 0 until toDoList.size){
                        OSFrame(toDoList[i],Color(0xFFF44336),Color(0xFF7A221C),{ viewModel.toDoList?.value?.get(i)?.selected ?: false }){
                            viewModel.changeToDoSelected(
                                i
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                horizontalArrangement = Arrangement.Center
            ){
                StartButton(onShowMyDialog)
            }
        }
        Column(
            modifier = Modifier
                .weight(0.3f)
                .background(Color(0xFFFFF6DA))
                .fillMaxHeight()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth().height(65.dp)
                    .background(Color(0xFFFFC107)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "IN PROCESS",
                    style = TextStyle(
                        fontSize = 25.sp,
                        lineHeight = 15.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                    )
                )
            }
            LazyColumn(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .height(800.dp)
                    .background(Color.Transparent)
            ){
                item {
                    for (i in 0 until inProcessList.size){
                        OSFrame(inProcessList[i],Color(0xFFFFC107),Color(0xFFAC8B2A),{ viewModel.inProcessList?.value?.get(i)?.selected ?: false }){
                            viewModel.changeInProcessSelected(
                                i
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                horizontalArrangement = Arrangement.Center
            ){
                FinishButton { viewModel.finishOs() }
            }

        }
        Column(
            modifier = Modifier
                .weight(0.3f)
                .background(Color(0xFFDAFFE2))
                .fillMaxHeight()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth().height(65.dp)
                    .background(Color(0xFF54B368)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "DONE",
                    style = TextStyle(
                        fontSize = 25.sp,
                        lineHeight = 15.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                    )
                )
            }
            LazyColumn(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxHeight()
                    .background(Color.Transparent)
            ){
                item {
                    for (i in 0 until doneList.size){
                        OSFrame(doneList[i],Color(0xFF54B368),Color(0xFF377C45),{ viewModel.doneList?.value?.get(i)?.selected ?: false }) {
                            viewModel.changeDoneSelected(
                                i
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FinishButton(finishOs:()->Unit) {
    Button(
        onClick = finishOs,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(bottom = 10.dp)
            .width(200.dp)
            .height(70.dp),
        colors =  ButtonDefaults.buttonColors(
            containerColor = Color.Black
        )
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Finalizar",
                style = TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 15.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                )
            )
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "back",
                tint = Color.White,
            )
        }
    }
}

@Composable
fun StartButton(onShowMyDialog: (Boolean) -> Unit) {
    Button(
        onClick = { onShowMyDialog(true) },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(bottom = 10.dp)
            .width(200.dp)
            .height(70.dp),
        colors =  ButtonDefaults.buttonColors(
            containerColor = Color.Black
        )
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Iniciar",
                style = TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 15.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                )
            )
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "back",
                tint = Color.White,
            )
        }
    }
}

@Composable
fun OSFrame(os:ServiceOrder,color: Color,colorSelected:Color,getSelected:()->Boolean, markAsSelected:()->Unit) {
    Button(
        onClick = markAsSelected,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(bottom = 10.dp),
        colors =  ButtonDefaults.buttonColors(
            containerColor = if(!getSelected()) color else colorSelected
        )
    ) {
        Text(
            text = "OS${os.serviceOrderId}",
            style = TextStyle(
                fontSize = 22.sp,
                lineHeight = 15.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                color = Color.White,
            )
        )
    }
}

@Composable
fun MyDialog(
    showMyDialog: Boolean,
    startButton:(String)->Unit,
    onShowMyDialog: (Boolean) -> Unit,

) {
    if (showMyDialog) {
        Dialog(
            onDismissRequest = { onShowMyDialog(false) },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = true
            )
        ) {
            Column(
                Modifier
                    .width(449.dp)
                    .height(300.dp)
                    .background(
                        color = Color(0xFFFFFFFF),
                        shape = RoundedCornerShape(size = 15.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = "Seleccione la etapa",
                    fontSize = 30.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF000000),
                )
                Spacer(modifier = Modifier.padding(10.dp))
                OptionButton("Desarmado",1, startButton)
                Spacer(modifier = Modifier.padding(10.dp))
                OptionButton("Armado",2,startButton)
            }

        }

    }
}

@Composable
fun OptionButton(
   optionTxt:String,
   optionId: Int,
   startButton:(String)->Unit
) {
    ElevatedButton(
        onClick = {
                  startButton(optionTxt)
        },
        modifier = Modifier
            .width(200.dp)
            .height(55.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF0B109E),
            contentColor = Color(0xFFFFFFFF),
            disabledContainerColor = Color(0xFFB3B6C4)

        ), contentPadding = PaddingValues(),
        enabled = true
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Row(
            ) {
                Text(text = optionTxt, fontSize = 22.sp, fontFamily = poppins)

            }

        }
    }
}