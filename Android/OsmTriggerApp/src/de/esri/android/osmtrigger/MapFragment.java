package de.esri.android.osmtrigger;


import org.json.JSONException;
import org.json.JSONObject;

import com.esri.android.map.Callout;
import com.esri.android.map.Layer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.Point;
import com.esri.core.map.CallbackListener;
import com.esri.core.map.FeatureSet;
import com.esri.core.map.Graphic;
import com.esri.core.tasks.ags.query.Query;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Fragment that displays the map.
 */
public class MapFragment extends Fragment{
	private static final String TAG = "OSM Geotrigger";
	private String webMapUrl = "http://esri-de-dev.maps.arcgis.com/home/item.html?id=26d316dfb7034da1991cc862a51d04e2";
	private String user = "rsu4devprog";
	private String password = "devprog42195"; //TODO Rainald Passwort entfernen
	private MapView mapView;
	private String mapState;
	private final String MAP_STATE = "MapState";
	private Callout popup;
	
	public MapFragment(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.i(TAG, "MapFragment, onCreateView()");
		
		// load webmap
		mapView = new MapView(getActivity(), webMapUrl, user, password);
		
	    if (savedInstanceState != null) {
	        mapState = savedInstanceState.getString(MAP_STATE);
	    }	
		if (mapState != null) {
			mapView.restoreState(mapState);
		}
		
		return mapView;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	    if (mapState != null) {
	        outState.putString(MAP_STATE, mapState);
	    }
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mapView.unpause();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mapState = mapView.retainState();
		mapView.pause();
	}	
	
	//TODO Webinar Layer suchen und nach Feature abfragen
	/**
	 * Show a feature and a popup with its attributes when a trigger notification is send.
	 * @param text The notification test.
	 * @param url The notification url.
	 * @param data The notification data.
	 */
	public void showFeature(String text, String url, String data){		
		try {
			// get the layer 
			JSONObject dataJson = new JSONObject(data);
			String layerName = dataJson.getString("layer");
			ArcGISFeatureLayer featurelayer = null;
			if(mapView.isLoaded()){
				Layer[] layers = mapView.getLayers();
				for(Layer layer : layers){
					String name = layer.getName();
					if(layerName.equals(name)){
						featurelayer = (ArcGISFeatureLayer) layer;
						break;
					}
				}
				// get OBJECTID
				JSONObject attributes = dataJson.getJSONObject("tags");
				String objectId = attributes.getString("OBJECTID");
				String where = "OBJECTID=" + objectId;
				// select the feature
				Query query = new Query();
				query.setWhere(where);
			    CallbackListener<FeatureSet> callback = new CallbackListener<FeatureSet>() {

			        public void onCallback(FeatureSet featureSet) {           
			        	if(featureSet.getGraphics().length > 0){
			        		Graphic feature = featureSet.getGraphics()[0];
			        		showPopup(feature);
			        	}
			        }

			        public void onError(Throwable e) {
			        	Log.e(TAG, "Error: " + e.getMessage());
			        }
			    };
			    if(featurelayer != null){
					featurelayer.queryFeatures(query, callback);
			    }
			}
		} catch (JSONException e) {
			Log.e(TAG, "Error: " + e.getMessage());
		}		
	}
	
	private void showPopup(Graphic feature){
		Activity activity = getActivity();
		
		LinearLayout calloutLayout = new LinearLayout(activity);
		LinearLayout.LayoutParams calloutLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		calloutLayout.setLayoutParams(calloutLayoutParams);
		calloutLayout.setOrientation(LinearLayout.VERTICAL);
		
		LinearLayout headerLayout = new LinearLayout(activity);
		LinearLayout.LayoutParams headerLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		headerLayout.setLayoutParams(headerLayoutParams);
		headerLayout.setOrientation(LinearLayout.HORIZONTAL);
		headerLayout.setBackgroundColor(Color.YELLOW);
		
		TextView headerText = new TextView(activity);
		LinearLayout.LayoutParams headerTextLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
		headerText.setLayoutParams(headerTextLayoutParams);
		headerText.setPadding(12, 5, 12, 5);
		headerText.setTextColor(Color.rgb(0, 0, 0));
		headerText.setText("OSM Feature");
		headerText.setTextSize(15.0f);
		headerText.setTypeface(null, Typeface.BOLD);
		headerText.setBackgroundColor(Color.rgb(210, 210, 210));
		headerLayout.addView(headerText);
		
		TextView closeButton = new TextView(activity);
		LinearLayout.LayoutParams closeButtonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.0f);
		closeButton.setLayoutParams(closeButtonLayoutParams);
		closeButton.setPadding(18, 5, 18, 5);
		closeButton.setText("X");
		closeButton.setTextSize(15.0f);
		closeButton.setTypeface(null, Typeface.BOLD);
		closeButton.setBackgroundColor(Color.rgb(84, 145, 184));
		closeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popup.hide();
			}
		});
		headerLayout.addView(closeButton);
		
		calloutLayout.addView(headerLayout);
		
		TableLayout table = new TableLayout(activity);
		TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
		table.setLayoutParams(layoutParams);
		
		String[] attrNames = feature.getAttributeNames();
		for(String attrName : attrNames){
			if(!attrName.equals("KeyValueAll")){
				Object attrValue = feature.getAttributeValue(attrName);
				TableRow row = new TableRow(activity);
				TextView nameText = new TextView(activity);
				nameText.setPadding(2, 1, 6, 0);
				nameText.setTextColor(Color.rgb(0, 0, 0));
				nameText.setText(attrName);
				nameText.setTextSize(15.0f);
				TextView valueText = new TextView(activity);
				valueText.setPadding(6, 1, 2, 0);
				valueText.setTextColor(Color.rgb(0, 0, 0));
				String value = attrValue != null ? attrValue.toString() : "";			
				valueText.setTextSize(15.0f);
				if(value.startsWith("http")){
					String url = "<a href=\"" + value + "\">" + this.getString(R.string.link_hint) + "</a>";
					valueText.setMovementMethod(LinkMovementMethod.getInstance());
					valueText.setText(Html.fromHtml(url));
				}else{
					valueText.setText(value);
				}
				row.addView(nameText);
				row.addView(valueText);
				table.addView(row);				
			}
		}
		calloutLayout.addView(table);
		
		popup = mapView.getCallout();
		popup.setContent(calloutLayout);
		Point mapPoint = null;
		Geometry geometry = feature.getGeometry();
		if(geometry.getType() == Geometry.Type.POINT){
			mapPoint = (Point)geometry;
		}else{
			Envelope env = new Envelope();
			geometry.queryEnvelope(env);
			mapPoint = env.getCenter();
		}
		mapView.centerAt(mapPoint, false);
		Point p = mapView.getCenter();
		popup.show(mapPoint);
	}
}
