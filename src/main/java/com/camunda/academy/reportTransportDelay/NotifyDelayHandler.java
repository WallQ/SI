package com.camunda.academy.reportTransportDelay;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.client.api.worker.JobHandler;

public class NotifyDelayHandler implements JobHandler {
    @Override
    public void handle(JobClient client, ActivatedJob job) {
        System.out.println("Processing job " + job.getKey());
        client.newCompleteCommand(job.getKey()).send().join();
    }
}
