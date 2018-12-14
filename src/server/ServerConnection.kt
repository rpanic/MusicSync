package server

import model.Connection
import model.timeForNewPing
import java.net.Socket

class ServerConnection(var socket: Socket) : Connection(socket) {

    private var ping : Long = 0
    private var lastPing: Long = 0L

    private var offset: Long = 0
    private var lastOffset: Long = 0L

    /*fun getPing() : Long{
        if(lastPing == 0L || System.currentTimeMillis() - lastPing > timeForNewPing){
            ping = calculatePing()
            lastPing = System.currentTimeMillis()
        }
        return ping
    }*/

    fun calculatePing(): Long {

        var times = 1000
        var sum = 0L
        for(i in 0..times){
            sum += ping()
        }
        println("Averaged Ping: ${sum / times}")
        return sum / times

    }

    private fun ping(): Long{
        writer.write("ping\n")
        writer.flush()
        var start = System.nanoTime()
        var read = reader.readLine()
        var end = System.nanoTime()
        if(read.equals("pingrep")){
            //println("Ping: " + (end-start))
            return end - start
        }
        return Long.MAX_VALUE
    }


    fun calculateTimestampOffset() : Long{

        if(lastOffset == 0L || System.currentTimeMillis() - lastOffset > timeForNewPing){
            getTimestampOffset()
            getTimestampOffset()
            offset = getTimestampOffset()
            lastOffset = System.currentTimeMillis()
        }
        println("Offset: $offset")
        return offset

    }

    private fun getTimestampOffset() : Long{

        writer.write("timestamp")
        writer.newLine()
        var start = System.nanoTime()
        writer.flush()

        var read = reader.readLine()
        var ping = System.nanoTime() - start

        println("ping: $ping")

        var own = System.nanoTime() + (ping / 2L)

        if(read.startsWith("timestamprep")){
            var other = read.split(" ")[1].toLong()

            return other - own;

        }
        return Long.MIN_VALUE

    }

    fun signal(ts: Long) {

        println("Signal $ts $offset")

        var msg = "signal " + (ts + offset) + "\n";
        writer.write(msg)
        writer.flush()

    }

}