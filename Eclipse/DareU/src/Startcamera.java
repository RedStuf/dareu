
/*
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.View;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;


public class Startcamera {



	public void startCamera() {
		Fragment cameraFragment = new CameraFragment();
		FragmentTransaction transaction = getActivity().getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.fragmentContainer, cameraFragment);
		transaction.addToBackStack("Pending");
		transaction.commit();
	}


	@Override
	public void onResume() {
		super.onResume();
		ParseFile photoFile = ((Challenge) getActivity())
				.getCurrentChallenge().getPhotoFile();
		if (photoFile != null) {
			evidencePreview.setParseFile(photoFile);
			evidencePreview.loadInBackground(new GetDataCallback() {
				@Override
				public void done(byte[] data, ParseException e) {
					evidencePreview.setVisibility(View.VISIBLE);
				}
			});
		}
	}

}

	// Geschreven door Amit Sanchit//
	
*/
