package org.joytojson;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
public class GlobalKeyListener implements NativeKeyListener {
    public void nativeKeyPressed(NativeKeyEvent e) {
        String key = NativeKeyEvent.getKeyText(e.getKeyCode());
        System.out.println("Key Pressed: " + key);
        if (!Main.keyList.has(key)) {
            Main.keyList.put(key,key);
        }


        if (NativeKeyEvent.getKeyText(e.getKeyCode()) == Main.toggleKey) {

            if (Main.toggle) {
                Main.toggle = false;
            }
            else {
                Main.toggle = true;
            }
            System.out.println("JoyToJSON "+ Main.toggle);
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        String key = NativeKeyEvent.getKeyText(e.getKeyCode());
        //System.out.println("Key Released: " + key);

        Main.keyList.remove(key);

    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        //System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
    }
}
