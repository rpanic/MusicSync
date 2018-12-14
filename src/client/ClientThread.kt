package client

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import java.io.File
import javax.sound.sampled.AudioInputStream



class ClientThread (val ss: Socket) : Runnable{

    var reader = BufferedReader(InputStreamReader(ss.getInputStream()))
    var writer = BufferedWriter(OutputStreamWriter(ss.getOutputStream()))
    var clip: Clip? = null

    override fun run() {

        val audioInputStream = AudioSystem.getAudioInputStream(File(client.filepath).absoluteFile)
        clip = AudioSystem.getClip()
        clip!!.open(audioInputStream)

        println("Starting Listening")
        for(line in reader.lines()){

            if(!line.equals("ping") && !line.equals("timestamp"))
                println("Recieved $line")
            when{

                line.equals("ping") -> {
                    writer.write("pingrep\n")
                    writer.flush()
                }

                line.equals("timestamp") -> {
                    writer.write("timestamprep ${System.nanoTime()}")
                    writer.newLine()
                    writer.flush()
                }

                line.startsWith("signal") -> {
                    signal(line.split(" ")[1].toLong())
                }

            }

        }

        println("Ended Listening")

    }

    fun signal(ts: Long){
        Thread{
            var diff = ts - System.currentTimeMillis()
            println("diff $diff")
            Thread.sleep(diff/* / 1000000L*/)
            println("${System.currentTimeMillis()} Signal")

            clip!!.start()
        }.start()
    }

}