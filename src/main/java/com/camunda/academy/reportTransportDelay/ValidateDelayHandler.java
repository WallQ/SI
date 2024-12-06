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

        int delay = rand.nextInt(10) + 1;

        System.out.println("Processing job " + job.getKey());
        System.out.println("Delay: " + delay);

        client
                .newCompleteCommand(job.getKey())
                .variables(Map.of("delay", (delay > 5)))
                .send()
                .join();
    }
}
