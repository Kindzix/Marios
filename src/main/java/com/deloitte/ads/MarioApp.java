package com.deloitte.ads;

import java.util.*;

public class MarioApp {
    private static Set<String> marioTypes;
    private final List<Mario> marioList;
    private final Map<String, User> users;
    public MarioApp() {
        marioTypes = new HashSet<>();
        marioTypes.add("Wielki dzięki, za pomoc!");
        marioTypes.add("Super wykonałeś to zadanie!");
        marioTypes.add("Doskonale się spisałeś!");
        marioTypes.add("Gratuluję, świetnie wykonanej pracy!");
        marioTypes.add("Ułatwiło mi to dzień/pracę!");

        marioList = new ArrayList<>();
        users = new HashMap<>();
    }

    public void addMario(Mario mario) {
        marioList.add(mario);
        for (User recipient : mario.getRecipients()) {
            recipient.addMario(mario.getType(), "");
        }
    }

    public void addMarioWithComment(Mario mario, String comment) {
        marioList.add(mario);
        for (User recipient : mario.getRecipients()) {
            recipient.addMario(mario.getType(), comment);
        }
    }

    public List<Mario> getMarioList() {
        return marioList;
    }

    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    public User getUserById(String userId) {
        return users.get(userId);
    }

    public void displayUserMarios(User user) {
        System.out.println("Marios dla użytkownika: " + user.getFullName());
        List<Mario> userMarios = new ArrayList<>();
        for (Mario m : marioList) {
            if (m.getRecipients().contains(user)) {
                userMarios.add(m);
            }
        }
        if (userMarios.isEmpty()) {
            System.out.println("Brak mariosów.");
        } else {
            for (Mario m : userMarios) {
                System.out.println("Mario ID: " + m.getId());
                System.out.println("Rodzaj Mario: " + m.getType());
                for (User recipient : m.getRecipients()) {
                    System.out.println("Komentarz dla " + recipient.getFullName() + ": " + recipient.getReceivedMario().get(m.getType()));
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        MarioApp marioApp = new MarioApp();

        User user1 = new User("1", "Olga", "Przybysz", "olga.przybysz@example.com");
        User user2 = new User("2", "Jagoda", "Rogala", "jagoda.rogala@example.com");
        User user3 = new User("3", "Norbert", "Michalak", "norbert.michalak@example.com");

        marioApp.addUser(user1);
        marioApp.addUser(user2);
        marioApp.addUser(user3);

        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while (choice != 3) {
            System.out.println("Co chcesz zrobić?");
            System.out.println("1. Wyświetl Marios dla użytkownika");
            System.out.println("2. Dodaj nowe Mario dla innego użytkownika");
            System.out.println("3. Wyjdź z aplikacji");

            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("Dostępni użytkownicy:");
                int i = 1;
                Map<Integer, User> userMap = new HashMap<>();
                for (User user : marioApp.users.values()) {
                    userMap.put(i, user);
                    System.out.println(i + ". " + user.getFullName());
                    i++;
                }

                System.out.println("Wybierz numer użytkownika, dla którego chcesz wyświetlić Marios:");
                int selectedUserIndex = scanner.nextInt();
                scanner.nextLine();
                User selectedUser = userMap.get(selectedUserIndex);
                if (selectedUser != null && selectedUserIndex > 0 && selectedUserIndex <= i) {
                    marioApp.displayUserMarios(selectedUser);
                } else {
                    System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
                }

                System.out.println("Naciśnij Enter, aby kontynuować...");
                scanner.nextLine();
            } else if (choice == 2) {
                System.out.println("Dostępni użytkownicy:");
                int i = 1;
                Map<Integer, User> userMap = new HashMap<>();
                for (User user : marioApp.users.values()) {
                    userMap.put(i, user);
                    System.out.println(i + ". " + user.getFullName());
                    i++;
                }

                System.out.println("Wybierz numer nadawcy:");
                int selectedSenderIndex = scanner.nextInt();
                scanner.nextLine();
                User sender = userMap.get(selectedSenderIndex);
                if (selectedSenderIndex > 0 && selectedSenderIndex <= i && sender != null) {
                    System.out.println("Wybierz numery użytkowników oddzielone przecinkami:");
                    String selectedUserIndices = scanner.nextLine();
                    String[] indices = selectedUserIndices.split(",");
                    Set<User> recipients = new HashSet<>();
                    boolean allIndicesValid = true;
                    for (String index : indices) {
                        int userIndex = Integer.parseInt(index.trim());
                            User selectedUser = userMap.get(userIndex);
                            if (selectedUser != null && selectedUser != sender && userIndex > 0 && userIndex <= i) {
                                recipients.add(selectedUser);
                            } else {
                                allIndicesValid = false;
                                break;
                            }
                    }

                    if (allIndicesValid) {
                        System.out.println("Dostępne typy Mario:");
                        i = 1;
                        List<String> marioTypesList = new ArrayList<>(marioTypes);
                        for (String type : marioTypesList) {
                            System.out.println(i + ". " + type);
                            i++;
                        }

                        System.out.println("Wybierz numer typu Mario:");
                        int selectedTypeIndex = scanner.nextInt();
                        scanner.nextLine();
                        if (selectedTypeIndex >= 1 && selectedTypeIndex <= marioTypesList.size()) {
                            String selectedType = marioTypesList.get(selectedTypeIndex - 1);

                            System.out.println("Wprowadź komentarz:");
                            String comment = scanner.nextLine();

                            Mario mario = new Mario("1", sender, recipients, selectedType);
                            marioApp.addMarioWithComment(mario, comment);

                            System.out.println("Mario zostało dodane.");
                        } else {
                            System.out.println("Nieprawidłowy numer typu Mario.");
                        }
                    } else {
                        System.out.println("Nieprawidłowy numer użytkownika. Spróbuj ponownie.");
                    }
                } else {
                    System.out.println("Nieprawidłowy numer nadawcy. Spróbuj ponownie.");
                }
            } else if (choice == 3) {
                System.out.println("Zamykanie aplikacji...");
            } else {
                System.out.println("Nieprawidłowy wybór. Spróbuj ponownie.");
                System.out.println("Naciśnij Enter, aby kontynuować...");
                scanner.nextLine();
            }
        }
    }
}
