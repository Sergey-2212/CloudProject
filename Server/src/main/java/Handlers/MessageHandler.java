package Handlers;

import Commands.Command;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class MessageHandler extends SimpleChannelInboundHandler<AbstractMessage> {
    private Path currentDir;
    public MessageHandler(Path currentDir){
        this.currentDir = currentDir;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("Connection isteblished.");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete");
        ctx.flush();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.debug("Connection closed.");
    }


//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println("channelReadMessage");
//    }
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, AbstractMessage msg) throws Exception {
        System.out.println("MessageReceivedMethod");
        log.debug("Received: {}", msg);
        if(msg instanceof CommandMessage) {
            processCommandMessage(ctx,msg);
        }



    }

    private void processCommandMessage(ChannelHandlerContext ctx, AbstractMessage msg) {
        CommandMessage cms = (CommandMessage) msg;
        Command command = cms.getCommand();
        switch (command) {
            case DELETE_FILE -> deleteFile(ctx, cms.getCurrentFileName().toPath());
            case CREATE_DIRECTORY -> createDir(ctx, cms.getCurrentFileName().toPath());
            case RENAME_DIRECTORY -> renameDir(ctx, cms.getCurrentFileName().toPath(), cms.getNewFileName().toPath());
            case DELETE_DIRECTORY -> deleteDir(ctx, cms.getCurrentFileName().toPath());
            case RENAME_FILE -> renameFile(ctx, cms.getCurrentFileName().toPath(), cms.getNewFileName().toPath());
        }
    }

    private void renameFile(ChannelHandlerContext ctx, Path currentName, Path newName) {
        Path before = currentDir.resolve(currentName);
        Path after = currentDir.resolve(newName);
        boolean result = before.toFile().renameTo(after.toFile()); //не нашел подходящий метод у класса Files
        if(result) {
            log.debug("File: " + currentName + " successfully renamed to the - " + newName);
        } else {
            log.debug("File: " + currentName + " was not renamed.");
        }
    }

    private void deleteDir(ChannelHandlerContext ctx, Path path) {
        boolean result = false;
        try {
            result = Files.deleteIfExists(currentDir.resolve(path));
        } catch (IOException e) {
            log.error("deleteDir executed " + "isExisted = " + result);
        }

    }

    private void renameDir(ChannelHandlerContext ctx, Path currentName, Path newName) {
        Path before = currentDir.resolve(currentName);
        Path after = currentDir.resolve(newName);
        boolean result = before.toFile().renameTo(after.toFile()); //не нашел подходящий метод у класса Files
        if(result) {
            log.debug("Dir: " + currentName + " successfully renamed to the - " + newName);
        } else {
            log.debug("Dir: " + currentName + " was not renamed.");
        }
    }

    private void createDir(ChannelHandlerContext ctx, Path path) {
        Path dir = path;
        try {
            log.debug("createDir");
            System.out.println(dir);
            System.out.println(currentDir.resolve(dir));
            Files.createDirectory(currentDir.resolve(dir));
        } catch (IOException e) {
            log.error("Mkdir error. {}", e);
        }
    }

    private void deleteFile(ChannelHandlerContext ctx, Path path) {
        log.debug("deleteFileStarted");
        Path file = currentDir.resolve(path);
        System.out.println(file);
        try {
            Files.delete(file);
        } catch (IOException e) {
            log.error("Deleting file error: {}", e);
        }
    }
}
