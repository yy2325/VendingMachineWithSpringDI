package com.sg.vendingmachine.logging;
import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachineAuditDaoImpl;
import com.sg.vendingmachine.dao.exceptions.VendingMachinePersistenceException;
import com.sg.vendingmachine.service.VendingMachineInsufficientFundsException;
import com.sg.vendingmachine.service.VendingMachineNoItemInventoryException;
import org.aspectj.lang.JoinPoint;
/**
 * This class logs and tracks events of the vending machine
 * in case of an insufficient fund event or a no item in inventory event
 * were to occur.
 * @author yiy11
 */
public class Logging {
    VendingMachineAuditDao auditDao;
    int insufficientCount = 0;
    int inventoryCount = 0;
    int count = 0;
    /**
     * Constructs a new Logging object
     * @param auditDao 
     */
    public Logging(VendingMachineAuditDaoImpl auditDao) {
        this.auditDao = auditDao;
    }
    /**
     * Using aspectj to create audit entries.
     * @param jp JoinPoint object
     * @param ex Throwable object
     * @throws VendingMachineInsufficientFundsException
     * @throws VendingMachineNoItemInventoryException 
     */
    public void createAuditEntry(JoinPoint jp, Throwable ex) throws VendingMachineInsufficientFundsException, VendingMachineNoItemInventoryException  {
        if(ex.toString().equals("| No Item In Inventory Exception")){
            count = inventoryCount++; //updates inventory not found events
        }else{
            count = insufficientCount++; //updates insufficient events
        }
        Object[] args = jp.getArgs();
        String auditEntry = jp.getSignature().getName() + " : " + ex.toString() + " (" + count + ") ";
        auditEntry += args[0];
        auditEntry += " | The balance was: $" + args[1];
        
        try {
            auditDao.writeAuditEntry(auditEntry);
        } catch (VendingMachinePersistenceException e) {
            System.err.println(
               "ERROR: Could not create audit entry in LoggingAdvice.");
        }
    }
    
}
