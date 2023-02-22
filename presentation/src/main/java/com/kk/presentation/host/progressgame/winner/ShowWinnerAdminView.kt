package com.kk.presentation.host.progressgame.winner

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kk.designsystem.components.KKBox
import com.kk.designsystem.components.KkButton
import com.kk.designsystem.components.KkOrangeTitle
import com.kk.designsystem.components.KkTitle
import com.kk.presentation.R
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun ShowWinnerAdminView(
    navigateToNextRound: () -> Unit,
    viewModel: ShowWinnerAdminViewModel = koinViewModel()
){

    val uiState by viewModel.uiState.collectAsState()

    KKBox{
        Column(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                KkTitle(label = uiState.round.toString() + stringResource(id = R.string.round))
            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7F), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                AnimatedVisibility(visible = uiState.anyWinner) {
                    KkOrangeTitle(label = stringResource(id = R.string.for_to))
                    KkOrangeTitle(label = uiState.winnerName + "!")
                }
                AnimatedVisibility(visible = uiState.noWinner) {
                    KkOrangeTitle(label = stringResource(id = R.string.no_winner))
                }
            }
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom) {
                KkButton(onClick = { viewModel.setEvent(ContractShowWinnerAdmin.Event.NextGame) },
                    label = stringResource(id = R.string.next_button),
                    modifier = Modifier.fillMaxWidth())
            }
        }
    }

    LaunchedEffect(key1 = Unit){
        viewModel.effect.collectLatest { effect ->
            when(effect){
                ContractShowWinnerAdmin.Effect.Navigate -> navigateToNextRound()
            }
        }
    }
}