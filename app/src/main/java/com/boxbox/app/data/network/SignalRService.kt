package com.boxbox.app.data.network

import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignalRService @Inject constructor(
    private val baseUrl: String
) {

    private var hubConnection: HubConnection? = null

    @Suppress("CheckResult")
    fun connect(
        hubPath: String,
        onConnected: (() -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        onEvent: ((Any) -> Unit)? = null
    ) {
        val fullUrl = if (hubPath.isNotEmpty()) "$baseUrl/$hubPath" else baseUrl

        hubConnection = HubConnectionBuilder.create(fullUrl).build()

        hubConnection?.on("ReceiveEvent", { data -> onEvent?.invoke(data) }, Any::class.java)

        hubConnection?.start()
            ?.subscribe({
                println("✅ Conectado a $fullUrl")
                onConnected?.invoke()
            }, { error ->
                error.printStackTrace()
                onError?.invoke(error)
            })
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