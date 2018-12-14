package server

import client.ClientThread
import java.net.ServerSocket

class Server(var port: Int){

    var connections = ArrayList<ServerConnection>()

    fun listenAsync(){
        Thread {
            var ss = ServerSocket(1337)
            println("Server running")
            while (!ss.isClosed) {

                var s = ss.accept()
                connections.add(ServerConnection(s))
            }
        }.start()
    }

}