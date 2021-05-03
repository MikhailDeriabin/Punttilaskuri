package com.example.punttilaskuri;
import android.content.ClipData;
import android.content.ClipboardManager;

/**
 * Simple class to allow Clipboard copying
 * @author Henri Johansson
 */
public class CopyToClipboard {
    /**
     * Copies text given to the Clipboard of given context.
     * @param clipboardObject Clipboard context
     * @param label Label for the ClipData
     * @param text Text Copied to the Clipboard
     */
    public static void copy(Object clipboardObject , String label, String text) {
        ClipboardManager clipboard = (ClipboardManager) clipboardObject;
        ClipData clip = ClipData.newPlainText(label, text);
        clipboard.setPrimaryClip(clip);
    }

}
