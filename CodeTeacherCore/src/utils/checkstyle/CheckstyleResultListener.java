package utils.checkstyle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;

import codeteacher.err.Error;
import codeteacher.err.ErrorType;
import codeteacher.result.Performance;

public final class CheckstyleResultListener implements AuditListener {

    private final Logger logger = LoggerFactory.getLogger(CheckstyleResultListener.class);
    private final Performance result;

    public CheckstyleResultListener(String name) {
    	result = new Performance(name);
	}

	@Override
    public void auditStarted(final AuditEvent auditEvent) {

        logger.info("Starting audit...");
    }

    @Override
    public void auditFinished(final AuditEvent auditEvent) {

        logger.info("Audit finished.");
    }

    @Override
    public void fileStarted(final AuditEvent auditEvent) {

        logger.info("Auditing file {}...", auditEvent.getFileName());
    }

    @Override
    public void fileFinished(final AuditEvent auditEvent) {

        logger.info("Auditing file {} finished.", auditEvent.getFileName());
    }

    @Override
    public void addError(final AuditEvent auditEvent) {

        logger.info("Validation error {}: In {} ({}).", auditEvent.getSourceName(),
                                                        auditEvent.getMessage(),
                                                        auditEvent.getFileName(),
                                                        auditEvent.getLine(),
                                                        auditEvent.getColumn());
        StringBuilder sb = new StringBuilder();
        sb
        .append("file: ").append(auditEvent.getFileName())
        .append(" msg: ").append(auditEvent.getMessage())
        .append(" line: ").append(auditEvent.getLine())
        .append(" col: ").append(auditEvent.getColumn());

        Error error = new Error(ErrorType.CODE_STYLE_ERROR, sb.toString());
		result.addError(error );
    }

    @Override
    public void addException(final AuditEvent auditEvent, final Throwable throwable) {

        logger.error("Exception while audit: {}", throwable.getMessage());
    }

    public Performance getResult() {

        return result;
    }
}
