package com.boxbox.app.data.network

import com.boxbox.app.domain.model.Post
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignalRService @Inject constructor(
    private val baseUrl: String
) {

    private var hubConnection: HubConnection? = null

    fun connect(
        hubPath: String,
        conversationId: Int,
        onError: ((Throwable) -> Unit),
        onNewPost: (Post) -> Unit,
    ) {
        val fullUrl = if (hubPath.isNotEmpty()) "$baseUrl/$hubPath" else baseUrl

        hubConnection = HubConnectionBuilder.create(fullUrl).build()

        hubConnection?.on("NewPost",
            { post -> onNewPost(post) },
            Post::class.java)

        hubConnection?.start()
            ?.doOnComplete {
                hubConnection?.send("JoinConversation", conversationId)
            }
            ?.doOnError { onError(it) }
            ?.subscribe()
    }

    fun <T> subscribe(eventName: String, clazz: Class<T>, onEvent: (T) -> Unit) {
        hubConnection?.on(eventName, { data -> onEvent(data) }, clazz)
    }

    fun send(methodName: String, vararg args: Any) {
        hubConnection?.send(methodName, *args)
    }

    fun disconnect() {
        hubConnection?.let {
            if (it.connectionState.name == "CONNECTED") {
                it.stop()
                println("🛑 Desconectado de SignalR")
            }
        }
    }
}