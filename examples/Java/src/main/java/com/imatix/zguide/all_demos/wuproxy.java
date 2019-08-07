package com.imatix.zguide.all_demos;


import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;
import org.zeromq.ZContext;

/**
* Weather proxy device.
*/
public class wuproxy
{
    public static void main(String[] args)
    {
        //  Prepare our context and sockets
        try (ZContext context = new ZContext()) {
            //  This is where the weather server sits
            Socket frontend = context.createSocket(ZMQ.SUB);
            frontend.connect("tcp://192.168.55.210:5556");

            //  This is our public endpoint for subscribers
            Socket backend = context.createSocket(ZMQ.PUB);
            backend.bind("tcp://10.1.1.0:8100");

            //  Subscribe on everything
            frontend.subscribe(ZMQ.SUBSCRIPTION_ALL);

            //  Run the proxy until the user interrupts us
            ZMQ.proxy(frontend, backend, null);
        }
    }
}
