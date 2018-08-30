package com.joker.main

import android.app.job.JobInfo
import android.app.job.JobInfo.TriggerContentUri.FLAG_NOTIFY_FOR_DESCENDANTS
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Build
import android.os.Environment
import android.os.Process
import android.provider.MediaStore
import android.support.annotation.MainThread
import android.support.annotation.RequiresApi


class DiscoverService : JobService() {
  companion object {
    const val JOB_OBSERVE_IMAGE=0x1

    @RequiresApi(21)
    fun scheduleObserveImage(context : Context,intent:Intent):Int {
      val builder = JobInfo.Builder(1,ComponentName(context,DiscoverService::class.java))
      val jobInfo = builder
          .addTriggerContentUri(JobInfo.TriggerContentUri(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,FLAG_NOTIFY_FOR_DESCENDANTS))
          .setOverrideDeadline(0)
          .build()
      (context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as? JobScheduler)?.apply {
       return schedule(jobInfo)
      }
      return JobScheduler.RESULT_FAILURE
    }
  }


  val PROJECTION = arrayOf(MediaStore.Images.ImageColumns._ID,MediaStore.Images.ImageColumns.DATA)
  val DCIM_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).path
  // Path segments for image-specific URIs in the provider.
  val EXTERNAL_PATH_SEGMENTS = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.pathSegments!!

  override fun onCreate() {
    super.onCreate()
    println("DiscoverService.onCreate")
  }

  override fun onStart(intent : Intent?,startId : Int) {
    super.onStart(intent,startId)
    println("DiscoverService.onStart")
  }
  override fun onStartCommand(intent : Intent?,flags : Int,startId : Int) : Int {
    println("DiscoverService.onStartCommand")
    return super.onStartCommand(intent,flags,startId)
  }


  @MainThread
  override fun onStartJob(params : JobParameters?) : Boolean {
    val work = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      params?.dequeueWork()
    } else {
      //nothing
      null
    }

    println("${Process.myUid()}--------------------------${Process.myPid()}")
    println(Thread.currentThread())
    println("DiscoverService.onStartJob")
    params?.triggeredContentUris?.forEach { uri ->
      // Instead of real work, we are going to build a string to show to the user.
      val sb = StringBuilder()
      val ids = ArrayList<String>()
      val path = uri.pathSegments
      if (path != null && path.size == EXTERNAL_PATH_SEGMENTS.size + 1) {
        // This is a specific file.
        ids.add(path[path.size - 1])
      }
      if (ids.size > 0) {
        // If we found some ids that changed, we want to determine what they are.
        // First, we do a query with content provider to ask about all of them.
        val selection = StringBuilder()
        val size = ids.size-1
        for (i in 0 .. size) {
          if (selection.isNotEmpty()) {
            selection.append(" OR ")
          }
          selection.append(MediaStore.Images.ImageColumns._ID)
          selection.append("='")
          selection.append(ids[i])
          selection.append("'")
        }
        // Now we iterate through the query, looking at the filenames of
        // the items to determine if they are ones we are interested in.
        var cursor : Cursor? = null
        var haveFiles = false

        try {
          cursor = contentResolver.query(
              MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
              PROJECTION,selection.toString(),null,null)
          if (cursor != null) {
            while (cursor.moveToNext()) {
              // We only care about files in the DCIM directory.
              val dir = cursor.getString(1)
              if (dir.startsWith(DCIM_DIR)) {
                if (!haveFiles) {
                  haveFiles = true
                  sb.append("New photos:\n")
                }
                sb.append(cursor.getInt(0))
                sb.append(": ")
                sb.append(dir)
                sb.append("\n")
              }
            }
          }
        } catch (e : SecurityException) {
          sb.append("Error: no access to media!")
        } finally {
          cursor?.close()
        }
      }
      println(sb)
      if (sb.length>10){
        jobFinished(params,false)
      }
    }
  return true
}

  @MainThread
  override fun onStopJob(params : JobParameters?) : Boolean {
    println("DiscoverService.onStopJob----${params?.jobId}")
    return false
  }

}
