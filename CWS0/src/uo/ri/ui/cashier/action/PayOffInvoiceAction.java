package uo.ri.ui.cashier.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.InvoiceService;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.dto.VoucherDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.common.ServiceFactory;
import uo.ri.ui.cashier.Printer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PayOffInvoiceAction implements Action {

    /**
     * Algorithm:
     * <p>
     * - Ask user invoice number
     * - Retrieve invoice info
     * - Display invoice info
     * - Check is unpaid (status <> 'PAID')
     * - List payment methods accepted for the customer
     * - Ask user to type amount to charge in each payment method
     * Check that sum of amounts equals invoice amount
     * - Record payments in the DDBB
     * - Increase total for each payment method
     * - Decrease money available in coupon if any has been redeemed
     * - Finally, mark invoice as paid
     */
    @Override
    public void execute() throws BusinessException {
        Long invoiceNumber;

        //Ask user Invoice number
        invoiceNumber = Console.readLong("Type invoice number ? ");

        //Retrieve invoice info
        InvoiceService is = ServiceFactory.getInvoiceService();
        InvoiceDto i = is.findInvoice(invoiceNumber);

        //Display invoice info
        Printer.printInvoice(i);

        //Check invoice is unpaid
        if (i.status.equals("PAID"))
            throw new BusinessException("The invoice number introduced(" + invoiceNumber + ") is already paid.");

        //Retrieve Payment methods availabe for the invoice
        List<PaymentMeanDto> pms = is.findPayMethodsForInvoice(i.id);
        Printer.printPaymentMeans(pms);

        //Ask user to assign quantity to pay methods.
        Map<Long, Double> pay = new HashMap<>();
        do {
            System.out.printf("Total to pay: %.2f\n", i.total);
            double remaining = i.total - paidUntilNow(pay);
            System.out.printf("Remaining amount to pay: %.2f\n", remaining);
            Long id;
            Optional<PaymentMeanDto> tmp;
            do {
                id = Console.readLong("Type payment mean id: ");
                Long finalId = id;
                tmp = pms.stream().filter(x -> x.id.equals(finalId)).findAny();
            } while (!tmp.isPresent());

            PaymentMeanDto pm = tmp.get();
            double amount;

            if (pm instanceof VoucherDto) {
                do {
                    amount = Console.readDouble("Type amount to assign: ");
                } while (amount > remaining || amount > ((VoucherDto) pm).available);
                pay.put(id, amount);

            } else {
                do {
                    amount = Console.readDouble("Type amount to assign: ");
                } while (amount > remaining);
                pay.put(id, amount);
            }
        } while (!checkTotalPay(pay, i.total));

        is.settleInvoice(i.id, pay);

    }

    private double paidUntilNow(Map<Long, Double> pay) {
        double t = 0;
        for (Long l : pay.keySet())
            t += pay.get(l);
        return t;
    }

    private boolean checkTotalPay(Map<Long, Double> pay, double total) {
        double t = paidUntilNow(pay);
        return t == total;
    }

}
