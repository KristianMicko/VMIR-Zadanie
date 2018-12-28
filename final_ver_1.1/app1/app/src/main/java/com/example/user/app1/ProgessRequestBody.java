package com.example.user.app1;

import android.os.Looper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Handler;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class ProgessRequestBody extends RequestBody {

    private File file;
    private UploadCallBacks listener;
    public static final int DEFAULT_BUFFER_SIZE = 4096;

    public ProgessRequestBody(File file, UploadCallBacks listener) {
        this.file = file;
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse("image/*");
    }

    @Override
    public long contentLength() throws IOException {
        return file.length();
    }


    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        long fileLength = this.file.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream in = new FileInputStream(file);
        long uploaded = 0;
        try {
            int read;
            android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
            while ((read = in.read(buffer)) != -1 ){
                handler.post(new ProgessUpdater(uploaded,fileLength));
                uploaded+= read;
                sink.write(buffer,0,read);

            }
        }finally {
            in.close();
        }

    }

    private class ProgessUpdater implements Runnable {
        private long uploaded;
        private long fileLength;

        public ProgessUpdater(long uploaded, long fileLength) {
            this.uploaded = uploaded;
            this.fileLength = fileLength;
        }

        @Override
        public void run() {
            listener.onProgressUpdate((int)(100*uploaded/fileLength));
        }
    }
}
