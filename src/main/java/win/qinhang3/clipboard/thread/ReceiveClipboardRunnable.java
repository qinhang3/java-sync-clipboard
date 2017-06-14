package win.qinhang3.clipboard.thread;

import win.qinhang3.clipboard.SyncClipboard;
import win.qinhang3.clipboard.receiver.DataReceiver;
import win.qinhang3.clipboard.sender.DataSender;
import win.qinhang3.clipboard.util.ClipboardUtil;

import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by hang on 2017/6/14.
 */
public class ReceiveClipboardRunnable implements Runnable {
    private Scanner scanner;

    public ReceiveClipboardRunnable(Socket socket) throws IOException {
        scanner = new Scanner(socket.getInputStream());
    }

    @Override
    public void run() {
        while(true) {
            DataReceiver dataReceiver = ClipboardUtil.getDataReceiver(scanner);
            if (dataReceiver != null){
                synchronized (SyncClipboard.lock) {
                    SyncClipboard.clipboardHash = dataReceiver.receive();
                }
            }
        }
    }
}
