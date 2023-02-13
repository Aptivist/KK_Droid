package com.kk.data

import android.util.Log
import com.kk.data.model.BaseResponse
import com.kk.domain.models.BaseResult
import com.kk.network.di.BaseUrl
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive

class SocketServiceImp(private val httpClient: HttpClient) : ISocketService {
    private var socket: WebSocketSession? = null

    override suspend fun connectSocket(userType: UserType): BaseResult<String> {
        return try {
            socket = httpClient.webSocketSession {
                url(
                    when (userType) {
                        UserType.HostType -> BaseUrl.urlHost
                        UserType.PlayerType -> BaseUrl.urlPlayer
                    }
                )
            }

            if (socket?.isActive == true){
                BaseResult.Success("Connected")
            }else BaseResult.Error(NotActiveException())


        } catch (e: Exception) {
            BaseResult.Error(e)
        }

    }

    override suspend fun requestSocket(request: String) {
        socket?.send(request)
    }

    override fun receiveData(): Flow<BaseResponse<String>> {
        return try {
            socket?.incoming?.receiveAsFlow()
                ?.map {
                    BaseResponse(data = (it as Frame.Text).readText(), status = "")
                }?.onEach {
                    Log.e("Socket", it.toString() + "1")
                } ?: flow {
                emit(BaseResponse(data = "Nada", status = ""))
            }
        } catch (e: Exception) {
            flow {
                emit(BaseResponse(data = "Error", status = ""))
            }
        }
    }

    override suspend fun closeSocket() {
        socket?.close(CloseReason(code = CloseReason.Codes.NORMAL, "Closed"))
    }
}


sealed interface UserType {
    object HostType : UserType
    object PlayerType : UserType
}


class NotActiveException(): Exception("Error connection")