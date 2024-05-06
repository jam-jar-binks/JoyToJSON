package org.joytojson;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import static java.awt.event.KeyEvent.*;
import static java.lang.Thread.sleep;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;


import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;
import org.json.*;
public class Main {
    static boolean toggle;
    static String toggleKey;
    static JSONObject inputList = new JSONObject();
    static JSONObject keyList = new JSONObject();
    static JSONObject axisList = new JSONObject();
    static JSONObject hatList = new JSONObject();

    public static void log(String text) {
        System.out.println(text);
    }

    public static void readJoystickEvents() {
        //while (true) {
            /* Get the available controllers */
            Controller[] controllers = ControllerEnvironment
                    .getDefaultEnvironment().getControllers();
            if (controllers.length == 0) {
                System.out.println("Found no controllers.");
                System.exit(0);
            }

            for (int i = 0; i < controllers.length; i++) {
                /* Remember to poll each one */
                controllers[i].poll();

                /* Get the controllers event queue */
                EventQueue queue = controllers[i].getEventQueue();

                /* Create an event object for the underlying plugin to populate */
                Event event = new Event();

                /* For each object in the queue */
                while (queue.getNextEvent(event)) {

                    /*
                     * Create a string buffer and put in it, the controller name,
                     * the time stamp of the event, the name of the component
                     * that changed and the new value.
                     *
                     * Note that the timestamp is a relative thing, not
                     * absolute, we can tell what order events happened in
                     * across controllers this way. We can not use it to tell
                     * exactly *when* an event happened just the order.
                     */

                    if (Main.axisList.has( controllers[i].getName())) {
                        JSONObject temp = Main.axisList.getJSONObject(controllers[i].getName());
                        temp.put(event.getComponent().toString(),event.getValue());
                        Main.axisList.put(controllers[i].getName(),temp);

                    }else {
                        Main.axisList.put(controllers[i].getName(),new JSONObject().put(event.getComponent().toString(),event.getValue()));
                    }

                    // Main.axisList.put(controllers[i].getName(),"");

                        /*new JSONObject().put(event.getComponent().toString(),event.getValue())

                        controller[i].getName()[event.getComponent()] = event.getValue()
                        {
                        controllers[i].getName() = {
                            event.getComponent()=event.getValue(),
                            event.getComponent()=event.getValue()

                            }
                        }

                         */

                    //StringBuffer buffer = new StringBuffer(controllers[i]
                    //        .getName());
                    //buffer.append(" at ");
                    //buffer.append(event.getNanos()).append(", ");
                    //Component comp = event.getComponent();
                    //buffer.append(comp.getName()).append(" changed to ");
                    //float value = event.getValue();

                    /*
                     * Check the type of the component and display an
                     * appropriate value
                     */
                    // if (comp.isAnalog()) {
                    //buffer.append(value);
                    //} else {
                    //if (value == 1.0f) {
                    //buffer.append("On");
                    //} else {
                    //buffer.append("Off");
                    //}
                    //}
                    //System.out.println(buffer.toString());
                }
            }

            /*
             * Sleep for 20 milliseconds, in here only so the example doesn't
             * thrash the system.
             */
            //try {
            //    Thread.sleep(50);
            //} catch (InterruptedException e) {
                // TODO Auto-generated catch block
            //    e.printStackTrace();
            //}
        //}
    }
    public static void main(String[] args) throws AWTException, InterruptedException {
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new GlobalKeyListener());

        System.out.println("Hotkey Listener Started");
        Main.toggle = false;
        Main.toggleKey = "Scroll Lock";




        while (true) {
            //log(""+Main.toggle);
            if (Main.toggle) {

                readJoystickEvents();
                //log("test");

                inputList.clear();
                //inputList.put("i","i");
                inputList.put("keyList",keyList);
                inputList.put("axisList", axisList);
                //inputList.put("hatList", hatList);
                copy(inputList.toString());
                paste();
                sleep(50);


            } else {
                sleep(50);
            }

        }
        //while true do if selected app focused copy(joystick data as JSON) paste()
    }
    public static void copy(String text) throws InterruptedException {
        Clipboard clipboard = getSystemClipboard();
        try{
            clipboard.setContents(new StringSelection(text), null);
        }
        catch (IllegalStateException e){
            System.out.println(e);

            sleep(500);
        }

    }
    public static void paste() throws AWTException
    {
        Robot robot = new Robot();

        robot.keyPress(VK_CONTROL);
        robot.keyPress(VK_V);
        robot.keyRelease(VK_CONTROL);
        robot.keyRelease(VK_V);
    }

    private static Clipboard getSystemClipboard()
    {
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        return defaultToolkit.getSystemClipboard();
    }


}