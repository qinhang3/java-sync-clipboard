package win.qinhang3.clipboard.thread;

import win.qinhang3.clipboard.SyncClipboard;
import win.qinhang3.clipboard.receiver.DataReceiver;
import win.qinhang3.clipboard.sender.DataSender;
import win.qinhang3.clipboard.util.ClipboardUtil;

import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by hang on 2017/6/14.
 */
public class SendClipboardRunnable implements Runnable {
    private PrintWriter printWriter;

    public SendClipboardRunnable(Socket socket) throws IOException {
        printWriter = new PrintWriter(socket.getOutputStream());
    }

    @Override
    public void run() {
        while(true) {
            synchronized (SyncClipboard.lock) {
                Transferable transferable = ClipboardUtil.getSystemClipboard();
                DataSender dataSener = ClipboardUtil.getDataSener(transferable);

                if (dataSener != null) {
                    String newHash = dataSener.getHash();
                    if (SyncClipboard.clipboardHash == null || !newHash.equals(SyncClipboard.clipboardHash)) {
                        SyncClipboard.clipboardHash = newHash;
                        dataSener.send(printWriter);
                    }
                }
            }
            try {Thread.sleep(1000);} catch (Exception ignore) {}
        }
    }
}
