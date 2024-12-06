package com.camunda.academy.customerSupport;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.worker.JobWorker;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProvider;
import io.camunda.zeebe.client.impl.oauth.OAuthCredentialsProviderBuilder;

import java.time.Duration;

public class App {
    private static final String ZEEBE_ADDRESS = "dfdb8d36-5bf6-4b20-be42-8205ce0805f0.bru-2.zeebe.camunda.io:443";
    private static final String ZEEBE_CLIENT_ID = ".9mWeUsGuNP5gzgzzkA5pFi8tWly4wrN";
    private static final String ZEEBE_CLIENT_SECRET = "53jqL707IFE3xmeUxa3hb2a4gw0MK_eWq3GWJDnqqvqQ0lC~Fcc18NI13bFML.b8";
    private static final String ZEEBE_AUTHORIZATION_SERVER_URL = "https://login.cloud.camunda.io/oauth/token";
    private static final String ZEEBE_TOKEN_AUDIENCE = "zeebe.camunda.io";

    public static void main(String[] args) {
        final OAuthCredentialsProvider credentialsProvider =
                new OAuthCredentialsProviderBuilder()
                        .authorizationServerUrl(ZEEBE_AUTHORIZATION_SERVER_URL)
                        .audience(ZEEBE_TOKEN_AUDIENCE)
                        .clientId(ZEEBE_CLIENT_ID)
                        .clientSecret(ZEEBE_CLIENT_SECRET)
                        .build();

        try (final ZeebeClient client =
                     ZeebeClient.newClientBuilder()
                             .gatewayAddress(ZEEBE_ADDRESS)
                             .credentialsProvider(credentialsProvider)
                             .build()) {

            final JobWorker jobSaveNewTicketProblemWorker =
                    client.newWorker()
                            .jobType("saveNewTicketProblem")
                            .handler(new SaveNewTicketProblemHandler())
                            .timeout(Duration.ofSeconds(10).toMillis())
                            .open();

            final JobWorker jobNotifySupportNewProblemWorker =
                    client.newWorker()
                            .jobType("notifySupportNewProblem")
                            .handler(new NotifySupportNewProblemHandler())
                            .timeout(Duration.ofSeconds(10).toMillis())
                            .open();

            final JobWorker jobNotifyClientTicketRejectedWorker =
                    client.newWorker()
                            .jobType("notifyClientTicketRejected")
                            .handler(new NotifyClientTicketRejectedHandler())
                            .timeout(Duration.ofSeconds(10).toMillis())
                            .open();

            final JobWorker jobNotifyClientTicketFailedWorker =
                    client.newWorker()
                            .jobType("notifyClientTicketFailed")
                            .handler(new NotifyClientTicketFailedHandler())
                            .timeout(Duration.ofSeconds(10).toMillis())
                            .open();

            final JobWorker jobSaveTicketProblemResolutionWorker =
                    client.newWorker()
                            .jobType("saveTicketProblemResolution")
                            .handler(new SaveTicketProblemResolutionHandler())
                            .timeout(Duration.ofSeconds(10).toMillis())
                            .open();

            final JobWorker jobNotifyClientTicketSolvedWorker =
                    client.newWorker()
                            .jobType("notifyClientTicketSolved")
                            .handler(new NotifyClientTicketSolvedHandler())
                            .timeout(Duration.ofSeconds(10).toMillis())
                            .open();

            Thread.sleep(120000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
