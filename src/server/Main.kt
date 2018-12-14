package server

import java.util.*

fun main(args: Array<String>) {

    var s = Server(1337)
    s.listenAsync()

    Thread{client.main(args)}.start()
    Thread{client.main(args)}.start()

    var scanner = Scanner(System.`in`)
    var x = scanner.nextLine()
    while(x != null){
        println("Input: $x")
        if(x.equals("ping")){
            s.connections.forEach{x->println("${x.calculatePing()}")}
        }

        if(x.equals("signal")){

            s.connections.forEach {
                it.calculateTimestampOffset()
            }
            //var maxPing = s.connections.map { it.getPing() }.max()!!
            var maxPing = 500000000

            var timestamp = System.nanoTime() + maxPing
            s.connections.forEach { it.signal(timestamp) }

        }

        x = scanner.nextLine()
    }

}