package Handlers;

import Commands.Command;
import lombok.AllArgsConstructor;

import java.io.File;
import java.nio.file.Path;
@AllArgsConstructor
public class CommandMessage extends AbstractMessage {
        private Command command;
        private File currentFileName;
        private File newFileName;

        public CommandMessage(Command command, File currentFileName) {
                this.command = command;
                this.currentFileName = currentFileName;
        }


}
