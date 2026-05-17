package src.university;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MessageService {

    private static MessageService instance;
    private final List<Message> allMessages = new ArrayList<>();

    private MessageService() {}

    public static MessageService getInstance() {
        if (instance == null) instance = new MessageService();
        return instance;
    }

    public Message send(User sender, User receiver, String text) {
        Message msg = new Message(sender, receiver, text);
        allMessages.add(msg);
        receiver.update("MESSAGE", "From " + sender.getName() + ": " + text);
        Logger.log(sender.getLogin() + " sent message to " + receiver.getLogin());
        return msg;
    }

    public List<Message> getInbox(User user) {
        return allMessages.stream()
                .filter(m -> m.getReceiver().equals(user))
                .collect(Collectors.toList());
    }

    public List<Message> getUnread(User user) {
        return getInbox(user).stream()
                .filter(m -> !m.isRead())
                .collect(Collectors.toList());
    }

    public List<Message> getSent(User user) {
        return allMessages.stream()
                .filter(m -> m.getSender().equals(user))
                .collect(Collectors.toList());
    }

    public void printInbox(User user) {
        List<Message> inbox = getInbox(user);
        System.out.println("=== Inbox of " + user.getName() + " (" + inbox.size() + " messages) ===");
        if (inbox.isEmpty()) { System.out.println("  Empty."); return; }
        inbox.forEach(m -> { System.out.println("  " + m); m.markAsRead(); });
    }

    public void printUnread(User user) {
        List<Message> unread = getUnread(user);
        System.out.println("=== Unread messages for " + user.getName() + " ===");
        if (unread.isEmpty()) { System.out.println("  No unread messages."); return; }
        unread.forEach(m -> { System.out.println("  " + m); m.markAsRead(); });
    }
}
