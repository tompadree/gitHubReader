package com.example.data.cache;

import android.content.Context;

import com.example.data.cache.serializer.JsonSerializer;
import com.example.data.entity.ResultEntity;
import com.example.data.exception.ResultNotFoundException;
import com.example.domain.executor.ThreadExecutor;

import java.io.File;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Tom on 31.10.2016..
 */

public class ResultCacheImpl implements ResultCache{

    private static final String SETTINGS_FILE_NAME = "com.example.tom.SETTINGS";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";

    private static final String DEFAULT_FILE_NAME = "result_";
    private static final long EXPIRATION_TIME = 60 * 10 * 1000;

    private final Context context;
    private final File cacheDir;
    private final JsonSerializer serializer;
    private final FileManager fileManager;
    private final ThreadExecutor threadExecutor;

    /**
     * Constructor of the class {@link ResultCacheImpl}.
     *
     * @param context A
     * @param resultCacheSerializer {@link JsonSerializer} for object serialization.
     * @param fileManager {@link FileManager} for saving serialized objects to the file system.
     */
    @Inject
    public ResultCacheImpl(Context context, JsonSerializer resultCacheSerializer,
                         FileManager fileManager, ThreadExecutor executor) {
        if (context == null || resultCacheSerializer == null || fileManager == null || executor == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        this.context = context.getApplicationContext();
        this.cacheDir = this.context.getCacheDir();
        this.serializer = resultCacheSerializer;
        this.fileManager = fileManager;
        this.threadExecutor = executor;
    }

    @Override public Observable<ResultEntity> get(final String repoName) {
        return Observable.create(subscriber -> {
            File resultEntityFile = ResultCacheImpl.this.buildFile(repoName);
            String fileContent = ResultCacheImpl.this.fileManager.readFileContent(resultEntityFile);
            ResultEntity resultEntity = ResultCacheImpl.this.serializer.deserialize(fileContent);

            if (resultEntity != null) {
                subscriber.onNext(resultEntity);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new ResultNotFoundException());
            }
        });
    }

    @Override public void put(ResultEntity resultEntity) {
        if (resultEntity != null) {
            File resultEntityFile = this.buildFile(resultEntity.getRepoName());
            if (!isCached(resultEntity.getRepoName())) {
                String jsonString = this.serializer.serialize(resultEntity);
                this.executeAsynchronously(new ResultCacheImpl.CacheWriter(this.fileManager, resultEntityFile,
                        jsonString));
                setLastCacheUpdateTimeMillis();
            }
        }
    }

    @Override public boolean isCached(String repoName) {
        File resultEntitiyFile = this.buildFile(repoName);
        return this.fileManager.exists(resultEntitiyFile);
    }

    @Override public boolean isExpired() {
        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

        boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);

        if (expired) {
            this.evictAll();
        }

        return expired;
    }

    @Override public void evictAll() {
        this.executeAsynchronously(new ResultCacheImpl.CacheEvictor(this.fileManager, this.cacheDir));
    }

    /**
     * Build a file, used to be inserted in the disk cache.
     *
     * @param repoName The id result to build the file.
     * @return A valid file.
     */
    private File buildFile(String repoName) {
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(this.cacheDir.getPath());
        fileNameBuilder.append(File.separator);
        fileNameBuilder.append(DEFAULT_FILE_NAME);
        fileNameBuilder.append(repoName);

        return new File(fileNameBuilder.toString());
    }

    /**
     * Set in millis, the last time the cache was accessed.
     */
    private void setLastCacheUpdateTimeMillis() {
        long currentMillis = System.currentTimeMillis();
        this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis);
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private long getLastCacheUpdateTimeMillis() {
        return this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE);
    }

    /**
     * Executes a {@link Runnable} in another Thread.
     *
     * @param runnable {@link Runnable} to execute
     */
    private void executeAsynchronously(Runnable runnable) {
        this.threadExecutor.execute(runnable);
    }

    /**
     * {@link Runnable} class for writing to disk.
     */
    private static class CacheWriter implements Runnable {
        private final FileManager fileManager;
        private final File fileToWrite;
        private final String fileContent;

        CacheWriter(FileManager fileManager, File fileToWrite, String fileContent) {
            this.fileManager = fileManager;
            this.fileToWrite = fileToWrite;
            this.fileContent = fileContent;
        }

        @Override public void run() {
            this.fileManager.writeToFile(fileToWrite, fileContent);
        }
    }

    /**
     * {@link Runnable} class for evicting all the cached files
     */
    private static class CacheEvictor implements Runnable {
        private final FileManager fileManager;
        private final File cacheDir;

        CacheEvictor(FileManager fileManager, File cacheDir) {
            this.fileManager = fileManager;
            this.cacheDir = cacheDir;
        }

        @Override public void run() {
            this.fileManager.clearDirectory(this.cacheDir);
        }
    }

}
