package Handlers;

import Commands.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.File;

@Data
@EqualsAndHashCode(callSuper = false)
public class CommandMessage extends AbstractMessage{
    private Command command;
    private File currentFileName;
    private File newFileName;

    public CommandMessage(Command command, File currentFileName) {
        this.command = command;
        this.currentFileName = currentFileName;
    }
}

