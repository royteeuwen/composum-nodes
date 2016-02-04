package com.composum.sling.clientlibs.service;

import com.composum.sling.clientlibs.handle.Clientlib;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface ClientlibProcessor {

    String DEFAULT_CHARSET = "UTF-8";

    /**
     * Transforms the content and result in a stream with the probaly changed content - for pipes.
     *
     * @param clientlib    the clientlib definition for meta information
     * @param sourceStream the original to process by the processor
     * @return the transformation result
     * @throws IOException
     */
    InputStream processContent(Clientlib clientlib, InputStream sourceStream, Map<String,Object> hints)
            throws IOException;
}
