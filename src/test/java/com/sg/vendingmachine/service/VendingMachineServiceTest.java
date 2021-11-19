package com.sg.vendingmachine.service;
import com.sg.vendingmachine.dto.Selection;
import java.math.BigDecimal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This is a junit test class to test the functions of the service layer
 * @author Yi Yang
 *
 */
public class VendingMachineServiceTest {
    
    private final VendingMachineService service;
    /**
     * Constructs a new VendingMachineServiceTest object
     */
    public VendingMachineServiceTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        this.service = ctx.getBean("serviceLayer", VendingMachineService.class);  
    }
    /**
     * Tests if inventory is updated
     * @throws Exception
     */
    @Test
    public void testIfPurchaseSelectionIsUpdatingTheInventory() throws Exception {
        int a = service.getSelectionByName("Cookie").getInventory();
        service.purchaseSelection(service.getSelectionByName("Cookie"),service.getSelectionByName("Cookie").getCost());
        assertEquals( (a) ,service.getSelectionByName("Cookie").getInventory());
    }
    /**
     * Tests if a purchase for an item is valid, if insufficient fund is given
     * and no exception is thrown, it'd be a fail.
     * @throws Exception
     */
    
    @Test
    public void testValidatePurchaseForAnItemInInventory() throws Exception {
        try {
            service.validatePurchase(service.getSelectionByName("Cookie"), new BigDecimal ("0.50"));
        fail("Expected VendingMachineInsufficientFundsException was not thrown.");
        } catch (VendingMachineInsufficientFundsException e) {
        }
    }
    /**
     * Tests if exception is thrown for an item with no inventory left
     * @throws Exception
     */
    @Test
    public void testValidatePurchaseForAnItemNotInInventory() throws Exception {
        Selection temp = service.getSelectionByName("Sprite");
        temp.setInventory(0);
        try {
        service.validatePurchase(temp, new BigDecimal ("3.50"));
        fail("Expected VendingMachineNoItemInventoryException was not thrown.");
        } catch (VendingMachineNoItemInventoryException e) {
        }
    }
}