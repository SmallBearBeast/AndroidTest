package com.example.administrator.androidtest.Fresco;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import com.example.administrator.androidtest.Fresco.Listener.*;
import com.example.libbase.Util.BitmapUtil;
import com.example.libbase.Util.FileUtil;
import com.example.libbase.Util.MainThreadUtil;
import com.example.libbase.Util.ThreadUtil;
import com.facebook.cache.common.CacheErrorLogger;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.internal.Closeables;
import com.facebook.common.internal.Supplier;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.memory.PooledByteBufferInputStream;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.backends.pipeline.info.ImageOriginListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imageformat.ImageFormatChecker;
import com.facebook.imagepipeline.cache.CountingMemoryCache;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.*;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

import java.io.File;
import java.io.InputStream;

public class FrescoUtil {
    private static final String TAG = "FrescoUtil";
    private static final String FRESCO_CACHE = "fresco_cache";
    private static final int FRESCO_DEFAULT_WIDTH = -1;
    private static final int FRESCO_DEFAULT_HEIGHT = -1;

    // TODO: 2019/4/21 ImagePipelineConfig配置
    // TODO: 2019/4/23 producer时间
    public static void init(Context context){
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(context)
                .setCacheErrorLogger(new CacheErrorLogger() {
                    @Override
                    public void logError(CacheErrorCategory category, Class<?> clazz, String message, Throwable throwable) {

                    }
                })
                .setBaseDirectoryPath(context.getExternalCacheDir())
                .setBaseDirectoryName(FRESCO_CACHE)
                .setMaxCacheSize(CacheConstant.MAX_DISK_CACHE_SIZE)
                .build();

        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
                .setDownsampleEnabled(true)
                .setMainDiskCacheConfig(diskCacheConfig)
                .setBitmapMemoryCacheParamsSupplier(new Supplier<MemoryCacheParams>() {
                    @Override
                    public MemoryCacheParams get() {
                        MemoryCacheParams params = new MemoryCacheParams(
                                CacheConstant.MAX_MEMORY_CACHE_SIZE, // Max total size of elements in the cache
                                256,// Max entries in the cache @see BitmapCounterProvider
                                Integer.MAX_VALUE, // Max total size of elements in eviction queue
                                Integer.MAX_VALUE,                     // Max length of eviction queue
                                Integer.MAX_VALUE);                    // Max cache entry size
                        return params;
                    }
                })
//                .setRequestListeners()
//                .setImageCacheStatsTracker(null)
//                .setMemoryTrimmableRegistry(null)
//                .setBitmapMemoryCacheTrimStrategy(null)
//                .setSmallImageDiskCacheConfig(null)
//                .setBitmapsConfig(Bitmap.Config.RGB_565)
//                .setCacheKeyFactory(null)
//                .setRequestListeners(null)
//                .setDiskCacheEnabled(true)
//                .setEncodedMemoryCacheParamsSupplier(null)
//                .setExecutorSupplier(null)
//                .setFileCacheFactory(null)
//                .setHttpConnectionTimeout(1000 * 30)
//                .setImageDecoder(null)
//                .setImageDecoderConfig(null)
//                .setImageTranscoderFactory(null)
//                .setImageTranscoderType(ImageTranscoderType.JAVA_TRANSCODER)
//                .setIsPrefetchEnabledSupplier(null)
//                .setProgressiveJpegConfig(null)
//                .setMemoryChunkType(MemoryChunkType.BUFFER_MEMORY)
//                .setNetworkFetcher(null)
//                .setPlatformBitmapFactory(null)
//                .setPoolFactory(null)
//                .setResizeAndRotateEnabledForNetwork(true)
                .build();
        Fresco.initialize(context, config);
    }


    public interface BitmapGetCallback{
        void bitmap(Bitmap bitmap);
    }

    /**
     * 获取解码图片
     */
    public static void fetchDecodedImage(String url, final BitmapGetCallback CALLBACK){
        final ImageRequest REQUEST = defaultRequestBuilder(Uri.parse(url), FRESCO_DEFAULT_WIDTH, FRESCO_DEFAULT_HEIGHT, null).build();
        final ImagePipeline IMAGEPIPELINE = Fresco.getImagePipeline();
        final DataSource<CloseableReference<CloseableImage>> DATASOURCE = IMAGEPIPELINE.fetchDecodedImage(REQUEST, null);
        DATASOURCE.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            protected void onNewResultImpl(final Bitmap BITMAP) {
                if(CALLBACK != null){
                    MainThreadUtil.run(new Runnable() {
                        @Override
                        public void run() {
                            CALLBACK.bitmap(BITMAP);
                        }
                    });
                }
                DATASOURCE.close();
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                if(CALLBACK != null){
                    MainThreadUtil.run(new Runnable() {
                        @Override
                        public void run() {
                            CALLBACK.bitmap(null);
                        }
                    });
                }
                DATASOURCE.close();
            }
        }, CallerThreadExecutor.getInstance());
    }

    /**
     * 获取未解码图片
     */
    public static void fetchEncodedImage(String url){
        final ImageRequest REQUEST = defaultRequestBuilder(Uri.parse(url), FRESCO_DEFAULT_WIDTH, FRESCO_DEFAULT_HEIGHT, null).build();
        final ImagePipeline IMAGEPIPELINE = Fresco.getImagePipeline();
        final DataSource<CloseableReference<PooledByteBuffer>>  DATASOURCE = IMAGEPIPELINE.fetchEncodedImage(REQUEST, null);
        DATASOURCE.subscribe(new BaseDataSubscriber<CloseableReference<PooledByteBuffer>>() {
            @Override
            protected void onNewResultImpl(DataSource<CloseableReference<PooledByteBuffer>> dataSource) {
                if(dataSource.getResult() != null){
                    PooledByteBuffer buffer = dataSource.getResult().get();
                    InputStream is = new PooledByteBufferInputStream(buffer);
                    try {
                        ImageFormat imageFormat = ImageFormatChecker.getImageFormat(is);
//                        Files.copy(is, );
                    } catch (Exception e) {

                    } finally {
                        Closeables.closeQuietly(is);
                    }
                }
                DATASOURCE.close();
            }

            @Override
            protected void onFailureImpl(DataSource<CloseableReference<PooledByteBuffer>> dataSource) {
                DATASOURCE.close();
            }
        }, CallerThreadExecutor.getInstance());
    }


    /**
     * 下载图片
     */
    public static void download(String url, String dir, String fileName, String suffix){
        final String TEMP_PATH = dir + File.separator + fileName + "_temp." + suffix;
        final String PATH = dir + File.separator + fileName + "." + suffix;
        if(!FileUtil.isFileExist(PATH)){
            final ImageRequest REQUEST = defaultRequestBuilder(Uri.parse(url), FRESCO_DEFAULT_WIDTH, FRESCO_DEFAULT_HEIGHT, null).build();
            final ImagePipeline IMAGEPIPELINE = Fresco.getImagePipeline();
            final DataSource<CloseableReference<CloseableImage>> DATASOURCE = IMAGEPIPELINE.fetchDecodedImage(REQUEST, null);
            DATASOURCE.subscribe(new BaseBitmapDataSubscriber() {
                @Override
                protected void onNewResultImpl(final Bitmap BITMAP) {
                    Runnable run  = new Runnable() {
                        @Override
                        public void run() {
                            BitmapUtil.save(BITMAP, TEMP_PATH, false);
                            FileUtil.rename(TEMP_PATH, PATH);
                        }
                    };
                    if(MainThreadUtil.isMainThread()){
                        ThreadUtil.execute(run);
                    }else {
                        run.run();
                    }
                    DATASOURCE.close();
                }

                @Override
                protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                    DATASOURCE.close();
                }
            }, CallerThreadExecutor.getInstance());
        }

    }
    /**下载图片**/

    /**
     * 清除缓存
     */
    public static void clearMemoryCaches(){
        Fresco.getImagePipeline().clearMemoryCaches();
    }

    public static void clearDiskCaches(){
        Fresco.getImagePipeline().clearDiskCaches();
    }

    public static void clearCaches(){
        Fresco.getImagePipeline().clearCaches();
    }

    public static void evictFromCache(Uri uri){
        Fresco.getImagePipeline().evictFromCache(uri);
    }

    public static void evictFromDiskCache(Uri uri){
        Fresco.getImagePipeline().evictFromDiskCache(uri);
    }

    public static void evictFromMemoryCache(Uri uri){
        Fresco.getImagePipeline().evictFromMemoryCache(uri);
    }

    public static void evictFromDiskCache(ImageRequest request){
        Fresco.getImagePipeline().evictFromDiskCache(request);
    }
    /**清除缓存**/


    /**
     * 简单的通过Uri或者严格通过ImageRequest去判断是否有内存缓存或磁盘缓存
     */
    public static boolean isInBitmapMemoryCache(Uri uri){
        return Fresco.getImagePipeline().isInBitmapMemoryCache(uri);
    }

    public static boolean isInBitmapMemoryCache(ImageRequest request){
        return Fresco.getImagePipeline().isInBitmapMemoryCache(request);
    }

    public interface DiskCacheCallback{
        void cache(boolean inCache);
    }

    public static void isInDiskCache(Uri uri, final DiskCacheCallback CALLBACK){
        final DataSource<Boolean> DATASOURCE = Fresco.getImagePipeline().isInDiskCache(uri);
        DATASOURCE.subscribe(new BaseDataSubscriber<Boolean>() {
            @Override
            protected void onNewResultImpl(DataSource<Boolean> dataSource) {
                if(CALLBACK != null){
                    CALLBACK.cache(dataSource.getResult() == null ? false : dataSource.getResult());
                }
                DATASOURCE.close();
            }

            @Override
            protected void onFailureImpl(DataSource<Boolean> dataSource) {
                CALLBACK.cache(false);
                DATASOURCE.close();
            }
        }, CallerThreadExecutor.getInstance());
    }

    public static void isInDiskCache(ImageRequest request, final DiskCacheCallback CALLBACK){
        final DataSource<Boolean> DATASOURCE = Fresco.getImagePipeline().isInDiskCache(request);
        DATASOURCE.subscribe(new BaseDataSubscriber<Boolean>() {
            @Override
            protected void onNewResultImpl(DataSource<Boolean> dataSource) {
                if(CALLBACK != null){
                    CALLBACK.cache(dataSource.getResult() == null ? false : dataSource.getResult());
                }
            }

            @Override
            protected void onFailureImpl(DataSource<Boolean> dataSource) {
                CALLBACK.cache(false);
                DATASOURCE.close();
            }
        }, CallerThreadExecutor.getInstance());
    }
    /**简单的通过Uri或者严格通过ImageRequest去判断是否有内存缓存或磁盘缓存**/


    /**
     * 预加载操作
     */
    public static void prefetchToBitmapCache(ImageRequest imageRequest){
        Fresco.getImagePipeline().prefetchToBitmapCache(imageRequest, null);
    }

    public static void prefetchToDiskCache(ImageRequest imageRequest, Priority priority){
        Fresco.getImagePipeline().prefetchToDiskCache(imageRequest, null, priority);
    }
    /**预加载操作**/


    /**
     * 获取ControllerBuilder
     */
    public static PipelineDraweeControllerBuilder defaultControllerBuilder(ImageRequest request, ImageOriginListener imageOriginListener, ControllerListener controllerListener){
        DispatchImageOriginListener dispatchImageOriginListener = new DispatchImageOriginListener();
        dispatchImageOriginListener.addListener(new LogImageOriginListener(request.getSourceUri()));
        if (imageOriginListener != null) {
            dispatchImageOriginListener.addListener(imageOriginListener);
        }

        DispatchControllListener dispatchControllListener = new DispatchControllListener();
        dispatchControllListener.addListener(new LogControllListener(controllerListener, request.getSourceUri()));
        if (controllerListener != null) {
            dispatchControllListener.addListener(controllerListener);
        }
        return Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setImageOriginListener(dispatchImageOriginListener)
                .setTapToRetryEnabled(true)
                .setAutoPlayAnimations(true)
                .setRetainImageOnFailure(true)
                .setControllerListener(dispatchControllListener);
    }

    public static PipelineDraweeControllerBuilder lowControllerBuilder(ImageRequest request, ImageRequest lowRequest, ImageOriginListener imageOriginListener, ControllerListener controllerListener){
        PipelineDraweeControllerBuilder builder = defaultControllerBuilder(request, imageOriginListener, controllerListener);
        return builder.setLowResImageRequest(lowRequest);
    }

    public static PipelineDraweeControllerBuilder availableControllerBuilder(ImageRequest[] requests, ImageOriginListener imageOriginListener, ControllerListener controllerListener){
        PipelineDraweeControllerBuilder builder = defaultControllerBuilder(null, imageOriginListener, controllerListener);
        return builder.setFirstAvailableImageRequests(requests);
    }
    /**获取ControllerBuilder**/

    /**
     * 获取RequestBuilder
     */
    public static ImageRequestBuilder defaultRequestBuilder(Uri uri, int width, int height, RequestListener requestListener){
        DispatchRequestListener dispatchRequestListener = new DispatchRequestListener();
        dispatchRequestListener.addListener(new LogRequestListener());
        if (requestListener != null) {
            dispatchRequestListener.addListener(requestListener);
        }
        ImageRequestBuilder builder = ImageRequestBuilder.newBuilderWithSource(uri).setRequestListener(dispatchRequestListener)
                .setLocalThumbnailPreviewsEnabled(true);
        if(width > 0 && height > 0){
            builder.setResizeOptions(new ResizeOptions(width, height));
        }
        return builder;
    }

    public static ImageRequestBuilder processorRequestBuilder(Uri uri, int width, int height, RequestListener requestListener, Postprocessor postprocessor){
        ImageRequestBuilder builder = defaultRequestBuilder(uri, width, height, requestListener);
        builder.setPostprocessor(postprocessor);
        return builder;
    }
    /**获取RequestBuilder**/

    /**
     * 获取磁盘缓存大小
     */
    public static long getFileCacheSize(){
        long mainFileSize = Fresco.getImagePipelineFactory().getMainFileCache().getSize();
        long smallFileSize = Fresco.getImagePipelineFactory().getSmallImageFileCache().getSize();
        return mainFileSize + smallFileSize;
    }
    /**获取磁盘缓存大小**/

    /**
     * 获取内存缓存大小
     */
    public static float getMemoryCacheSize() {
        CountingMemoryCache countingCache = Fresco.getImagePipelineFactory().getBitmapCountingMemoryCache();
        long size1 = countingCache.getSizeInBytes();
        CountingMemoryCache encodingCache = Fresco.getImagePipelineFactory().getEncodedCountingMemoryCache();
        long size2 = encodingCache.getSizeInBytes();
        return (size1 + size2) / (1024.0F * 1024.0F);
    }
    /**获取内存缓存大小**/
}
