package source55.writeHere;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.view.View;
import android.graphics.Bitmap;
import android.Manifest;
import android.widget.Button;
import android.widget.Toolbar.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.view.Gravity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

public class MainActivity extends Activity{
	WebView wv;
	@Override protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		LinearLayout ll=new LinearLayout(this);
		ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.setPadding(12,12,12,0);
		LayoutParams lpw=new LayoutParams(LayoutParams.FILL_PARENT,this.getResources().getDisplayMetrics().heightPixels/2);
		lpw.setMargins(0,0,0,12);
		wv=new WebView(this);
		wv.setLayoutParams(lpw);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.loadUrl(getString(R.string.querystring));
		ll.addView(wv);
		ll.addView(geraBtn(getString(R.string.style),new View.OnClickListener(){public void onClick(View v){
			wv.loadUrl("javascript:geraStilo(false);");
		}}));
		ll.addView(geraBtn(getString(R.string.reset),new View.OnClickListener(){public void onClick(View v){
			wv.loadUrl("javascript:inicio();");
		}}));
		ll.addView(geraBtn(getString(R.string.frase),new View.OnClickListener(){public void onClick(View v){
			wv.loadUrl("javascript:geraStilo(true);");
		}}));
		ll.addView(geraBtn(getString(R.string.save),new View.OnClickListener(){public void onClick(View v){
			wv.setDrawingCacheEnabled(true);
			Bitmap bitmap=Bitmap.createBitmap(wv.getDrawingCache());
			wv.setDrawingCacheEnabled(false);
			Intent shareImageIntent=new Intent(Intent.ACTION_SEND);
			shareImageIntent.setType("image/jpeg");
			shareImageIntent.putExtra(Intent.EXTRA_STREAM,Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,String.valueOf(System.currentTimeMillis()),"styled phrase")));
			startActivity(Intent.createChooser(shareImageIntent,""));
		}}));
		ScrollView sv=new ScrollView(this);
		sv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		sv.addView(ll);
		setContentView(sv);
	}
	Button geraBtn(String caption,View.OnClickListener fcn){
		Button b=new Button(this);
		b.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		b.setText(caption);
		b.setGravity(Gravity.LEFT);
		b.setPadding(40,20,20,20);
		b.setOnClickListener(fcn);
		return b;
	}
}
