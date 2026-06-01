package demo.customerroute.messages;

import demo.customerroute.tier.TierLookup;
import demo.customerroute.tier.TierNotFoundException;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CamelRouteBuilder extends RouteBuilder {

    private static final String PROCESS_MESSAGE = "direct:process-message";
    private static final String LOGGER_ID = CamelRouteBuilder.class.getName();

    @Value("${inbound.file-uri}")
    private String fileUri;

    @Value("${inbound.jms-uri}")
    private String jmsUri;

    /**
     * Accepts messages from two sources (file inbox and JMS queue) and routes
     * them to {@code CUSTOMER.<TIER>.OUT} based on the customer's tier.
     * <p>
     * If the message contains a tier that does not exist, it is forwarded to
     * {@code TIER.ERROR}. The tier can then be created via a POST request to the REST API
     * before reprocessing the message.
     * <p>
     * Messages are serialized back to JSON before being forwarded to the outbound queue.
     * <p>
     * @throws Exception if the route configuration fails
     */
    @Override
    public void configure() throws Exception {
        //General dead letter queue. If nothing else is specified a message will be forwarded here after 3 failed attempts
        errorHandler(deadLetterChannel("jms:queue:TIER.DEAD.LETTER")
                .maximumRedeliveries(3)
                .redeliveryDelay(1000));

        onException(CustomerNotFoundException.class)
                .handled(true)
                .log(LoggingLevel.ERROR, LOGGER_ID, "Missing customer. Forwarding to error queue.")
                .to("jms:queue:TIER.DEAD.LETTER");

        onException(TierNotFoundException.class)
                .handled(true)
                .log(LoggingLevel.ERROR, LOGGER_ID, "Chosen tier does not exist. Forwarding to tier error queue.")
                .to("jms:queue:TIER.ERROR");

        from(fileUri)
                .routeId("messages-file")
                .to(PROCESS_MESSAGE);

        from(jmsUri)
                .routeId("messages-jms")
                .to(PROCESS_MESSAGE);


        from(PROCESS_MESSAGE)
                .routeId("message-inbox")

                .unmarshal().json(JsonLibrary.Jackson, IncomingMessageDto.class)

                .choice()
                    .when(simple("${body.customer} == null || ${body.customer.trim} == ''"))
                    .throwException(new CustomerNotFoundException())
                .end()

                .setProperty("customer", simple("${body.customer}"))

                .bean(TierLookup.class, "processTier(${body.tier})")

                .setProperty("tier", simple("${body.level}"))

                .log(LoggingLevel.INFO, LOGGER_ID, "Thank you ${exchangeProperty.customer} for being a " +
                        "${exchangeProperty.tier}-customer. Your discount is ${body.discountPercentage}%")

                .marshal().json()

                .toD("jms:queue:CUSTOMER.${exchangeProperty.tier.toUpperCase()}.OUT");

    }
}
