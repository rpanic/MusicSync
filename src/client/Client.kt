package client

import model.Connection
import java.net.Socket

var filepath = "C:/Users/User/Downloads/Tight.wav"

fun main(args: Array<String>) {

    var add = if(args.size > 0) args[0] else "127.0.0.1"
    var port = if(args.size > 1) args[1].toInt() else 1337
    if(args.size > 2) filepath = args[2]

    var s = Socket(add, port)
    Thread(ClientThread(s)).start()

}