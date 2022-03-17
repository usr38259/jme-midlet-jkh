package jkh;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class jkhMIDlet extends MIDlet implements CommandListener {

    private Command exitCommand;
    private Command calcCommand;
    private Command repeatCommand;
    private Display display;
    private Form form;
    private TextField fP1;
    private TextField fP2;
    private TextField fP3;
    private TextField fP4;
    private TextField fP5;
    private TextField fP6;
    private TextField fP7;
    private String strCaption;
    private TextBox t;
    private static float KK1 = 0.5731242052f;
    private static float KK2 = 0.4268757948f;

    public jkhMIDlet() {
        display = Display.getDisplay(this);
        calcCommand = new Command("Расчёт", Command.OK, 0);
        repeatCommand = new Command("Повтор", Command.BACK, 0);
        exitCommand = new Command("Выход", Command.EXIT, 0);
    }

    public void startApp() {
        strCaption = "Расчёт платы ЖКХ";
        form = new Form (strCaption);
        fP1 = new TextField ("Свет", null, 10, TextField.DECIMAL);
        fP2 = new TextField ("ЕРКЦ", null, 10, TextField.DECIMAL);
        fP3 = new TextField ("УК", null, 10, TextField.DECIMAL);
        fP4 = new TextField ("Водоканал", null, 10, TextField.DECIMAL);
        fP5 = new TextField ("Газ", null, 10, TextField.DECIMAL);
        fP6 = new TextField ("Отопление (ЕРКЦ)", null, 10, TextField.DECIMAL);
        fP7 = new TextField ("Кап. ремонт (ЕРКЦ)", null, 10, TextField.DECIMAL);
        form.append (fP1);
        form.append (fP2);
        form.append (fP3);
        form.append (fP4);
        form.append (fP5);
        form.append (fP6);
        form.append (fP7);
        form.addCommand(calcCommand);
        form.addCommand(exitCommand);
        form.setCommandListener(this);
        t = new TextBox(strCaption, null, 256, TextField.UNEDITABLE);
        t.addCommand(repeatCommand);
        t.addCommand(exitCommand);
        t.setCommandListener(this);
        display.setCurrent(form);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable s) {
        if (c == calcCommand) {
            float P1, P2, P3, P4, P5, P21, P22;
            float P1K, PK1, PK2;
            boolean calcEx = false;
            P1 = parseField (fP1);
            P2 = parseField (fP2);
            P3 = parseField (fP3);
            P4 = parseField (fP4);
            P5 = parseField (fP5);
            P21 = parseField (fP6);
            P22 = parseField (fP7);
            if (fP6.size() != 0 || fP7.size() != 0) calcEx = true;
            P1K = P1 / 3;
            StringBuffer sb = new StringBuffer ();
            sb.append("Свет: ");
            sb.append(P1);
            sb.append("\nЕРКЦ: ");
            sb.append(P2);
            sb.append("\nУК: ");
            sb.append(P3);
            sb.append("\nВодокан.: ");
            sb.append(P4);
            sb.append("\nГаз: ");
            sb.append(P5);
            sb.append("\nСвет с К: ");
            sb.append(P1K);
            sb.append("\nПлата: ");
            sb.append((P2+P3+P4+P5)/2+P1K);
            if (calcEx) {
                PK1 = (P3+P4+P5+P2-P21-P22)/2 + (P21+P22)*KK1 + P1K;
                PK2 = (P3+P4+P5+P2-P21-P22)/2 + (P21+P22)*KK2 + P1K;
                sb.append("\nОтопление: ");
                sb.append(P21);
                sb.append("\nКап.рем.: ");
                sb.append(P22);
                sb.append("\nПлата К1: ");
                sb.append(PK1);
                sb.append("\nПлата К2: ");
                sb.append(PK2);
                sb.append("\nРазница: ");
                sb.append(PK1 - PK2);
            }
            t.setString (sb.toString());
            display.setCurrent(t);
        } else if (c == repeatCommand) {
            display.setCurrent (form);
        } else if (c == exitCommand) {
            destroyApp(false);
            notifyDestroyed();
        }
    }

    protected float parseField (TextField field) {
        if (field.size() != 0) try {
                return Float.parseFloat(field.getString());
            } catch (NumberFormatException e) {
            }
        return 0;
    }

}
