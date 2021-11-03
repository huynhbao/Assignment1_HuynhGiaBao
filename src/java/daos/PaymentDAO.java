/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import dtos.ProductDTO;
import java.util.ArrayList;
import java.util.List;
import utils.MyConstants;

/**
 *
 * @author HuynhBao
 */
public class PaymentDAO {

    public String authorizePayment(List<ProductDTO> list)
            throws PayPalRESTException {

        Payer payer = getPayerInformation();
        RedirectUrls redirectUrls = getRedirectURLs();
        List<Transaction> listTransaction = getTransactionInformation(list);

        Payment requestPayment = new Payment();
        requestPayment.setTransactions(listTransaction);
        requestPayment.setRedirectUrls(redirectUrls);
        requestPayment.setPayer(payer);
        requestPayment.setIntent("authorize");

        APIContext apiContext = new APIContext(MyConstants.CLIENT_ID, MyConstants.CLIENT_SECRET, MyConstants.MODE);

        Payment approvedPayment = requestPayment.create(apiContext);

        return getApprovalLink(approvedPayment);

    }

    private Payer getPayerInformation() {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        PayerInfo payerInfo = new PayerInfo();
        //payerInfo.setEmail();

        payer.setPayerInfo(payerInfo);

        return payer;
    }

    private RedirectUrls getRedirectURLs() {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(MyConstants.cancelUrl);
        redirectUrls.setReturnUrl(MyConstants.returnUrl);

        return redirectUrls;
    }

    private List<Transaction> getTransactionInformation(List<ProductDTO> list) {
        float total = 0;
        for (ProductDTO product : list) {
            total += product.getPrice() * product.getQuantity();
        }
        Details details = new Details();
        //details.setShipping(String.valueOf(0));
        details.setSubtotal(String.format("%.2f", total));
        //details.setTax(String.valueOf(0));

        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(String.format("%.2f", total));
        amount.setDetails(details);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription("Your order");

        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();

        for (ProductDTO product : list) {
            Item item = new Item();
            item.setCurrency("USD");
            item.setName(product.getName());
            item.setPrice(String.valueOf(product.getPrice()));
            //item.setTax(orderDetail.getTax());
            item.setQuantity(String.valueOf(product.getQuantity()));
            item.setDescription(product.getDescription());

            items.add(item);
        }
        

        itemList.setItems(items);
        transaction.setItemList(itemList);

        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);

        return listTransaction;
    }

    private String getApprovalLink(Payment approvedPayment) {
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;

        for (Links link : links) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                approvalLink = link.getHref();
                break;
            }
        }

        return approvalLink;
    }

    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
        APIContext apiContext = new APIContext(MyConstants.CLIENT_ID, MyConstants.CLIENT_SECRET, MyConstants.MODE);
        return Payment.get(apiContext, paymentId);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        Payment payment = new Payment().setId(paymentId);

        APIContext apiContext = new APIContext(MyConstants.CLIENT_ID, MyConstants.CLIENT_SECRET, MyConstants.MODE);

        return payment.execute(apiContext, paymentExecution);
    }
}
