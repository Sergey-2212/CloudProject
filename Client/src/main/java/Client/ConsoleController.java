package Client;

import Handlers.CommandMessage;
import Net.NetClient;

import java.io.File;
import java.nio.file.Path;
import java.util.Scanner;

import static Commands.Command.*;

public class ConsoleController {

    public static void main(String[] args) {

//        NettyNet net = new NettyNet(msg ->
//        {if(msg instanceof FileMessage) {
//            //TODO
//            }
//
//        if(msg instanceof CommandMessage) {
//           //TODO
//        }
//        });
//        Scanner scanner = new Scanner(System.in);
//        while(scanner.hasNextLine()) {
//            String msg = scanner.nextLine();
       //     net.sendCommand(new CommandMessage(DELETE_FILE, Path.of("1.txt")));
        NetClient net = new NetClient(System.out::println);
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            //net.sendCommand(new CommandMessage(CREATE_DIRECTORY, new File("createDir")));
            //net.sendCommand(new CommandMessage(DELETE_FILE, new File("1.txt")));
            //net.sendCommand(new CommandMessage(DELETE_DIRECTORY, new File("TestDelete")));
            //net.sendCommand(new CommandMessage(RENAME_FILE, new File("1.txt"), new File("2.txt")));
            net.sendCommand(new CommandMessage(RENAME_DIRECTORY, new File("TestRename"), new File("TestWasRenamed")));

        }
    }
}



