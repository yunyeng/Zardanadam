package appversion1.zardanadam;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private MediaPlayer mediaPlayer;
	private String[] songURL;
	private String singleSong;
	private int playbackPosition = 0;
	private int j = 0;
	private int k = 0;
	private ImageButton play;
	private ImageButton pause;
	private String[] albumNames;
	private String[] songNames;

	private TextView songTitle;
	private String[] songDescs;
	private TextView songLyrics;
	private TextView songAlbum;
	private ImageView coverArt;

	private int[] covers = { R.drawable.kafamseninleguzel,
			R.drawable.kalbimyok, R.drawable.dibinigor,
			R.drawable.sevgililergunu, R.drawable.sureyya, R.drawable.korsan,
			R.drawable.tamambocegi };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void doClick(View view) {
		songURL = getResources().getStringArray(R.array.songURLs);
		String singleSong = songURL[j];
		play = (ImageButton) findViewById(R.id.startPlayerBtn);
		pause = (ImageButton) findViewById(R.id.pausePlayerBtn);
		switch (view.getId()) {
		case R.id.startPlayerBtn:
			try {
				if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
					//if (playbackPosition != 0) {
						mediaPlayer.seekTo(playbackPosition);
						Log.d("MainActivity", "Song Name is: " + singleSong);
					//}
					mediaPlayer.start();
				} else {
					Log.d("MainActivity", "Song Name is: " + singleSong);
					playAudio(singleSong);
				}
				play.setVisibility(View.INVISIBLE);
				pause.setVisibility(View.VISIBLE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.pausePlayerBtn:
			if (mediaPlayer != null && mediaPlayer.isPlaying()) {
				playbackPosition = mediaPlayer.getCurrentPosition();
				mediaPlayer.pause();
				pause.setVisibility(View.INVISIBLE);
				play.setVisibility(View.VISIBLE);
			}
			break;
		}
	}

	private void playAudio(String url) throws Exception {
		killMediaPlayer();
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setDataSource(url);
		mediaPlayer.prepare();
		mediaPlayer.start();
	}

	private void killMediaPlayer() {
		if (mediaPlayer != null) {
			try {
				mediaPlayer.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void onPause() {
		play = (ImageButton) findViewById(R.id.startPlayerBtn);
		pause = (ImageButton) findViewById(R.id.pausePlayerBtn);
		pause.setVisibility(View.INVISIBLE);
		play.setVisibility(View.VISIBLE);
		Log.e("Pickle", "onPause ");
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
			mediaPlayer.release();
		}
		super.onPause();
	}

	public int whichAlbum(int id) {
		int result = 0;
		if (id >= 0 && id < 10) {
			result = 0;
		} else if (id >= 10 && id < 23) {
			result = result + 1;
		} else if (id >= 23 && id < 39) {
			result = result + 2;
		} else if (id >= 39 && id < 41) {
			result = result + 3;
		} else if (id >= 41 && id < 44) {
			result = result + 4;
		} else if (id >= 44 && id < 57) {
			result = result + 5;
		} else if (id >= 57 && id < 66) {
			result = result + 6;
		}
		return result;
	}

	public int albumNumbers(int album) {
		int result = 0;
		switch (album) {
		case 0:
			result = 0;
			break;
		case 1:
			result = 10;
			break;
		case 2:
			result = 23;
			break;
		case 3:
			result = 39;
			break;
		case 4:
			result = 41;
			break;
		case 5:
			result = 44;
			break;
		case 6:
			result = 57;
			break;
		}
		return result;
	}

	public void nextSong(View v) {
		coverArt = (ImageView) findViewById(R.id.cover);
		songURL = getResources().getStringArray(R.array.songURLs);

		albumNames = getResources().getStringArray(R.array.albumNames);
		songAlbum = (TextView) findViewById(R.id.textView4);
		songNames = getResources().getStringArray(R.array.songNames);
		songTitle = (TextView) findViewById(R.id.textView2);
		songDescs = getResources().getStringArray(R.array.songLyrics);
		songLyrics = (TextView) findViewById(R.id.textView1);

		j = Math.abs((j + 1) % songNames.length);
		k = whichAlbum(j);
		songTitle.setText(songNames[j]);
		songLyrics.setText(songDescs[j]);
		songAlbum.setText(albumNames[k]);
		coverArt.setImageResource(covers[k]);
		if (mediaPlayer != null && (mediaPlayer.isPlaying() || !mediaPlayer.isPlaying())) {
			singleSong = songURL[j];
			mediaPlayer.stop();
			mediaPlayer.release();
			try {
				playAudio(singleSong);
				Log.d("MainActivity", "Song Name is: " + singleSong);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void previousSong(View v) {
		songURL = getResources().getStringArray(R.array.songURLs);
		
		coverArt = (ImageView) findViewById(R.id.cover);
		albumNames = getResources().getStringArray(R.array.albumNames);
		songAlbum = (TextView) findViewById(R.id.textView4);
		songNames = getResources().getStringArray(R.array.songNames);
		songTitle = (TextView) findViewById(R.id.textView2);
		songDescs = getResources().getStringArray(R.array.songLyrics);
		songLyrics = (TextView) findViewById(R.id.textView1);

		if (j <= 0) {
			j = songNames.length - 1;
		} else {
			j--;
		}
		k = whichAlbum(j);
		songTitle.setText(songNames[j]);
		songLyrics.setText(songDescs[j]);
		songAlbum.setText(albumNames[k]);
		coverArt.setImageResource(covers[k]);
		if (mediaPlayer != null && (mediaPlayer.isPlaying() || !mediaPlayer.isPlaying())) {
			singleSong = songURL[j];
			mediaPlayer.stop();
			mediaPlayer.release();
			try {
				playAudio(singleSong);
				Log.d("MainActivity", "Song Name is: " + singleSong);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void changeAlbum(View v) {
		songURL = getResources().getStringArray(R.array.songURLs);
		
		songNames = getResources().getStringArray(R.array.songNames);
		songTitle = (TextView) findViewById(R.id.textView2);
		songDescs = getResources().getStringArray(R.array.songLyrics);
		songLyrics = (TextView) findViewById(R.id.textView1);
		coverArt = (ImageView) findViewById(R.id.cover);
		albumNames = getResources().getStringArray(R.array.albumNames);
		songAlbum = (TextView) findViewById(R.id.textView4);

		int buttonName = v.getId();
		if (buttonName == R.id.previousAlbum) {

			if (k <= 0) {
				k = albumNames.length - 1;
			} else {
				k--;
			}

			j = albumNumbers(k);
			coverArt.setImageResource(covers[k]);
			songAlbum.setText(albumNames[k]);
			songTitle.setText(songNames[j]);
			songLyrics.setText(songDescs[j]);			

		} else if (buttonName == R.id.nextAlbum) {

			k = Math.abs((k + 1) % albumNames.length);
			j = albumNumbers(k);
			coverArt.setImageResource(covers[k]);
			songAlbum.setText(albumNames[k]);
			songTitle.setText(songNames[j]);
			songLyrics.setText(songDescs[j]);

		}
		
		if (mediaPlayer != null && (mediaPlayer.isPlaying() || !mediaPlayer.isPlaying())) {
			singleSong = songURL[j];
			mediaPlayer.stop();
			mediaPlayer.release();
			try {
				playAudio(singleSong);
				Log.d("MainActivity", "Song Name is: " + singleSong);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	public void buttons(View v) {
		int buttonName = v.getId();
		String url = "";
		if (buttonName == R.id.button1) {
			url = "http://www.zardanadam.com";
		} else if (buttonName == R.id.button2) {
			url = "http://www.facebook.com/group.php?gid=5686887372";
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
	}

}