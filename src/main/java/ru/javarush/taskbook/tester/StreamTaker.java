package ru.javarush.taskbook.tester;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

/**
 * Created by Alex Ieromenko on 25.01.15.
 * Catch all the JVM output asynchronously
 */
final class StreamTaker implements Callable {
    InputStream is;

    public StreamTaker(InputStream is) {
        this.is = is;
    }

    @Override
    public BufferedReader call() throws Exception {
        BufferedReader br;
//        try {
        InputStreamReader isr = new InputStreamReader(is);
        br = new BufferedReader(isr);
        // left for testing purpose!!!
//            String line;
//            while ((line = br.readLine()) != null)
//                System.out.println(">" + line);
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
        return br;
    }
}

