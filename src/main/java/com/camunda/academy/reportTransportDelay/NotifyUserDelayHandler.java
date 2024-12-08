package com.camunda.academy.reportTransportDelay;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

public class NotifyUserDelayHandler implements JobHandler {
    @Override
    public void handle(JobClient client, ActivatedJob job) {
        System.out.println("Running job: " + job.getKey());
        System.out.println("Notifying user about possible delay in transport...");
        client.newCompleteCommand(job.getKey()).send().join();
    }
}
