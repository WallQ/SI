package com.camunda.academy.customerSupport;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

public class NotifySupportNewProblemHandler implements JobHandler {
    @Override
    public void handle(JobClient client, ActivatedJob job) {
        System.out.println("Running job: " + job.getKey());
        System.out.println("Notifying support about new problem...");
        client.newCompleteCommand(job.getKey()).send().join();
    }
}
