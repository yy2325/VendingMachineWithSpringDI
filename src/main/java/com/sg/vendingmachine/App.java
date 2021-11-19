package com.sg.vendingmachine;
import com.sg.vendingmachine.controller.VendingMachineController;
import com.sg.vendingmachine.dao.exceptions.VendingMachinePersistenceException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * This class contains the main method that runs the application
 * @author Yi Yang
 *
 */
public class App {
    /**
     * Main method of the program
     * @param args
     * @throws VendingMachinePersistenceException 
     */
    public static void main(String[] args) throws VendingMachinePersistenceException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        VendingMachineController controller = ctx.getBean("controller", VendingMachineController.class);
        controller.run();   
    }
    
}
