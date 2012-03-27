package mobile.team.reservations;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class EditPreferencesActivity extends PreferenceActivity {
	@Override
	public void onCreate(Bundle savedInstanceState)	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
}