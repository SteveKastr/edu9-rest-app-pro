package gr.aueb.cf.edu9app.repository;

import gr.aueb.cf.edu9app.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
