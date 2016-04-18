package org.javatigers.spbatch.holiday.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.RepeatListener;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.stereotype.Component;

@Component
public class TraceListener implements JobExecutionListener, StepExecutionListener, ChunkListener, RepeatListener, ItemReadListener<Object>,
        ItemProcessListener<Object, Object>, RetryListener, ItemWriteListener<Object>, SkipListener<Object, Object> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onSkipInRead(Throwable t) {
        logger.warn("onSkipInRead", t);
    }

    @Override
    public void onSkipInWrite(Object item, Throwable t) {
        if (logger.isWarnEnabled()) {
            // Note, this is the only method that gives a stacktrace
            logger.warn(String.format("onSkipInWrite %s", item), t);
        }
    }

    @Override
    public void onSkipInProcess(Object item, Throwable t) {
        if (logger.isWarnEnabled()) {
            // Note, this is the only method that gives a stacktrace
            logger.warn(String.format("onSkipInProcess %s", item), t);
        }
    }

    @Override
    public void beforeWrite(List<? extends Object> items) {
        logger.trace("beforeWrite listSize={}, {}", items.size(), items);
    }

    @Override
    public void afterWrite(List<? extends Object> items) {
        logger.trace("afterWrite listSize={}, {}", items.size(), items);
    }

    @Override
    public void onWriteError(Exception exception, List<? extends Object> items) {
        if (logger.isErrorEnabled()) {
            // Note, this is the only method that gives a stacktrace
            logger.error(String.format("onWriteError listSize=%s, %s", items.size(), items), exception);
        }
    }

    @Override
    public <T> void close(RetryContext context, RetryCallback<T> callback, Throwable throwable) {
        if (logger.isTraceEnabled()) {
            // Note, this is the only method that gives a stacktrace
            logger.trace(String.format("Retry close %s, %s", context, callback), throwable);
        }
    }

    @Override
    public <T> void onError(RetryContext context, RetryCallback<T> callback, Throwable throwable) {
        if (logger.isErrorEnabled()) {
            // Note, this is the only method that gives a stacktrace
            logger.error(String.format("Retry onError %s, %s", context, callback), throwable);
        }
    }

    @Override
    public <T> boolean open(RetryContext context, RetryCallback<T> callback) {
        logger.trace("Retry open {}, {}", context, callback);
        return false;
    }

    @Override
    public void beforeProcess(Object item) {
        logger.trace("beforeProcess {}", item);
    }

    @Override
    public void afterProcess(Object item, Object result) {
        logger.trace("afterProcess {} {}", item, result);
    }

    @Override
    public void onProcessError(Object item, Exception e) {
        if (logger.isErrorEnabled()) {
            // Note, this is the only method that gives a stacktrace
            logger.error(String.format("onProcessError %s", item), e);
        }
    }

    @Override
    public void beforeRead() {
        logger.trace("beforeRead");
    }

    @Override
    public void afterRead(Object item) {
        logger.trace("afterRead {}", item);
    }

    @Override
    public void onReadError(Exception ex) {
        logger.error("onReadError", ex);
    }

    @Override
    public void after(RepeatContext context, RepeatStatus result) {
        logger.trace("after {} {}", context, result);
    }

    @Override
    public void before(RepeatContext context) {
        logger.trace("before {}", context);
    }

    @Override
    public void close(RepeatContext context) {
        logger.trace("close {}", context);
    }

    @Override
    public void onError(RepeatContext context, Throwable throwable) {
        if (logger.isErrorEnabled()) {
            // Note, this is the only method that gives a stacktrace
            logger.error(String.format("onError %s", context), throwable);
        }
    }

    @Override
    public void open(RepeatContext context) {
        logger.trace("open {}", context);
    }

    /*
     * @Override public void beforeChunk( ChunkContext context ) { logger.trace( "beforeChunk {}", context ); }
     * 
     * @Override public void afterChunk( ChunkContext context ) { logger.trace( "afterChunk {}", context ); }
     * 
     * @Override public void afterChunkError( ChunkContext context ) { logger.trace( "afterChunkError {}", context ); }
     */

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.trace("beforeStep {}", stepExecution);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.trace("afterStep {}", stepExecution);
        return null;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.trace("beforeJob {}", jobExecution);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.trace("afterJob {}", jobExecution);
    }

	@Override
	public void beforeChunk(ChunkContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterChunk(ChunkContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterChunkError(ChunkContext context) {
		// TODO Auto-generated method stub
		
	}

}

