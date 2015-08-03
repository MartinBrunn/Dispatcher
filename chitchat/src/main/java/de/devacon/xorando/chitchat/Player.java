package de.devacon.xorando.chitchat;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import de.devacon.datastruct.TimeStamp;

/**
 * Created by @Martin@ on 01.08.2015 09:12.
 */
public class Player implements View.OnClickListener,
        MediaRecorder.OnErrorListener,
        MediaPlayer.OnCompletionListener,
        MediaRecorder.OnInfoListener {
    enum RecordState {
        IDLE,
        RECORDING
    }
    interface OnRecordStateChangedListener {
        void onRecordStateChangedListener(RecordState state);
    }
    MediaRecorder recorder = null;
    OnRecordStateChangedListener listener ;
    File dir = null;
    boolean storeAsMillis = false;
    boolean isRecording = false;
    ArrayList<File> queue = new ArrayList<>();
    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.play:
                v.setVisibility(View.INVISIBLE);
                record() ; break;
            case R.id.stop:
                v.setVisibility(View.INVISIBLE);
                stop();break;
        }
    }
    public void record() {
        String name = "";
        if(storeAsMillis) {
            name = "D_" + Long.toString(System.currentTimeMillis());
        }
        else {
            //TimeStamp time = new TimeStamp(System.currentTimeMillis(),TimeStamp.DECI);
            Long l = System.currentTimeMillis();
            name = "F_" + String.format("%tY%tm%td_%tH%tM%tS_%tL",l,l,l,l,l,l,l);
                    //time.toString();
        }
        recorder = new MediaRecorder();
        recorder.setOnErrorListener(this);
        recorder.setOnInfoListener(this);
        String str = "";
        //recorder.reset();
        int max = recorder.getAudioSourceMax();
        boolean success = false;
        //Toast.makeText(getApplicationContext(),"Max:" + Integer.toString(max),Toast.LENGTH_LONG).show();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        /*for(int i = 0 ; i < max ; ++i) {
            try {
                recorder.setAudioSource(i);
                success = true;
                break;
            } catch (RuntimeException e) {
                e.printStackTrace();
                str = e.getLocalizedMessage();
                //Toast.makeText(getApplicationContext(), str + " " + Integer.toString(i), Toast.LENGTH_LONG).show();
                recorder.reset();
            }
        }
        if(!success) {
            recorder.release();
            recorder = null;
            return;
        }*/

        try {
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        }
        catch(IllegalStateException e) {
            e.printStackTrace();
            str = e.getLocalizedMessage();
            //Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG).show();
        }

        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        dir.mkdirs();
        File file = new File(dir,name + ".3gp");
        recorder.setOutputFile(file.getAbsolutePath());
        try {
            recorder.prepare();
            recorder.start();
            isRecording = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(isRecording && listener != null) {
            listener.onRecordStateChangedListener(RecordState.RECORDING);
        }


    }
    public void stop() {
        if(recorder == null)
            return;
        if(isRecording)
            recorder.stop();
        recorder.release();
        recorder = null;
        isRecording = false;
        if(listener != null) {
            listener.onRecordStateChangedListener(RecordState.IDLE);
        }
    }
    public void enqueue(File file) {
        queue.add(file);
    }
    public void play() {
        if(queue.size() > 0 ){
            File next = queue.get(0);
            queue.remove(0);
            playFile(next);
        }
    }
    public void playFile(File file) {
            MediaPlayer player = new MediaPlayer();
            player.setOnCompletionListener(this);

            try {
                player.setDataSource(file.getAbsolutePath());
                player.prepare();
                player.start();

            } catch (IOException e) {
                e.printStackTrace();
            }

    }
    public void setTargetDir(File dir){
        this.dir = dir;
    }
    /**
     * Called when an error occurs while recording.
     *
     * @param mr    the MediaRecorder that encountered the error
     * @param what  the type of error that has occurred:
     *              <ul>
     *              <li>{@link #MEDIA_RECORDER_ERROR_UNKNOWN}
     *              <li>{@link #MEDIA_ERROR_SERVER_DIED}
     *              </ul>
     * @param extra an extra code, specific to the error type
     */
    @Override
    public void onError(MediaRecorder mr, int what, int extra) {
        String txt ;
        switch(what) {
            case MediaRecorder.MEDIA_RECORDER_ERROR_UNKNOWN:
                txt = "Unknown error";
                break;
            case MediaRecorder.MEDIA_ERROR_SERVER_DIED:
                txt = "Server died";
                break;
            default:
                txt = "Unknown Unknown";
                break;
        }
        //Toast.makeText(getApplicationContext(), "Media recorder error " + txt, Toast.LENGTH_LONG).show();
    }

    /**
     * Called when an error occurs while recording.
     *
     * @param mr    the MediaRecorder that encountered the error
     * @param what  the type of error that has occurred:
     *              <ul>
     *              <li>{@link #MEDIA_RECORDER_INFO_UNKNOWN}
     *              <li>{@link #MEDIA_RECORDER_INFO_MAX_DURATION_REACHED}
     *              <li>{@link #MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED}
     *              </ul>
     * @param extra an extra code, specific to the error type
     */
    @Override
    public void onInfo(MediaRecorder mr, int what, int extra) {
        String str;

        switch(what) {
            case MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED:
                str = "duration reached";
                break;
            case MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED:
                str = "File size reached";
                break;
            case MediaRecorder.MEDIA_RECORDER_INFO_UNKNOWN:
                str = "Unknown";
                break;
            default:
                str = "Unknown unknown";
                break;
        }
        //Toast.makeText(getApplicationContext(),"info:" + str,Toast.LENGTH_LONG).show();

    }
    /**
     * Called when the end of a media source is reached during playback.
     *
     * @param mp the MediaPlayer that reached the end of the file
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        MediaPlayer player = mp;
        if(player != null) {
            player.release();

        }
        if(queue.size()>0) {
            File next = queue.get(0);
            queue.remove(0);
            playFile(next);
        }

    }
    public void setOnRecordStateChangedListener(OnRecordStateChangedListener listener){
        this.listener = listener;
    }
}
