package view.command;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Scanner;

public class SetDisplayCommand extends Command {
    private final AtomicBoolean displayFlag;
    public SetDisplayCommand(String key, String desc, AtomicBoolean displayFlag){
        super(key, desc);
        this.displayFlag = displayFlag;
    }
    @Override
    public void execute() {
        System.out.println("Display:\n1. On\n2. Off");
        System.out.print("Choose 1/2: ");
        Scanner sc = new Scanner(System.in);
        int option = sc.nextInt();
        switch (option) {
            case 1-> {
                displayFlag.set(true);
                System.out.println("Display is on");
            }
            case 2-> {
                displayFlag.set(false);
                System.out.println("Display is off");
            }
            default->{
                System.out.println("Invalid option");
                displayFlag.set(false);
            }
        }
    }
}
