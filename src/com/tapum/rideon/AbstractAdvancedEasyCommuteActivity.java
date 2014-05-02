package com.tapum.rideon;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView.OnQueryTextListener;

import com.tapum.api.rideon.model.Favorite;
import com.tapum.rideon.broker.ArrivalInfoBroker;
import com.tapum.rideon.favorite.FavoriteBroker;
import com.tapum.rideon.lib.R;

/**
 * 
 * @author Neeraj Jain
 * 
 */

public abstract class AbstractAdvancedEasyCommuteActivity extends
		AbstractEasyCommuteActivity implements OnQueryTextListener {
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.agency_menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (ArrivalInfoBroker.instance == null)
			return false;
		MenuItem item = menu.findItem(R.id.menu_favorite);
		Log.d("AbstractAdvancedEasyCommuteActivity-onPrepareOptionsMenu",
				"favorite_item=" + item);
		if (item == null) {
			return true;
		}
		boolean isfavorite = false;

		String stopCode = ArrivalInfoBroker.instance.getStopCode();
		Log.d("AbstractAdvancedEasyCommuteActivity-onPrepareOptionsMenu",
				stopCode);
		if (stopCode != null && FavoriteBroker.favorites != null) {
			for (Favorite fav : FavoriteBroker.favorites) {
				if (fav.getStation().getAbbreviation().equals(stopCode)) {
					isfavorite = true;
					break;
				}
			}
		}
		Log.d("onPrepareOptionsMenu-isfavorite", "" + isfavorite);
		if (isfavorite) {
			item.setIcon(getResources().getDrawable(R.drawable.btn_star_big_on));
		} else {
			item.setIcon(getResources()
					.getDrawable(R.drawable.btn_star_big_off));
		}

		return true;
	}

}
