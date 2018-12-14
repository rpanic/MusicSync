package model

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.management.BufferPoolMXBean
import java.net.Socket

val timeForNewPing = 300L

open class Connection(var connection: Socket){

    val reader = BufferedReader(InputStreamReader(connection.getInputStream()))
    val writer = BufferedWriter(OutputStreamWriter(connection.getOutputStream()))

}