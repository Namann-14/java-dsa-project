public class LibraryManagementSystem {
    public static void main(String[] args) {
        System.out.println("Welcome to Library Management System");
        
        LibraryManager manager = new LibraryManager();
        ConsoleUI ui = new ConsoleUI(manager);
        ui.start();
    }
} 