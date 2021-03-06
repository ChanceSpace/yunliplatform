package com.yajun.green.web.resovler;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * User: Jack Wang
 * Date: 15-8-10
 * Time: 上午9:14
 */
public class ApplicationMultipartResolver  extends CommonsMultipartResolver {

    private HttpServletRequest request;

    private ApplicationProgressListener progressListener;

    @Override
    public boolean isMultipart(HttpServletRequest request) {
        this.request = request;
        return super.isMultipart(request);
    }

    @Override
    protected FileUpload newFileUpload(FileItemFactory fileItemFactory) {
        FileUpload fileUpload = super.newFileUpload(fileItemFactory);

        if (progressListener == null) {
            progressListener = new ApplicationProgressListener(request);
        } else {
            progressListener.setRequest(request);
        }

        fileUpload.setProgressListener(progressListener);

        return fileUpload;
    }
}
