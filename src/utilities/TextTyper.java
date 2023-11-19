package utilities;

public class TextTyper {

    // Function to type out the given text with a typing effect
    public static void typeText(String text, int delay) {
        try {
            for (int i = 0; i < text.length(); i++) {
                System.out.printf("%c", text.charAt(i));
                // System.out.flush(); // Flush the output stream to ensure immediate display
                Thread.sleep(delay);
            }
            System.out.println(); // Move to the next line after typing the entire text
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
