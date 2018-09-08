package com.joker.http.okhttp.request;
import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

public class ProgressRequestBody extends RequestBody{
 private final MediaType mediaType;
 private final File file;
 public ProgressRequestBody(MediaType mediaType,File file){
   this.mediaType=mediaType;
   this.file=file;
 }

 @Override
 public MediaType contentType(){
  return mediaType;
 }

 @Override
 public void writeTo(BufferedSink sink) throws IOException{
  Source source = null;
  try {
   source = Okio.source(file);
   sink.writeAll(source);
  } finally {
   Util.closeQuietly(source);
  }
 }
}
