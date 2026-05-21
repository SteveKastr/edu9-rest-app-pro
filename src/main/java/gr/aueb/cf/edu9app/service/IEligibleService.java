package gr.aueb.cf.edu9app.service;

import gr.aueb.cf.edu9app.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.edu9app.dto.JobStatusDTO;

public interface IEligibleService {
    void generateReport(String jobId);
    JobStatusDTO getJobStatus(String jobId);
}
