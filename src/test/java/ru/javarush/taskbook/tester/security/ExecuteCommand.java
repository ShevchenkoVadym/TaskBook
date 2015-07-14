package ru.javarush.taskbook.tester.security;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * User: blacky
 * Date: 29.03.15
 */
public class ExecuteCommand {
    public static void main(String[] args) throws Exception {
        // Execute a command 'ls -la': list all files in current directory
        Process proc = Runtime.getRuntime().exec( "ls -la" );

        proc.waitFor();

        InputStream is = proc.getInputStream();
        BufferedReader buf = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String str;
        str = buf.readLine();

        while (str != null)
        {
            System.out.println(str);
            str = buf.readLine();
        }

    }
}

/*
// Я никогда не буду работать за копейки

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Solution {

	 public static void main(String[] args) throws Exception {
        // Execute a command 'ls -la': list all files in current directory
        Process proc = Runtime.getRuntime().exec( "ls -la" );

        proc.waitFor();

        InputStream is = proc.getInputStream();
        BufferedReader buf = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String str;
        str = buf.readLine();

        while (str != null)
        {
            System.out.println(str);
            str = buf.readLine();
        }

	 }
 }
 */