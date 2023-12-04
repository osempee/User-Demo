package com.ingrid.User.Demo.Services;

import com.ingrid.User.Demo.UserDemoApplication;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.util.stream.Collectors;

@Service
public class UserDemoService {
    public String messagesFileLocation = "src/main/resources/static/messages.txt";
    public String logsFileLocation = "src/main/resources/static/log.txt";
    public String logEntryMessage = "New Message Created";

    public String getMessages() {
        String location = messagesFileLocation;

        try (FileReader fileReader = getFileReader(location);
             BufferedReader bufferedReader = new BufferedReader(fileReader);) {
            String messages = bufferedReader.lines().collect(Collectors.joining("\n"));

            return messages;
        } catch (IOException ioException) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not read resource", ioException);
        }
    }

    public long getMessageCount() {
        String location = messagesFileLocation;

        try (FileReader fileReader = getFileReader(location);
             BufferedReader bufferedReader = new BufferedReader(fileReader);) {
            long messageCount = bufferedReader.lines().count();

            return messageCount;
        } catch (IOException ioException) {
            System.out.println(ioException);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not read resource", ioException);
        }
    }

    public void storeMessage(UserDemoApplication.UserDemo.UserMessageDto message) {
        String messagesFileLocation = this.messagesFileLocation;
        String logFileLocation = logsFileLocation;

        try (
                FileWriter messageFileWriter = getFileWriter(messagesFileLocation, true);
                BufferedWriter messagesBufferedWriter = new BufferedWriter(messageFileWriter);
                FileWriter logFileWriter = getFileWriter(logFileLocation,  true);
                BufferedWriter logBufferedWriter = new BufferedWriter(logFileWriter);) {
            messagesBufferedWriter.newLine();
            messagesBufferedWriter.append(message.content);
            logBufferedWriter.newLine();
            logBufferedWriter.append(logEntryMessage);

            return;
        } catch (IOException ioException) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could complete request", ioException);
        }
    }

    public FileReader getFileReader(String location) throws IOException {
        return new FileReader(location);
    }

    public FileWriter getFileWriter(String location, boolean append) throws IOException {
        return new FileWriter(location, append);
    }
}
