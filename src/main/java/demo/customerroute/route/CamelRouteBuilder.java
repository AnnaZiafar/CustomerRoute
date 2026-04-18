package demo.customerroute.route;

import demo.customerroute.customer.CustomerInfo;
import demo.customerroute.customer.CustomerLookup;
import demo.customerroute.exception.CustomerNotFoundException;
import demo.customerroute.exception.TierNotFoundException;
import demo.customerroute.tier.TierLookup;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class CamelRouteBuilder extends RouteBuilder {

    String loggerId = getClass().getName();

    @Override
    public void configure() throws Exception {

        onException(TierNotFoundException.class)
                .handled(true)
                .log(LoggingLevel.ERROR, loggerId, "Invalid Tier. Save tier with a valid discount before proceeding with customer.")
                .to("jms:queue:CUSTOMER.TIER.ERROR");

        onException(CustomerNotFoundException.class)
                .handled(true)
                .log(LoggingLevel.ERROR, loggerId, "Incoming message is missing required field 'customer'")
                .to("jms:queue:CUSTOMER.ERROR");


        from("{{inbound.endpoint-uri}}")
                .routeId("messages-inbox")

                .unmarshal().json(JsonLibrary.Jackson, CustomerInfo.class)

                //If field 'customer' is missing stop the current flow and send message to CUSTOMER.ERROR
                .choice()
                    .when(simple("${body.customer} == null || ${body.customer.trim} == ''"))
                    .throwException(new CustomerNotFoundException())
                .end()

                .bean(CustomerLookup.class, "processCustomer")

                .setProperty("discount", method(TierLookup.class, "getDiscountPercentage(${body.tier})"))

                .log(LoggingLevel.INFO, loggerId, "Thank you ${body.customer} for being a ${body.tier}-customer. " +
                        "Your discount is ${exchangeProperty.discount}%")

                .toD("jms:queue:CUSTOMER.${body.tier.toUpperCase()}.OUT");

    }
}
