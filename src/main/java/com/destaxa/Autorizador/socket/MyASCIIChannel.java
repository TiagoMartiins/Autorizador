package com.destaxa.Autorizador.socket;

import org.jpos.iso.ISOPackager;
import org.jpos.iso.channel.ASCIIChannel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MyASCIIChannel extends ASCIIChannel {
    public MyASCIIChannel(ISOPackager packager, Socket socket) throws IOException {
        super(packager);
        connect(socket);
    }
}

