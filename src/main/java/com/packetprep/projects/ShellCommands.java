package com.packetprep.projects;


import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class ShellCommands {
//    @ShellMethod (value = "Displaying Shell Method")
//    public String display(String s) {
//        return "Shell Command Executed "+s;
//    }

    public void display() {
        String command = "cd C:\\Users\\Packetprep\\Desktop\\";
        try
        {
            Process process = Runtime.getRuntime().exec(command);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
