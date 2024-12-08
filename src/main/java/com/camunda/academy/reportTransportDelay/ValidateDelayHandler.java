package com.camunda.academy.reportTransportDelay;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

import java.util.Map;
import java.util.Random;

public class ValidateDelayHandler implements JobHandler {
    @Override
    public void handle(JobClient client, ActivatedJob job) {
        Random rand = new Random();

        int delay = rand.nextInt(6) + 1;

        System.out.println("Running job: " + job.getKey());
        System.out.println("Validating delay...");

        if (delay > 3) {
            System.out.println("The system detected a delay of approximately " + delay + " minutes.");
        } else {
            System.out.println("The system ruled out a possible delay.");
        }

        client
                .newCompleteCommand(job.getKey()).variables(Map.of("delay", (delay > 3)))
                .send()
                .join();
    }
}
