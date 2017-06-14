package win.qinhang3.clipboard;

import win.qinhang3.clipboard.thread.ReceiveClipboardRunnable;
import win.qinhang3.clipboard.thread.SendClipboardRunnable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by hang on 2017/6/14.
 */
public class SyncClipboard{

    private ServerSocket serverSocket;
    private static Integer port = 22089;

    public static String clipboardHash = null;
    public static final Object lock = new Object();


    public static void main(String[] args) throws IOException, InterruptedException {
        SyncClipboard syncClipboard = new SyncClipboard();
        String serverAddress = System.getProperty("serverAddress");
        if (serverAddress != null){
            syncClipboard.initClient(serverAddress);
        } else {
            syncClipboard.initServer();
        }

        Thread.sleep(10000000000L);
    }

    private void initServer() throws IOException {
        serverSocket = new ServerSocket(port);
        Socket socket;
        socket = serverSocket.accept();
        syncStart(socket);
    }

    public void initClient(String server) throws IOException {
        Socket socket = new Socket(server, port);
        syncStart(socket);
    }

    private void syncStart(final Socket socket) throws IOException {
        new Thread(new SendClipboardRunnable(socket)).start();
        new Thread(new ReceiveClipboardRunnable(socket)).start();
    }
}
