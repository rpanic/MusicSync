package server

import org.apache.commons.net.ntp.NTPUDPClient
import java.net.InetAddress

fun main(args: Array<String>) {

    var ntpClient = NTPUDPClient()
    ntpClient.defaultTimeout = 10000

    var info = ntpClient.getTime(InetAddress.getByName("clock.psu.edu"))

    print("Local: ${System.currentTimeMillis()} Net: ${info.returnTime}")

}