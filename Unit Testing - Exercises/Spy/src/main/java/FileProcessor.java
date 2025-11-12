import java.util.*;

/**
 * A file processing system that delegates to specific handlers based on file type.
 * This class demonstrates dependency injection and method delegation - perfect for spy testing.
 */
public class FileProcessor {
    private final ImageHandler imageHandler;
    private final DocumentHandler documentHandler;
    private final AudioHandler audioHandler;
    private final Logger logger;

    public FileProcessor(ImageHandler imageHandler, DocumentHandler documentHandler,
                        AudioHandler audioHandler, Logger logger) {
        this.imageHandler = imageHandler;
        this.documentHandler = documentHandler;
        this.audioHandler = audioHandler;
        this.logger = logger;
    }

    /**
     * Processes a single file by delegating to the appropriate handler
     */
    public ProcessingResult processFile(FileInfo file) {
        logger.info("Starting to process file: " + file.getName());

        try {
            ProcessingResult result;

            switch (file.getType()) {
                case IMAGE:
                    result = imageHandler.process(file);
                    break;
                case DOCUMENT:
                    result = documentHandler.process(file);
                    break;
                case AUDIO:
                    result = audioHandler.process(file);
                    break;
                default:
                    logger.error("Unsupported file type: " + file.getType());
                    return new ProcessingResult(false, "Unsupported file type");
            }

            if (result.isSuccess()) {
                logger.info("Successfully processed file: " + file.getName());
            } else {
                logger.error("Failed to process file: " + file.getName() + " - " + result.getMessage());
            }

            return result;

        } catch (Exception e) {
            logger.error("Exception while processing file: " + file.getName() + " - " + e.getMessage());
            return new ProcessingResult(false, "Processing exception: " + e.getMessage());
        }
    }

    /**
     * Processes multiple files in batch
     */
    public BatchProcessingResult processBatch(List<FileInfo> files) {
        logger.info("Starting batch processing of " + files.size() + " files");

        List<ProcessingResult> results = new ArrayList<>();
        int successCount = 0;

        for (FileInfo file : files) {
            ProcessingResult result = processFile(file);
            results.add(result);
            if (result.isSuccess()) {
                successCount++;
            }
        }

        logger.info("Batch processing completed. Success: " + successCount + "/" + files.size());
        return new BatchProcessingResult(results, successCount, files.size() - successCount);
    }

    /**
     * Validates file before processing
     */
    public boolean validateFile(FileInfo file) {
        if (file == null || file.getName() == null || file.getName().trim().isEmpty()) {
            logger.warn("Invalid file: null or empty name");
            return false;
        }

        if (file.getSize() <= 0) {
            logger.warn("Invalid file size for: " + file.getName());
            return false;
        }

        logger.debug("File validation passed for: " + file.getName());
        return true;
    }
}

// Supporting classes and interfaces
interface ImageHandler {
    ProcessingResult process(FileInfo file);
}

interface DocumentHandler {
    ProcessingResult process(FileInfo file);
}

interface AudioHandler {
    ProcessingResult process(FileInfo file);
}

interface Logger {
    void info(String message);
    void error(String message);
    void warn(String message);
    void debug(String message);
}

class FileInfo {
    private final String name;
    private final FileType type;
    private final long size;

    public FileInfo(String name, FileType type, long size) {
        this.name = name;
        this.type = type;
        this.size = size;
    }

    public String getName() { return name; }
    public FileType getType() { return type; }
    public long getSize() { return size; }
}

enum FileType {
    IMAGE, DOCUMENT, AUDIO
}

class ProcessingResult {
    private final boolean success;
    private final String message;

    public ProcessingResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}

class BatchProcessingResult {
    private final List<ProcessingResult> results;
    private final int successCount;
    private final int failureCount;

    public BatchProcessingResult(List<ProcessingResult> results, int successCount, int failureCount) {
        this.results = results;
        this.successCount = successCount;
        this.failureCount = failureCount;
    }

    public List<ProcessingResult> getResults() { return results; }
    public int getSuccessCount() { return successCount; }
    public int getFailureCount() { return failureCount; }
}
